/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.lesson2.annotator;

import java.util.Arrays;
import java.util.List;

import com.workfusion.vds.sdk.api.nlp.annotator.Annotator;
import com.workfusion.vds.sdk.api.nlp.model.Document;
import com.workfusion.vds.sdk.api.nlp.model.NamedEntity;

/**
 * Assignment 1
 */
public class Assignment1KeywordNerAnnotator implements Annotator<Document> {

    /**
     * Keywords list to use.
     */
    private static final List<String> STATES = Arrays.asList(
            "Missouri",
            "Nevada",
            "Alaska",
            "Hawaii",
            "Texas",
            "Maryland",
            "Vermont"
    );

    /**
     * Type for {@link NamedEntity} to use.
     */
    private final static String NER_TYPE = "state";

    @Override
    public void process(Document document) {

        //TODO: PUT YOUR CODE HERE

    }

}