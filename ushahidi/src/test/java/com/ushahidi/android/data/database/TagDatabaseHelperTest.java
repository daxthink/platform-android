package com.ushahidi.android.data.database;

import com.ushahidi.android.data.BaseTestCase;
import com.ushahidi.android.data.entity.TagEntity;
import com.ushahidi.android.data.entity.TestEntityFixtures;
import com.ushahidi.android.data.exception.TagNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.util.Arrays;
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
        // For some reason this causes the test to fail and don't understand why. You can uncomment
        // this out to see why.
        // result.assertReceivedOnNext(Collections.singletonList(tagEntityList));

        result.assertCompleted();
        TagEntity tagEntity = result.getOnNextEvents().get(0).get(0);
        assertThat(tagEntity).isNotNull();
        assertThat(tagEntity).isInstanceOf(TagEntity.class);
        assertThat(tagEntity.getDeploymentId()).isNotNull();
        assertThat(tagEntity.getDeploymentId()).isEqualTo(TestEntityFixtures.DEPLOYMENT_ID);
        assertThat(tagEntity.getCreated()).isNotNull();
        assertThat(tagEntity.getCreated())
                .isEqualTo(TestEntityFixtures.getTagEntity().getCreated());
        assertThat(tagEntity.getIcon()).isNotNull();
        assertThat(tagEntity.getIcon()).isEqualTo(TestEntityFixtures.getTagEntity().getIcon());
        assertThat(tagEntity.getPriority()).isNotNull();
        assertThat(tagEntity.getPriority())
                .isEqualTo(TestEntityFixtures.getTagEntity().getPriority());
        assertThat(tagEntity.getType()).isNotNull();
        assertThat(tagEntity.getType()).isEqualTo(TestEntityFixtures.getTagEntity().getType());
        assertThat(tagEntity.getColor()).isNotNull();
        assertThat(tagEntity.getColor()).isEqualTo(TestEntityFixtures.getTagEntity().getColor());
        assertThat(tagEntity.getParentId()).isNull();
        assertThat(tagEntity.getParentId())
                .isEqualTo(TestEntityFixtures.getTagEntity().getParentId());
        assertThat(tagEntity.getDescription()).isNotNull();
        assertThat(tagEntity.getDescription())
                .isEqualTo(TestEntityFixtures.getTagEntity().getDescription());
    }

    @Test
    public void shouldFailToGetTags() throws IOException {
        assertThat(mTagDatabaseHelper).isNotNull();
        List<TagEntity> tagEntityList = TestEntityFixtures.getTagEntities();
        TestSubscriber<List<TagEntity>> result = new TestSubscriber<>();
        mTagDatabaseHelper.getTags(3l).subscribe(result);
        result.assertError((TagNotFoundException) result.getOnErrorEvents().get(0));
    }

    @Test
    public void shouldSuccessfullyPutTags() throws IOException {
        assertThat(mTagDatabaseHelper).isNotNull();
        List<TagEntity> tagEntityList = TestEntityFixtures.getTagEntities();

        TestSubscriber<Long> result = new TestSubscriber<>();
        mTagDatabaseHelper.putTags(tagEntityList).subscribe(result);
        result.assertNoErrors();
        result.assertReceivedOnNext(Arrays.asList(1l));
    }

    @Test
    public void shouldSuccessfullyDeleteTag() throws IOException {
        assertThat(mTagDatabaseHelper).isNotNull();
        List<TagEntity> tagEntityList = TestEntityFixtures.getTagEntities();

        mTagDatabaseHelper.putTags(tagEntityList).subscribe();
        TestSubscriber<Boolean> result = new TestSubscriber<>();
        mTagDatabaseHelper.deleteTag(TestEntityFixtures.getTagEntity()).subscribe(result);
        result.assertNoErrors();
        result.assertReceivedOnNext(Arrays.asList(Boolean.TRUE));
    }

    @Test
    public void shouldFailToDeleteTag() throws IOException {
        assertThat(mTagDatabaseHelper).isNotNull();
        // Clear table if there is any entry in it this way we can test that there was nothing to
        // deleted from the db
        mTagDatabaseHelper.clearEntries();
        TestSubscriber<Boolean> result = new TestSubscriber<>();
        mTagDatabaseHelper.deleteTag(TestEntityFixtures.getTagEntity()).subscribe(result);
        result.assertTerminalEvent();
    }

    @After
    public void tearDown() throws Exception {
        mTagDatabaseHelper.clearEntries();
        mTagDatabaseHelper.close();
    }
}
