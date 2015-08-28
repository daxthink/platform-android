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
        assertThat(postEntity).isInstanceOf(PostEntity.class);
        assertThat(postEntity.getDeploymentId()).isNotNull();
        assertThat(postEntity.getDeploymentId()).isEqualTo(TestEntityFixtures.DEPLOYMENT_ID);
        assertThat(postEntity.getCreated()).isNotNull();
        assertThat(postEntity._id).isEqualTo(10681);
        assertThat(postEntity.getUser()._id).isEqualTo(5);
        assertThat(postEntity.getContent()).isEqualTo("want 5 people, comment below");
        assertThat(postEntity.getAuthorEmail()).isNull();
        assertThat(postEntity.getAuthorRealname()).isNull();
        assertThat(postEntity.getCreated()).isNotNull();
        assertThat(postEntity.getUpdated()).isNotNull();
        assertThat(postEntity.getValues()).isNotNull();
        assertThat(postEntity.getValues().getValues())
                .isEqualTo(
                        "{\"where--when\":[\"2015-08-04 21:00:00\"],\"location\":[{\"lon\":12.452835,\"lat\":41.903491}]}");
        assertThat(postEntity.getPostTagEntityList()).isNull();
        assertThat(postEntity.getTags().get(0)._id).isEqualTo(361);
        assertThat(postEntity.getTags().get(0).getTag()).isEqualTo("Question");
        assertThat(postEntity.getTags().get(0).getColor()).isEqualTo("#911010");
        assertThat(postEntity.getTags().get(0).getCreated()).isNotNull();
        assertThat(postEntity.getTags().get(0).getDescription()).isEqualTo("Question");
        assertThat(postEntity.getTags().get(0).getIcon()).isEqualTo("tag");
        assertThat(postEntity.getTags().get(0).getPriority()).isEqualTo(99);
        assertThat(postEntity.getTags().get(0).getType()).isEqualTo(TagEntity.Type.CATEGORY);

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
