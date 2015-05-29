package com.ushahidi.android.domain.entity;

import com.ushahidi.android.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link com.ushahidi.android.domain.entity.Deployment}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class DeploymentTest {

    private Deployment mDeployment;

    private static final Long DUMMY_ID = 1l;

    private static final String DUMMY_TITLE = "Dummy Deployment Title";

    private static final Deployment.Status DUMMY_STATUS = Deployment.Status.DEACTIVATED;

    private static final String DUMMY_URL = "http://deployment.com";

    @Before
    public void setUp() {
        mDeployment = new Deployment();
    }

    @Test
    public void shouldSetDeployment() {
        mDeployment._id = DUMMY_ID;
        mDeployment.setTitle(DUMMY_TITLE);
        mDeployment.setStatus(DUMMY_STATUS);
        mDeployment.setUrl(DUMMY_URL);

        assertThat(mDeployment).isNotNull();
        assertThat(mDeployment).isInstanceOf(Deployment.class);
        assertThat(mDeployment._id).isEqualTo(DUMMY_ID);
        assertThat(mDeployment.getTitle()).isSameAs(DUMMY_TITLE);
        assertThat(mDeployment.getStatus()).isEqualTo(DUMMY_STATUS);
        assertThat(mDeployment.getUrl()).isEqualTo(DUMMY_URL);
    }
}