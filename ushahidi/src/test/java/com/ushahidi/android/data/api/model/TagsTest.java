package com.ushahidi.android.data.api.model;

import com.ushahidi.android.data.api.BaseApiTestCase;
import com.ushahidi.android.data.entity.TagEntity;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static com.ushahidi.android.data.TestHelper.getResource;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TagsTest extends BaseApiTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void shouldSuccessfullyDeserializeTagss() throws IOException {
        final String tagJson = getResource("tags.json");
        final Tags tags = gson.fromJson(tagJson, Tags.class);
        assertThat(tags).isNotNull();
        assertThat(tags.getTags()).isNotNull();
        assertThat(tags.getTags().size()).isEqualTo(7);
        assertThat(tags.getTags().get(0)._id).isEqualTo(331);
        assertThat(tags.getTags().get(0).getTag()).isEqualTo("Positive reports");
        assertThat(tags.getTags().get(0).getColor()).isEqualTo("#0c1404");
        assertThat(tags.getTags().get(0).getCreated()).isNotNull();
        assertThat(tags.getTags().get(0).getDescription()).isEqualTo("Positive reports");
        assertThat(tags.getTags().get(0).getIcon()).isEqualTo("tag");
        assertThat(tags.getTags().get(0).getType()).isEqualTo(TagEntity.Type.CATEGORY);
    }
}
