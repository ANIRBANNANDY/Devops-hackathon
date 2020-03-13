/*
 * Copyright (C) WorkFusion 2018. All rights reserved.
 */
package com.workfusion.lab.model;

import java.math.BigDecimal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.workfusion.vds.sdk.api.nlp.model.Field;

/**
 * This class represents DTO to store data from {@link Field} for tests.
 */
public class TestField extends TestElement {

    private String name;
    private String value;
    private BigDecimal score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), name, value, score);
    }

    @Override
    public boolean equals(Object obj) {
        TestField other = (TestField) obj;
        return super.equals(obj)
                && Objects.equal(this.name, other.name)
                && Objects.equal(this.value, other.value)
                && Objects.equal(this.score, other.score);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Field.class)
                .add("name", name)
                .add("value", value)
                .add("score", score)
                .add("begin", begin)
                .add("end", end)
                .add("text", text)
                .toString();
    }

}
