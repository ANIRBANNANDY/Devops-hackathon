/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson1;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.workfusion.lab.lesson1.annotator.Assignment1TokenAnnotator;
import com.workfusion.lab.lesson1.annotator.Assignment2SentenceAnnotator;
import com.workfusion.lab.utils.BaseLessonTest;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.Element;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.model.Sentence;
import com.workfusion.vds.sdk.api.nlp.model.Token;

public class Lesson1Test extends BaseLessonTest {

    /**
     * <p><b>Assignment 1</b></p>
     * <p>
     *     Create an annotator that injects {@link Token} elements into the document. As an template, use {@link Assignment1TokenAnnotator}.
     *     Let's consider a token as a word that contains one or more characters.
     * </p>
     * <p>Tips:</p>
     * <ul>
     *     <li>Use the following regular expression: \w+ or [a-zA-Z0-9_]+</li>
     *     <li>Use the {@link Document#getText()} method to get document text for analysis using regular expression</li>
     *     <li>Use the {@link Document#add(Element.ElementDescriptor)} method to inject Token into document</li>
     *     <li>Use the {@link Token#descriptor()} method to get new {@link Token.Descriptor} and add it into document</li>
     *     <li>Add the required properties into {@link Token.Descriptor} using setter methods. Use {@link Element.ElementDescriptor#setBegin(int x)} and
     *      {@link Element.ElementDescriptor#setEnd(int y)}, where 'x' is the begin position of token text in a Document and 'y' is the end
     *      position of token text</li>
     * </ul>
     */
    @Test
    public void assignment1() throws Exception {
        // Creates ML-SDK Document to process
        IeDocument document = getDocument("documents/lesson_1_assignment_1.txt");

        // Calls annotator to process
        processAnnotators(document, new Assignment1TokenAnnotator());

        // Gets all Tokens provided by the annotator to check
        List<Token> tokens = new ArrayList<>(document.findAll(Token.class));

        // Checks the provided token with the assignment 1 pattern
        checkElements(tokens, "lesson_1_assignment_1_check.json");
    }

    /**
     * <p><b>Assignment 2</b></p>
     * <p>
     *     Provide your custom annotator that injects {@link Sentence} elements into the document. As an template, use
     *     {@link Assignment2SentenceAnnotator}. Let's consider a Sentence as a chunk of text between characters .!?
     * </p>
     * <p>Tips:</p>
     * <ul>
     *     <li>Use the {@link Sentence#descriptor()} method to get {@link Element.ElementDescriptor}. Supply the descriptor with the required
     *      properties using setter methods. Add the descriptor to the document ({@link Document#add(Element.ElementDescriptor)}).</li>
     * </ul>
     */
    @Test
    public void assignment2() throws Exception {
        // Creates ML-SDK Document to process
        IeDocument document = getDocument("documents/lesson_1_assignment_2.txt");

        // Calls annotator to process
        processAnnotators(document, new Assignment2SentenceAnnotator());

        // Gets all Sentence provided by the annotator to check
        List<Sentence> sentences = new ArrayList<>(document.findAll(Sentence.class));

        // Checks the provided token with the assignment 1 pattern
        checkElements(sentences, "lesson_1_assignment_2_check.json");
    }

}