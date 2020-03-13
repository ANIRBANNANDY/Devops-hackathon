package com.workfusion.lab.utils;

import java.util.Collection;

import org.junit.Test;

import com.workfusion.vds.sdk.api.nlp.model.Field;
import com.workfusion.vds.sdk.api.nlp.model.IeDocument;
import com.workfusion.vds.sdk.api.nlp.model.Tag;

import static org.assertj.core.api.Assertions.assertThat;

public class LessonTestUtilsTest {


    @Test
    public void getDocument() throws Exception {
        BaseLessonTest t = new BaseLessonTest();
        IeDocument document = t.getDocument("documents/lesson_4_assignment_1.html");
        Collection<Tag> tags = document.findAll(Tag.class);
        assertThat(tags.size()).isEqualTo(466);

        t.addFields(document, "total");
        Collection<Field> fields = document.findFields("total");
        assertThat(fields.size()).isEqualTo(7);

    }


}
