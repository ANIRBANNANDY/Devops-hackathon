/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.model;

import com.google.common.base.MoreObjects;
import com.workfusion.vds.sdk.api.nlp.model.Sentence;

/**
 * This class represents DTO to store data from {@link Sentence} for tests.
 */
public class TestSentence extends TestElement {

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Sentence.class)
                .add("begin", begin)
                .add("end", end)
                .add("text", text)
                .toString();
    }
}
