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
    }
}
