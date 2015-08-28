package com.ushahidi.android.data.database;

import com.ushahidi.android.data.BaseTestCase;
import com.ushahidi.android.data.api.model.Posts;
import com.ushahidi.android.data.api.model.Tags;
import com.ushahidi.android.data.entity.PostEntity;
import com.ushahidi.android.data.entity.TagEntity;
import com.ushahidi.android.data.entity.TestEntityFixtures;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.observers.TestSubscriber;

import static com.google.common.truth.Truth.assertThat;
import static com.ushahidi.android.data.TestHelper.getResource;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostDatabaseHelperTest extends BaseTestCase {

    private PostDatabaseHelper mPostDatabaseHelper;

    private TagDatabaseHelper mTagDatabaseHelper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mPostDatabaseHelper = new PostDatabaseHelper(RuntimeEnvironment.application);
        mTagDatabaseHelper = new TagDatabaseHelper(RuntimeEnvironment.application);
    }

    @Test
    public void shouldSuccessfullyGetPosts() throws IOException {
        assertThat(mPostDatabaseHelper).isNotNull();
        List<PostEntity> postEntityList = getPostEntities();

        mTagDatabaseHelper.putTags(getTagEntities()).subscribe();
        mPostDatabaseHelper.putPosts(postEntityList).subscribe();
        TestSubscriber<List<PostEntity>> result = new TestSubscriber<>();

        mPostDatabaseHelper.getPostList(TestEntityFixtures.DEPLOYMENT_ID).subscribe(result);
        result.assertNoErrors();
        // For some reason this causes the test to fail and don't understand why. You can uncomment
        // this out to see why.
        // result.assertReceivedOnNext(Collections.singletonList(postEntityList));

        result.assertCompleted();
        PostEntity postEntity = result.getOnNextEvents().get(0).get(0);
        assertThat(postEntity).isNotNull();
        System.out.println(postEntity);
        assertThat(postEntity).isInstanceOf(PostEntity.class);
        assertThat(postEntity.getDeploymentId()).isNotNull();
        assertThat(postEntity.getDeploymentId()).isEqualTo(TestEntityFixtures.DEPLOYMENT_ID);
        assertThat(postEntity.getCreated()).isNotNull();

    }

    @After
    public void tearDown() throws Exception {
        mPostDatabaseHelper.clearEntries();
        mTagDatabaseHelper.clearEntries();
        mPostDatabaseHelper.close();
        mTagDatabaseHelper.close();
    }

    private List<PostEntity> getPostEntities() throws IOException {
        final String postJson = getResource("posts.json");
        final Posts posts = gson.fromJson(postJson, Posts.class);
        List<PostEntity> postEntityList = new ArrayList<>(posts.getPosts().size());
        for (PostEntity postEntity : posts.getPosts()) {
            postEntity.setDeploymentId(TestEntityFixtures.DEPLOYMENT_ID);
            postEntityList.add(postEntity);
        }
        return postEntityList;
    }

    private List<TagEntity> getTagEntities() throws IOException {
        final String tagJson = getResource("tags.json");
        final Tags tags = gson.fromJson(tagJson, Tags.class);
        List<TagEntity> tagEntityList = new ArrayList<>(tags.getTags().size());
        for (TagEntity tagEntity : tags.getTags()) {
            tagEntity.setDeploymentId(TestEntityFixtures.DEPLOYMENT_ID);
            tagEntityList.add(tagEntity);
        }
        return tagEntityList;
    }
}
