package com.ushahidi.android.domain.entity;

import com.ushahidi.android.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class FormAttributeTest {

    @Test
    public void shouldSetFormAttribute() {
        FormAttribute formAttribute = TestFixtures.getFormAttribute();
        assertThat(formAttribute).isNotNull();
        assertThat(formAttribute.getKey()).isEqualTo("test_varchar");
        assertThat(formAttribute.getFormId())
                .isEqualTo(2l);
        assertThat(formAttribute.getKey())
                .isEqualTo("test_varchar");
        assertThat(formAttribute.getLabel())
                .isEqualTo("Test varchar");
        assertThat(formAttribute.getRequired())
                .isTrue();
        assertThat(formAttribute.getPriority())
                .isEqualTo(1);
        assertThat(formAttribute.getCardinality())
                .isEqualTo(1);
        assertThat(formAttribute.getOptions()).isNull();
    }
}
