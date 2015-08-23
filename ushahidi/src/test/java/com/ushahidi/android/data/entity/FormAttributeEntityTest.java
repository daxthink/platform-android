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
public class FormAttributeEntityTest {

    @Test
    public void shouldSetFormAttributeEntityTest() {
        FormAttributeEntity formAttributeEntity = TestEntityFixtures.getFormAttributeEntity();
        assertThat(formAttributeEntity).isNotNull();
        assertThat(formAttributeEntity.getKey()).isEqualTo("test_varchar");
        assertThat(formAttributeEntity.getFormId())
                .isEqualTo(1l);
        assertThat(formAttributeEntity.getKey())
                .isEqualTo("test_varchar");
        assertThat(formAttributeEntity.getLabel())
                .isEqualTo("Test varchar");
        assertThat(formAttributeEntity.getRequired())
                .isFalse();
        assertThat(formAttributeEntity.getPriority())
                .isEqualTo(1);
        assertThat(formAttributeEntity.getCardinality())
                .isEqualTo(1);
        assertThat(formAttributeEntity.getOptions()).isNull();
    }
}
