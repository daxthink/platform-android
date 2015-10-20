package com.ushahidi.android.data.api.model;

import com.ushahidi.android.data.api.BaseApiTestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static com.ushahidi.android.data.TestHelper.getResource;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormStagesTest extends BaseApiTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldSuccessfullyDeserializeForms() throws IOException {
        final String formStageJson = getResource("form_stage.json");
        final FormStages formStages = gson
                .fromJson(formStageJson, FormStages.class);
        assertThat(formStages).isNotNull();
        assertThat(formStages.getFormStages()).isNotNull();
        assertThat(formStages.getCount()).isEqualTo(1);
        assertThat(formStages.getFormStages().get(0).getLabel()).isEqualTo("Where & when?");
        assertThat(formStages.getFormStages().get(0).getRequired()).isFalse();
        assertThat(formStages.getFormStages().get(0).getPriority()).isEqualTo(99);
    }
}
