/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

public class DocumentParser {

    /**
     * Row index attribute name.
     */
    public final static String ROW_INDEX_ATTR = "row-index";

    /**
     * Column index attribute name.
     */
    public final static String COLUMN_INDEX_ATTR = "column-index";
    /**
     * Line index attribute name.
     */
    public final static String LINE_INDEX_ATTR = "line-index";

    /**
     * Wrapper class to handle Tag description
     */
    public static class Tag {

        private String name;
        private int begin;
        private int end;
        private String text;
        private Map<String, String> attr = new HashMap<>();

        public Tag(String name, int begin, Attributes attr) {
            this.name = name;
            this.begin = begin;
            this.end = begin;
            attr.asList().stream().forEach(a -> {
                this.attr.put(a.getKey(), a.getValue());
            });
        }

        public String getName() {
            return name;
        }

        public int getBegin() {
            return begin;
        }

        public int getEnd() {
            return end;
        }

        public Map<String, String> getAttr() {
            return attr;
        }

        public String getText() {
            return text;
        }

        void complete(String text, int end) {
            this.text = text;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Tag{" +
                    "name='" + name + '\'' +
                    ", begin=" + begin +
                    ", end=" + end +
                    ", text='" + text + '\'' +
                    ", attr=" + attr +
                    '}';
        }
    }

    /**
     * Private wrapper class to handle table indexes
     */
    private static class TableIndexes {

        int rowIndex = 0;
        int columnIndex = 0;

        public TableIndexes(int rowIndex, int columnIndex) {
            this.rowIndex = rowIndex;
            this.columnIndex = columnIndex;
        }
    }

    /**
     * Document Content description class
     */
    public static class DocumentContent {

        private String text;
        private List<Tag> tags = new ArrayList<>();

        public DocumentContent(String text, List<Tag> tags) {
            this.text = text;
            this.tags = tags;
        }

        public String getText() {
            return text;
        }

        public List<Tag> getTags() {
            return tags;
        }
    }

    /**
     * Prepares documentContent from html
     *
     * @param html
     * @return
     */
    public DocumentContent prepareDocumentContent(String html) {
        Element element = Jsoup.parse(html);
        FormattingVisitor formatter = new FormattingVisitor();
        NodeTraversor traversor = new NodeTraversor(formatter);
        traversor.traverse(element); // walk the DOM, and call .head() and .tail() for each node
        return formatter.getDocumentContent();
    }


    // the formatting rules, implemented in a breadth-first DOM traverse
    private class FormattingVisitor implements NodeVisitor {

        private Stack<Tag> currentTags = new Stack<>();
        private Stack<TableIndexes> tableIndexes = new Stack<>();
        private static final int maxWidth = 80;
        private int width = 0;
        private StringBuilder accum = new StringBuilder(); // holds the accumulated text
        private List<Tag> tags = new ArrayList<>();
        private int rowIndex = 0;
        private int columnIndex = 0;
        private int lineIndex = 0;


        // hit when the node is first seen
        public void head(Node node, int depth) {
            String name = node.nodeName();

            addTag(node);
            if (node instanceof TextNode) {
                append(((TextNode) node).text()); // TextNodes carry all user-readable text in the DOM.
            } else if (name.equals("li")) {
                append("\n * ");
            } else if (name.equals("dt")) {
                append("  ");
            } else if (StringUtil.in(name, "p", "h1", "h2", "h3", "h4", "h5", "tr")) {
                append("\n");
            }
        }

        boolean filterTag(Node node) {
            return !(node instanceof TextNode) && !(node instanceof Document);
        }

        void addTag(Node node) {
            if (filterTag(node)) {
                Tag tag = new Tag(node.nodeName(), accum.length(), node.attributes());
                currentTags.push(tag);
                tags.add(tag);

                if ("table".equalsIgnoreCase(node.nodeName())) {
                    tableIndexes.push(new TableIndexes(rowIndex, columnIndex));
                    rowIndex = 0;
                    columnIndex = 0;
                } else if ("tr".equalsIgnoreCase(node.nodeName())) {
                    tag.getAttr().put(ROW_INDEX_ATTR, String.valueOf(rowIndex));
                } else if ("th".equalsIgnoreCase(node.nodeName()) || "td".equalsIgnoreCase(node.nodeName())) {
                    tag.getAttr().put(COLUMN_INDEX_ATTR, String.valueOf(columnIndex++));
                    tag.getAttr().put(ROW_INDEX_ATTR, String.valueOf(rowIndex));
                } else if ("line".equalsIgnoreCase(node.nodeName())) {
                    tag.getAttr().put(LINE_INDEX_ATTR, String.valueOf(lineIndex++));
                }
            }
        }

        void completeTag(Node node) {
            if (filterTag(node)) {
                Tag tag = currentTags.pop();
                tag.complete(accum.substring(tag.getBegin(), accum.length()), accum.length());

                if ("table".equalsIgnoreCase(node.nodeName())) {
                    TableIndexes tableIndexes = this.tableIndexes.pop();
                    rowIndex = tableIndexes.rowIndex;
                    columnIndex = tableIndexes.columnIndex;
                } else if ("tr".equalsIgnoreCase(node.nodeName())) {
                    rowIndex++;
                    columnIndex=0;
                }
            }
        }

        // hit when all of the node's children (if any) have been visited
        public void tail(Node node, int depth) {
            String name = node.nodeName();
            if (StringUtil.in(name, "br", "dd", "dt", "p", "h1", "h2", "h3", "h4", "h5")) {
                append("\n");
            } else if (name.equals("a")) {
                append(String.format(" <%s>", node.absUrl("href")));
            }
            completeTag(node);
        }

        // appends text to the string builder with a simple word wrap method
        private void append(String text) {
            if (text.startsWith("\n")) {
                width = 0; // reset counter if starts with a newline. only from formats above, not in natural text
            }
            if (text.equals(" ") &&
                    (accum.length() == 0 || StringUtil.in(accum.substring(accum.length() - 1), " ", "\n"))) {
                return; // don't accumulate long runs of empty spaces
            }

            if (text.length() + width > maxWidth) { // won't fit, needs to wrap
                String words[] = text.split("\\s+");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    boolean last = i == words.length - 1;
                    if (!last) // insert a space if not the last word
                    {
                        word = word + " ";
                    }
                    if (word.length() + width > maxWidth) { // wrap and reset counter
                        accum.append("\n").append(word);
                        width = word.length();
                    } else {
                        accum.append(word);
                        width += word.length();
                    }
                }
            } else { // fits as is, without need to wrap text
                accum.append(text);
                width += text.length();
            }
        }

        public DocumentContent getDocumentContent() {
            return new DocumentContent(accum.toString(), tags);
        }
    }

}