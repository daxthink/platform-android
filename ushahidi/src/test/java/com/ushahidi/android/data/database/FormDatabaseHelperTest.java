package com.ushahidi.android.data.database;

import com.ushahidi.android.data.BaseTestCase;
import com.ushahidi.android.data.api.model.Forms;
import com.ushahidi.android.data.entity.FormEntity;
import com.ushahidi.android.data.entity.FormEntity;
import com.ushahidi.android.data.entity.TestEntityFixtures;
import com.ushahidi.android.data.exception.FormNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.observers.TestSubscriber;

import static com.google.common.truth.Truth.assertThat;
import static com.ushahidi.android.data.TestHelper.getResource;

/**
 * @author Henry Addo
 */
public class FormDatabaseHelperTest extends BaseTestCase {

    private FormDatabaseHelper mFormDatabaseHelper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mFormDatabaseHelper = new FormDatabaseHelper(RuntimeEnvironment.application);
    }

    @Test
    public void shouldSuccessfullyGetForms() throws IOException {
        assertThat(mFormDatabaseHelper).isNotNull();
        List<FormEntity> formList = getFormEntities();
        assertThat(formList).isNotNull();
        mFormDatabaseHelper.put(formList).subscribe();
        TestSubscriber<List<FormEntity>> result = new TestSubscriber<>();
        mFormDatabaseHelper.getForms(TestEntityFixtures.DEPLOYMENT_ID).subscribe(result);
        result.assertNoErrors();
        result.assertCompleted();
        FormEntity formEntity = result.getOnNextEvents().get(0).get(0);
        assertThat(formEntity).isNotNull();
        assertThat(formEntity).isNotNull();
        assertThat(formEntity.getCreated()).isNotNull();
        assertThat(formEntity.getUpdated()).isNotNull();
        assertThat(formEntity.getDescription())
                .isEqualTo("For community actions and  interactions");
        assertThat(formEntity.getName()).isEqualTo("Noticeboard");
        assertThat(formEntity.getDeploymentId()).isEqualTo(TestEntityFixtures.DEPLOYMENT_ID);
    }

    @Test
    public void shouldFailToGetForms() throws IOException {
        assertThat(mFormDatabaseHelper).isNotNull();
        List<FormEntity> formEntityList = getFormEntities();
        TestSubscriber<List<FormEntity>> result = new TestSubscriber<>();
        mFormDatabaseHelper.getForms(3l).subscribe(result);
        result.assertError((FormNotFoundException) result.getOnErrorEvents().get(0));
    }

    @Test
    public void shouldSuccessfullyPutForms() throws IOException {
        assertThat(mFormDatabaseHelper).isNotNull();
        List<FormEntity> formEntityList = getFormEntities();

        TestSubscriber<Long> result = new TestSubscriber<>();
        mFormDatabaseHelper.put(formEntityList).subscribe(result);
        result.assertNoErrors();
        result.assertReceivedOnNext(Arrays.asList((long)formEntityList.size()));
    }

    @After
    public void tearDown() throws Exception {
        mFormDatabaseHelper.clearEntries();
        mFormDatabaseHelper.close();
    }

    private List<FormEntity> getFormEntities() throws IOException {
        final String formJson = getResource("forms.json");
        final Forms forms = gson.fromJson(formJson, Forms.class);
        List<FormEntity> formEntityList = new ArrayList<>(forms.getForms().size());
        for (FormEntity formEntity : forms.getForms()) {
            formEntity.setDeploymentId(TestEntityFixtures.DEPLOYMENT_ID);
            formEntityList.add(formEntity);
        }
        return formEntityList;
    }
}
