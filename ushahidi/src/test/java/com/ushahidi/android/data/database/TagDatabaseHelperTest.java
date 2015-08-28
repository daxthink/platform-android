package com.ushahidi.android.data.database;

import com.ushahidi.android.data.BaseTestCase;
import com.ushahidi.android.data.entity.TagEntity;
import com.ushahidi.android.data.entity.TestEntityFixtures;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import rx.observers.TestSubscriber;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TagDatabaseHelperTest extends BaseTestCase {

    private TagDatabaseHelper mTagDatabaseHelper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mTagDatabaseHelper = new TagDatabaseHelper(RuntimeEnvironment.application);
    }

    @Test
    public void shouldSuccessfullyGetTags() throws IOException {
        assertThat(mTagDatabaseHelper).isNotNull();
        List<TagEntity> tagEntityList = TestEntityFixtures.getTagEntities();

        mTagDatabaseHelper.putTags(tagEntityList).subscribe();
        TestSubscriber<List<TagEntity>> result = new TestSubscriber<>();

        mTagDatabaseHelper.getTags(TestEntityFixtures.DEPLOYMENT_ID).subscribe(result);
        result.assertNoErrors();
        result.assertReceivedOnNext(Collections.singletonList(tagEntityList));
        //result.assertValue(TestEntityFixtures.getTagEntities());

    }

    @After
    public void tearDown() throws Exception {
        mTagDatabaseHelper.close();
    }
}
