package com.ushahidi.android.domain.entity;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.DefaultConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * Test {@link Form}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class FormTest {

    @Test
    public void shouldTestAllFormFieldInitialized() {
        Form form = TestFixtures.getForm();
        assertThat(form).isNotNull();
        assertThat(form).isInstanceOf(Form.class);
        assertThat(form._id).isEqualTo(TestFixtures.getForm()._id);
        assertThat(form.getName()).isEqualTo(TestFixtures.getForm().getName());
        assertThat(form.getDescription()).isEqualTo(TestFixtures.getForm().getDescription());
        assertThat(form.getCreated()).isEqualTo(TestFixtures.getForm().getCreated());
        assertThat(form.getUpdated()).isEqualTo(TestFixtures.getForm().getUpdated());
        assertThat(form.getDeploymentId()).isEqualTo(TestFixtures.getForm().getDeploymentId());
    }
}
