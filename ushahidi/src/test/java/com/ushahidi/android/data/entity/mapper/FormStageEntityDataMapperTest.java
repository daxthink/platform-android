package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.DefaultConfig;
import com.ushahidi.android.data.entity.FormStageEntity;
import com.ushahidi.android.data.entity.TestEntityFixtures;
import com.ushahidi.android.domain.entity.FormStage;
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
 * Tests {@link FormStageEntityDataMapper}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = DefaultConfig.EMULATE_SDK, constants = BuildConfig.class)
public class FormStageEntityDataMapperTest {

    private FormStage mFormStage;

    private FormStageEntity mFormStageEntity;

    private FormStageEntityDataMapper mFormStageEntityDataMapper;

    @Before
    public void setUp() throws Exception {
        mFormStageEntityDataMapper = new FormStageEntityDataMapper();
    }

    @Test
    public void shouldMapFormStageEntityToFormStage() {
        mFormStage = TestFixtures.getFormStage();
        assertThat(mFormStage).isNotNull();
        mFormStageEntity = mFormStageEntityDataMapper.map(mFormStage);

        assertThat(mFormStageEntity).isNotNull();
        assertThat(mFormStageEntity.getFormId())
                .isEqualTo(TestFixtures.getFormStage().getFormId());
        assertThat(mFormStageEntity.getLabel()).isEqualTo("Test varchar");
        assertThat(mFormStageEntity.getRequired()).isTrue();
        assertThat(mFormStageEntity.getPriority())
                .isEqualTo(TestFixtures.getFormStage().getPriority());
    }

    @Test
    public void shouldMapFormStageToFormStageEntity() {
        mFormStageEntity = TestEntityFixtures.getFormStageEntity();
        assertThat(mFormStageEntity).isNotNull();
        mFormStage = mFormStageEntityDataMapper.map(mFormStageEntity);

        assertThat(mFormStage).isNotNull();
        assertThat(mFormStage.getFormId())
                .isEqualTo(TestEntityFixtures.getFormStageEntity().getFormId());
        assertThat(mFormStage.getLabel())
                .isEqualTo(TestEntityFixtures.getFormStageEntity().getLabel());
        assertThat(mFormStage.getRequired()).isTrue();
        assertThat(mFormStage.getPriority()).isEqualTo(
                TestEntityFixtures.getFormStageEntity().getPriority());
        assertThat(mFormStage.getDeploymentId())
                .isEqualTo(TestEntityFixtures.getFormStageEntity().getDeploymentId());

    }

    @Test
    public void shouldMapFormStageEntityListToFormStageList() {
        List<FormStage> formStageList = null;
        List<FormStageEntity> formStageEntities = new ArrayList<>();
        formStageEntities.add(TestEntityFixtures.getFormStageEntity());
        formStageList = mFormStageEntityDataMapper.map(formStageEntities);
        assertThat(formStageList).isNotNull();
        assertThat(formStageList).isNotEmpty();
        assertThat(formStageList.get(0)).isNotNull();
        assertThat(formStageList.get(0).getDeploymentId()).isNotNull();
        assertThat(formStageList.get(0).getDeploymentId()).isEqualTo(
                TestEntityFixtures.getFormStageEntity().getDeploymentId());
        assertThat(formStageList.get(0).getFormId()).isNotNull();
        assertThat(formStageList.get(0).getFormId())
                .isEqualTo(TestEntityFixtures.getFormStageEntity().getFormId());
        assertThat(formStageList.get(0).getLabel()).isNotNull();
        assertThat(formStageList.get(0).getLabel())
                .isEqualTo(TestEntityFixtures.getFormStageEntity().getLabel());
        assertThat(formStageList.get(0).getRequired()).isTrue();
        assertThat(formStageList.get(0).getPriority())
                .isEqualTo(TestEntityFixtures.getFormStageEntity().getPriority());
    }
}
