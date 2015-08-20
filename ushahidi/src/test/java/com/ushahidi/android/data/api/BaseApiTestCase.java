package com.ushahidi.android.data.api;

import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.ushahidi.android.data.BaseTestCase;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executor;

import static org.mockito.Mockito.spy;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class BaseApiTestCase extends BaseTestCase {

    protected MockWebServer mMockWebServer;

    protected Executor httpExecutor = spy(new SynchronousExecutor());

    protected Executor callbackExecutor = spy(new SynchronousExecutor());

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        mMockWebServer = new MockWebServer();
    }

    public class SynchronousExecutor implements Executor {

        @Override
        public void execute(Runnable runnable) {
            runnable.run();
        }
    }
}
