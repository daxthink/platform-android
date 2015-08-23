package com.ushahidi.android.data.entity;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.DefaultConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class FormEntityTest {

    @Test
    public void shouldSetFormEntityTest() {
        FormEntity formEntity = TestEntityFixtures.getFormEntity();
        assertThat(formEntity).isNotNull();
        assertThat(formEntity).isInstanceOf(FormEntity.class);
        assertThat(formEntity.getName()).isNotNull();
        assertThat(formEntity.getName()).isEqualTo(TestEntityFixtures.getFormEntity().getName());
        assertThat(formEntity.getDeploymentId()).isNotNull();
        assertThat(formEntity.getDeploymentId()).isEqualTo(
                TestEntityFixtures.getFormEntity().getDeploymentId());
        assertThat(formEntity.getDescription()).isNotNull();
        assertThat(formEntity.getDescription())
                .isEqualTo(TestEntityFixtures.getFormEntity().getDescription());
        assertThat(formEntity.getCreated()).isNotNull();
        assertThat(formEntity.getCreated())
                .isEqualTo(TestEntityFixtures.getFormEntity().getCreated());
        assertThat(formEntity.getUpdated()).isNotNull();
        assertThat(formEntity.getUpdated())
                .isEqualTo(TestEntityFixtures.getFormEntity().getUpdated());
        assertThat(formEntity.isDisabled()).isTrue();
        assertThat(formEntity.getFormAttributeEntities()).isNotNull();
        assertThat(formEntity.getFormAttributeEntities()).isNotEmpty();
        assertThat(formEntity.getFormAttributeEntities().get(0)).isNotNull();
        assertThat(formEntity.getFormAttributeEntities().get(0).getKey()).isEqualTo("test_varchar");
        assertThat(formEntity.getFormAttributeEntities().get(0).getFormId())
                .isEqualTo(1l);
        assertThat(formEntity.getFormAttributeEntities().get(0).getKey())
                .isEqualTo("test_varchar");
        assertThat(formEntity.getFormAttributeEntities().get(0).getLabel())
                .isEqualTo("Test varchar");
        assertThat(formEntity.getFormAttributeEntities().get(0).getRequired())
                .isFalse();
        assertThat(formEntity.getFormAttributeEntities().get(0).getPriority())
                .isEqualTo(1);
        assertThat(formEntity.getFormAttributeEntities().get(0).getCardinality())
                .isEqualTo(1);
        assertThat(formEntity.getFormAttributeEntities().get(0).getOptions()).isNull();
    }
}
