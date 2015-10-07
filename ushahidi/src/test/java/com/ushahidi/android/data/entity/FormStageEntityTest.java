package com.ushahidi.android.data.entity;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.DefaultConfig;
import com.ushahidi.android.domain.entity.FormStage;
import com.ushahidi.android.domain.entity.TestFixtures;

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
public class FormStageEntityTest {

    @Test
    public void shouldSetFormStageEntity() {
        FormStage formStage = TestFixtures.getFormStage();
        assertThat(formStage).isNotNull();
        assertThat(formStage.getFormId()).isEqualTo(2l);
        assertThat(formStage.getLabel()).isEqualTo("Test varchar");
        assertThat(formStage.getRequired()).isTrue();
        assertThat(formStage.getPriority()).isEqualTo(1);
    }
}
