package com.ushahidi.android.data.api.model;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.data.api.BaseApiTestCase;

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
        // TODO: Test the remanining fields 
    }
}
