/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson1.annotator;

import com.workfusion.vds.sdk.api.nlp.annotator.Annotator;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.Token;

/**
 * Assignment 1
 */
public class Assignment1TokenAnnotator implements Annotator<Document> {

    /**
     * Regex pattern to use for matching {@link Token} elements.
     */
    private static final String TOKEN_REGEXP = "\\w+";

    @Override
    public void process(Document document) {

        //TODO: PUT YOUR CODE HERE

    }

}
