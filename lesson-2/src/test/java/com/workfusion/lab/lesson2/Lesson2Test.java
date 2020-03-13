/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.workfusion.lab.lesson2.annotator.Assignment1KeywordNerAnnotator;
import com.workfusion.lab.lesson2.annotator.Assignment2EmailNerAnnotator;
import com.workfusion.lab.utils.BaseLessonTest;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.model.NamedEntity;
import com.workfusion.vds.sdk.nlp.component.annotator.tokenizer.MatcherTokenAnnotator;

public class Lesson2Test extends BaseLessonTest {

    /**
     * <p><b>Assignment 1</b></p>
     * <p>
     *     Create an annotator that adds {@link NamedEntity} elements with type="state" for Tokens containing the following words: Missouri,
     *     Nevada, Alaska, Hawaii, Texas, Maryland, Vermont.. As an template, use {@link Assignment1KeywordNerAnnotator}.
     * </p>
     * <p>Tips:</p>
     * <ul>
     *     <li>Use {@link NamedEntity#descriptor()} method to get a new NamedEntity.Descriptor and add it into a Document.</li>
     *     <li>Use {@link NamedEntity.Descriptor#setType(String type)} to set mention type for the Named Entity.</li>
     * </ul>
     */
    @Test
    public void assignment1() throws Exception {
        // Creates ML-SDK Document to process
        IeDocument document = getDocument("documents/lesson_2_assignment_1.txt");

        // Calls annotator to process
        processAnnotators(document,
                new MatcherTokenAnnotator("\\w+"), // Adds Token into document
                new Assignment1KeywordNerAnnotator() //Assignment annotator
        );

        // Gets all Tokens provided by the annotator to check
        List<NamedEntity> ners = new ArrayList<>(document.findAll(NamedEntity.class));

        // Checks the provided token with the assignment 1 pattern
        checkElements(ners, "lesson_2_assignment_1_check.json");
    }

    /**
     * <p><b>Assignment 2</b></p>
     * <p>
     *     Provide a custom Annotator that adds Named Entity elements for Tokens containing an email. As an template, use
     *     {@link Assignment2EmailNerAnnotator}.
     * </p>
     * <p>Tips:</p>
     * <ul>
     *     <li>Use the following regular expression for emails: \b[\w.%-]+@[-.\w]+\.[A-Za-z]{2,4}\b</li>
     * </ul>
     */
    @Test
    public void assignment2() throws Exception {
        // Creates ML-SDK Document to process
        Document document = getDocument("documents/lesson_2_assignment_2.txt");

        // Calls annotator to process
        processAnnotators(document,
                new MatcherTokenAnnotator("[\\w.@]+"), // Adds Token into document
                new Assignment2EmailNerAnnotator() //Assignment annotator
        );

        // Gets all Tokens provided by the annotator to check
        List<NamedEntity> ners = new ArrayList<>(document.findAll(NamedEntity.class));

        // Checks the provided token with the assignment 1 pattern
        checkElements(ners, "lesson_2_assignment_2_check.json");
    }

}