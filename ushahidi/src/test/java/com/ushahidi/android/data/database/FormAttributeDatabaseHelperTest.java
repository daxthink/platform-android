package com.ushahidi.android.data.database;

import com.ushahidi.android.data.BaseTestCase;
import com.ushahidi.android.data.api.model.FormAttributes;
import com.ushahidi.android.data.entity.FormAttributeEntity;
import com.ushahidi.android.data.entity.TestEntityFixtures;
import com.ushahidi.android.data.exception.FormAttributeNotFoundException;

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
public class FormAttributeDatabaseHelperTest extends BaseTestCase {

    private FormAttributeDatabaseHelper mFormAttributeDatabaseHelper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mFormAttributeDatabaseHelper = new FormAttributeDatabaseHelper(RuntimeEnvironment.application);
    }

    @Test
    public void shouldSuccessfullyGetFormAttributeAttributes() throws IOException {
        assertThat(mFormAttributeDatabaseHelper).isNotNull();
        List<FormAttributeEntity> formAttributeList = getFormAttributeEntities();
        assertThat(formAttributeList).isNotNull();
        mFormAttributeDatabaseHelper.putFormAttributeEntity(formAttributeList).subscribe();
        TestSubscriber<List<FormAttributeEntity>> result = new TestSubscriber<>();
        mFormAttributeDatabaseHelper.getFormAttributeEntity(TestEntityFixtures.DEPLOYMENT_ID,
                TestEntityFixtures.ID).subscribe(result);
        result.assertNoErrors();
        result.assertCompleted();
        FormAttributeEntity formAttributeEntity = result.getOnNextEvents().get(0).get(0);
        assertThat(formAttributeEntity).isNotNull();
        assertThat(formAttributeEntity.getKey()).isNotNull();
        assertThat(formAttributeEntity.getKey()).isEqualTo("location");
        assertThat(formAttributeEntity.getLabel()).isEqualTo("Location");
        assertThat(formAttributeEntity.getInput())
                .isEqualTo(FormAttributeEntity.Input.LOCATION);
        assertThat(formAttributeEntity.getRequired()).isFalse();
        assertThat(formAttributeEntity.getPriority()).isEqualTo(99);
        assertThat(formAttributeEntity.getCardinality()).isEqualTo(1);
    }

    @Test
    public void shouldFailToGetFormAttributes() throws IOException {
        assertThat(mFormAttributeDatabaseHelper).isNotNull();
        List<FormAttributeEntity> formAttributeEntityList = getFormAttributeEntities();
        TestSubscriber<List<FormAttributeEntity>> result = new TestSubscriber<>();
        mFormAttributeDatabaseHelper.getFormAttributeEntity(3l, 2l).subscribe(result);
        result.assertError((FormAttributeNotFoundException) result.getOnErrorEvents().get(0));
    }

    @Test
    public void shouldSuccessfullyPutFormAttributes() throws IOException {
        assertThat(mFormAttributeDatabaseHelper).isNotNull();
        List<FormAttributeEntity> formAttributeEntityList = getFormAttributeEntities();

        TestSubscriber<Long> result = new TestSubscriber<>();
        mFormAttributeDatabaseHelper.putFormAttributeEntity(formAttributeEntityList).subscribe(result);
        result.assertNoErrors();
        result.assertReceivedOnNext(Arrays.asList((long) formAttributeEntityList.size()));
    }

    @After
    public void tearDown() throws Exception {
        mFormAttributeDatabaseHelper.clearEntries();
        mFormAttributeDatabaseHelper.close();
    }

    private List<FormAttributeEntity> getFormAttributeEntities() throws IOException {
        final String formAttributeJson = getResource("form_attribute.json");
        final FormAttributes formAttributes = gson.fromJson(formAttributeJson, FormAttributes.class);
        List<FormAttributeEntity> formAttributeEntityList = new ArrayList<>(formAttributes.getFormAttributes().size());
        for (FormAttributeEntity formAttributeEntity : formAttributes.getFormAttributes()) {
            formAttributeEntity.setFormId(TestEntityFixtures.ID);
            formAttributeEntity.setDeploymentId(TestEntityFixtures.DEPLOYMENT_ID);
            formAttributeEntityList.add(formAttributeEntity);
        }
        return formAttributeEntityList;
    }
}
