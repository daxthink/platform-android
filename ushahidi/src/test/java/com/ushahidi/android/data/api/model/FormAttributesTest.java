package com.ushahidi.android.data.api.model;

import com.ushahidi.android.data.api.BaseApiTestCase;
import com.ushahidi.android.data.entity.FormAttributeEntity;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static com.ushahidi.android.data.TestHelper.getResource;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormAttributesTest extends BaseApiTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldSuccessfullyDeserializeForms() throws IOException {
        final String formAttributeJson = getResource("form_attribute.json");
        final FormAttributes formAttributes = gson
                .fromJson(formAttributeJson, FormAttributes.class);

        assertThat(formAttributes).isNotNull();
        assertThat(formAttributes.getFormAttributes()).isNotNull();
        assertThat(formAttributes.getCount()).isEqualTo(3);
        assertThat(formAttributes.getFormAttributes().get(0).getKey()).isNotNull();
        assertThat(formAttributes.getFormAttributes().get(0).getKey()).isEqualTo("location");
        assertThat(formAttributes.getFormAttributes().get(0).getLabel()).isEqualTo("Location");
        assertThat(formAttributes.getFormAttributes().get(0).getInput())
                .isEqualTo(FormAttributeEntity.Input.LOCATION);
        assertThat(formAttributes.getFormAttributes().get(0).getRequired()).isFalse();
        assertThat(formAttributes.getFormAttributes().get(0).getPriority()).isEqualTo(99);
        assertThat(formAttributes.getFormAttributes().get(0).getCardinality()).isEqualTo(1);
    }
}
