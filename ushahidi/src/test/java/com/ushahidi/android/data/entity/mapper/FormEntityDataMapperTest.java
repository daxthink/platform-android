package com.ushahidi.android.data.entity.mapper;

import com.ushahidi.android.BuildConfig;
import com.ushahidi.android.data.entity.FormEntity;
import com.ushahidi.android.data.entity.TestEntityFixtures;
import com.ushahidi.android.domain.entity.Form;
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
 * Tests {@link FormEntityDataMapper}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class FormEntityDataMapperTest {

    private Form mForm;

    private FormEntity mFormEntity;

    private FormEntityDataMapper mFormEntityDataMapper;

    @Before
    public void setUp() throws Exception {
        mFormEntityDataMapper = new FormEntityDataMapper();
    }

    @Test
    public void shouldMapFormEntityToForm() {
        mForm = TestFixtures.getForm();
        mFormEntity = mFormEntityDataMapper.map(mForm);

        assertThat(mFormEntity).isNotNull();
        assertThat(mFormEntity).isInstanceOf(FormEntity.class);
        assertThat(mFormEntity.getName()).isNotNull();
        assertThat(mFormEntity.getName()).isEqualTo(TestFixtures.getForm().getName());
        assertThat(mFormEntity.getDeploymentId()).isNotNull();
        assertThat(mFormEntity.getDeploymentId()).isEqualTo(
                TestFixtures.getForm().getDeploymentId());
        assertThat(mFormEntity.getDescription()).isNotNull();
        assertThat(mFormEntity.getDescription()).isEqualTo(TestFixtures.getForm().getDescription());
        assertThat(mFormEntity.getCreated()).isNotNull();
        assertThat(mFormEntity.getCreated()).isEqualTo(TestFixtures.getForm().getCreated());
        assertThat(mFormEntity.getUpdated()).isNotNull();
        assertThat(mFormEntity.getUpdated()).isEqualTo(TestFixtures.getForm().getUpdated());
        assertThat(mFormEntity.isDisabled()).isTrue();
    }

    @Test
    public void shouldMapFormToFormEntity() {
        mFormEntity = TestEntityFixtures.getFormEntity();
        mForm = mFormEntityDataMapper.map(mFormEntity);

        assertThat(mForm).isNotNull();
        assertThat(mForm).isInstanceOf(Form.class);
        assertThat(mForm.getName()).isNotNull();
        assertThat(mForm.getName()).isEqualTo(TestEntityFixtures.getFormEntity().getName());
        assertThat(mForm.getDeploymentId()).isNotNull();
        assertThat(mForm.getDeploymentId()).isEqualTo(
                TestEntityFixtures.getFormEntity().getDeploymentId());
        assertThat(mForm.getDescription()).isNotNull();
        assertThat(mForm.getDescription())
                .isEqualTo(TestEntityFixtures.getFormEntity().getDescription());
        assertThat(mForm.getCreated()).isNotNull();
        assertThat(mForm.getCreated()).isEqualTo(TestEntityFixtures.getFormEntity().getCreated());
        assertThat(mForm.getUpdated()).isNotNull();
        assertThat(mForm.getUpdated()).isEqualTo(TestEntityFixtures.getFormEntity().getUpdated());
        assertThat(mForm.isDisabled()).isTrue();

    }

    @Test
    public void shouldMapFormEntityListToFormList() {
        List<Form> formList = null;
        List<FormEntity> formEntities = new ArrayList<>();
        formEntities.add(TestEntityFixtures.getFormEntity());
        formList = mFormEntityDataMapper.map(formEntities);

        assertThat(formList).isNotNull();
        assertThat(formList).isNotEmpty();
        assertThat(formList.get(0)).isNotNull();
        assertThat(formList.get(0).getName()).isNotNull();
        assertThat(formList.get(0).getName()).isEqualTo(
                TestEntityFixtures.getFormEntity().getName());
        assertThat(formList.get(0).getDeploymentId()).isNotNull();
        assertThat(formList.get(0).getDeploymentId()).isEqualTo(
                TestEntityFixtures.getFormEntity().getDeploymentId());
        assertThat(formList.get(0).getDescription()).isNotNull();
        assertThat(formList.get(0).getDescription())
                .isEqualTo(TestEntityFixtures.getFormEntity().getDescription());
        assertThat(formList.get(0).getCreated()).isNotNull();
        assertThat(formList.get(0).getCreated())
                .isEqualTo(TestEntityFixtures.getFormEntity().getCreated());
        assertThat(formList.get(0).getUpdated()).isNotNull();
        assertThat(formList.get(0).getUpdated())
                .isEqualTo(TestEntityFixtures.getFormEntity().getUpdated());
        assertThat(formList.get(0).isDisabled()).isTrue();
    }
}
