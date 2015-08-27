package com.ushahidi.android.data.api.model;

import com.ushahidi.android.data.api.BaseApiTestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static com.ushahidi.android.data.TestHelper.getResource;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class PostsTest extends BaseApiTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldSuccessfullyDeserializePosts() throws IOException {
        final String postJson = getResource("posts.json");
        final Posts posts = gson.fromJson(postJson, Posts.class);
        assertThat(posts).isNotNull();
        assertThat(posts.getPosts()).isNotNull();
        assertThat(posts.getPosts().size()).isEqualTo(31);
        assertThat(posts.getPosts().get(0)._id).isEqualTo(10681);
        assertThat(posts.getPosts().get(0).getUser()._id).isEqualTo(5);
        assertThat(posts.getPosts().get(0).getContent()).isEqualTo("want 5 people, comment below");
        assertThat(posts.getPosts().get(0).getAuthorEmail()).isNull();
        assertThat(posts.getPosts().get(0).getAuthorRealname()).isNull();
        assertThat(posts.getPosts().get(0).getCreated()).isNotNull();
        assertThat(posts.getPosts().get(0).getUpdated()).isNotNull();
        assertThat(posts.getPosts().get(0).getValues()).isNotNull();
        assertThat(posts.getPosts().get(0).getValues().getValues())
                .isEqualTo(
                        "{\"where--when\":[\"2015-08-04 21:00:00\"],\"location\":[{\"lon\":12.452835,\"lat\":41.903491}]}");
        System.out.println(posts.getPosts().get(0).getPostTagEntityList());
        assertThat(posts.getPosts().get(0).getPostTagEntityList()).isNotNull();
        assertThat(posts.getPosts().get(0).getPostTagEntityList()).isNotEmpty();
        assertThat(posts.getPosts().get(0).getPostTagEntityList().get(0).getTagId()).isEqualTo(361);
    }
}
