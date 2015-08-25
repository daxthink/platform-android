package com.ushahidi.android.data.api.model;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.data.api.BaseApiTestCase;
import com.ushahidi.android.data.entity.TestEntityFixtures;
import com.ushahidi.android.domain.entity.TestFixtures;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static com.ushahidi.android.data.TestHelper.getResource;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class FormsTest extends BaseApiTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldSuccessfullyDeserializeForms() throws IOException {
        final String formJson = getResource("forms.json");
        final Forms forms = gson.fromJson(formJson, Forms.class);
        assertThat(forms).isNotNull();
        assertThat(forms.getForms(TestEntityFixtures.DEPLOYMENT_ID)).isNotNull();
        assertThat(forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).size()).isEqualTo(4);
        assertThat(forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getCreated())
                .isNotNull();
        assertThat(forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getUpdated()).isNull();
        assertThat(forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getDescription())
                .isEqualTo("a basic form");
        assertThat(forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getName())
                .isEqualTo("Basic Form");
        assertThat(forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getDeploymentId())
                .isNull();
        assertThat(
                forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getFormAttributeEntities())
                .isNotNull();
        assertThat(
                forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getFormAttributeEntities()
                        .get(0)).isNotNull();
        assertThat(
                forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getFormAttributeEntities()
                        .get(
                                0).getDeploymentId()).isEqualTo(TestEntityFixtures.DEPLOYMENT_ID);
        assertThat(
                forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getFormAttributeEntities()
                        .get(0).getFormId())
                .isEqualTo(1);
        assertThat(
                forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getFormAttributeEntities()
                        .get(0).getKey())
                .isEqualTo("test_varchar");
        assertThat(
                forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getFormAttributeEntities()
                        .get(0).getLabel())
                .isEqualTo("Test varchar");
        assertThat(
                forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getFormAttributeEntities()
                        .get(0).getRequired())
                .isFalse();
        assertThat(
                forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getFormAttributeEntities()
                        .get(0).getPriority())
                .isEqualTo(1);
        assertThat(
                forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getFormAttributeEntities()
                        .get(0).getCardinality())
                .isEqualTo(1);
        assertThat(
                forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(0).getFormAttributeEntities()
                        .get(0).getOptions()).isNull();
        assertThat(
                forms.getForms(TestEntityFixtures.DEPLOYMENT_ID).get(1).getFormAttributeEntities())
                .isNull();
    }
}
