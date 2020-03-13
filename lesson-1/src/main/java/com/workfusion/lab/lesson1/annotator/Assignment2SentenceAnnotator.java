/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson1.annotator;

import com.workfusion.vds.sdk.api.nlp.annotator.Annotator;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.Sentence;

/**
 * Assignment 2
 */
public class Assignment2SentenceAnnotator implements Annotator<Document> {

    /**
     * Regex pattern to use for splitting a document into {@link Sentence} elements.
     */
    private static final String SENTENCE_REGEXP = "[.!?]";

    @Override
    public void process(Document document) {

        //TODO: PUT YOUR CODE HERE

    }

}
