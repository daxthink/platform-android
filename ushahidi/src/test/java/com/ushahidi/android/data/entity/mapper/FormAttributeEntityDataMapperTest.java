package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.DefaultConfig;
import com.ushahidi.android.data.entity.FormAttributeEntity;
import com.ushahidi.android.data.entity.TestEntityFixtures;
import com.ushahidi.android.domain.entity.FormAttribute;
import com.ushahidi.android.domain.entity.TestFixtures;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests {@link FormAttributeEntityDataMapper}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class FormAttributeEntityDataMapperTest {

    private FormAttribute mFormAttribute;

    private FormAttributeEntity mFormAttributeEntity;

    private FormAttributeEntityDataMapper mFormAttributeEntityDataMapper;

    @Before
    public void setUp() throws Exception {
        mFormAttributeEntityDataMapper = new FormAttributeEntityDataMapper();
    }

    @Test
    public void shouldMapFormAttributeEntityToFormAttribute() {
        mFormAttribute = TestFixtures.getFormAttribute();
        assertThat(mFormAttribute).isNotNull();
        mFormAttributeEntity = mFormAttributeEntityDataMapper.map(mFormAttribute);

        assertThat(mFormAttributeEntity).isNotNull();
        assertThat(mFormAttributeEntity.getKey()).isEqualTo("test_varchar");
        assertThat(mFormAttributeEntity.getFormId())
                .isEqualTo(TestFixtures.getFormAttribute().getFormId());
        assertThat(mFormAttributeEntity.getKey())
                .isEqualTo("test_varchar");
        assertThat(mFormAttributeEntity.getLabel())
                .isEqualTo("Test varchar");
        assertThat(mFormAttributeEntity.getRequired()).isTrue();
        assertThat(mFormAttributeEntity.getPriority())
                .isEqualTo(TestFixtures.getFormAttribute().getPriority());
        assertThat(mFormAttributeEntity.getCardinality())
                .isEqualTo(1);
        assertThat(mFormAttributeEntity.getOptions()).isNull();
    }

    @Test
    public void shouldMapFormAttributeToFormAttributeEntity() {
        mFormAttributeEntity = TestEntityFixtures.getFormAttributeEntity();
        assertThat(mFormAttributeEntity).isNotNull();
        mFormAttribute = mFormAttributeEntityDataMapper.map(mFormAttributeEntity);

        assertThat(mFormAttribute).isNotNull();
        assertThat(mFormAttribute.getKey()).isEqualTo(
                TestEntityFixtures.getFormAttributeEntity().getKey());
        assertThat(mFormAttribute.getFormId())
                .isEqualTo(TestEntityFixtures.getFormAttributeEntity().getFormId());
        assertThat(mFormAttribute.getLabel())
                .isEqualTo(TestEntityFixtures.getFormAttributeEntity().getLabel());
        assertThat(mFormAttribute.getRequired()).isFalse();
        assertThat(mFormAttribute.getPriority()).isEqualTo(
                TestEntityFixtures.getFormAttributeEntity().getPriority());
        assertThat(mFormAttribute.getCardinality())
                .isEqualTo(TestEntityFixtures.getFormAttributeEntity().getCardinality());
        assertThat(mFormAttribute.getOptions()).isNull();
        assertThat(mFormAttribute.getDeploymentId())
                .isEqualTo(TestEntityFixtures.getFormAttributeEntity().getDeploymentId());

    }

    @Test
    public void shouldMapFormAttributeEntityListToFormAttributeList() {
        List<FormAttribute> formAttributeList = null;
        List<FormAttributeEntity> formAttributeEntities = new ArrayList<>();
        formAttributeEntities.add(TestEntityFixtures.getFormAttributeEntity());
        formAttributeList = mFormAttributeEntityDataMapper.map(formAttributeEntities);
        assertThat(formAttributeList).isNotNull();
        assertThat(formAttributeList).isNotEmpty();
        assertThat(formAttributeList.get(0)).isNotNull();
        assertThat(formAttributeList.get(0).getKey()).isNotNull();
        assertThat(formAttributeList.get(0).getKey()).isEqualTo(
                TestEntityFixtures.getFormAttributeEntity().getKey());
        assertThat(formAttributeList.get(0).getDeploymentId()).isNotNull();
        assertThat(formAttributeList.get(0).getDeploymentId()).isEqualTo(
                TestEntityFixtures.getFormAttributeEntity().getDeploymentId());
        assertThat(formAttributeList.get(0).getFormId()).isNotNull();
        assertThat(formAttributeList.get(0).getFormId())
                .isEqualTo(TestEntityFixtures.getFormAttributeEntity().getFormId());
        assertThat(formAttributeList.get(0).getLabel()).isNotNull();
        assertThat(formAttributeList.get(0).getLabel())
                .isEqualTo(TestEntityFixtures.getFormAttributeEntity().getLabel());
        assertThat(formAttributeList.get(0).getRequired()).isFalse();
        assertThat(formAttributeList.get(0).getPriority())
                .isEqualTo(TestEntityFixtures.getFormAttributeEntity().getPriority());
        assertThat(formAttributeList.get(0).getCardinality()).isEqualTo(
                TestEntityFixtures.getFormAttributeEntity().getCardinality());
        assertThat(formAttributeList.get(0).getOptions()).isNull();
    }
}
