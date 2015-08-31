package com.ushahidi.android.data.repository.datasource.deployment;

import com.ushahidi.android.data.BaseTestCase;
import com.ushahidi.android.data.database.DeploymentDatabaseHelper;
import com.ushahidi.android.data.entity.DeploymentEntity;
import com.ushahidi.android.data.entity.TestEntityFixtures;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentDatabaseDataSourceTest extends BaseTestCase {

    @Mock
    private DeploymentDatabaseHelper mMockDeploymentDatabaseHelper;

    private DeploymentDatabaseDataSource mDeploymentDatabaseDataSource;

    private static final Long NUM_ROW = 1l;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mDeploymentDatabaseDataSource = new DeploymentDatabaseDataSource(
                mMockDeploymentDatabaseHelper);
    }

    @Test
    public void shouldCreateDeploymentDatabaseDataSourceObject() {
        assertThat(mDeploymentDatabaseDataSource).isNotNull();
    }

    @Test
    public void shouldGetDeploymentEntityList() {
        List<DeploymentEntity> deploymentEntityList = TestEntityFixtures.getDeploymentEntityList();
        assertThat(deploymentEntityList).isNotNull();
        assertThat(deploymentEntityList).isNotEmpty();
        given(mMockDeploymentDatabaseHelper.getDeployments())
                .willReturn(Observable.just(deploymentEntityList));
        mDeploymentDatabaseDataSource.getDeploymentEntityList();

        verify(mMockDeploymentDatabaseHelper).getDeployments();
    }

    @Test
    public void shouldGetDeploymentEntity() {
        DeploymentEntity deploymentEntity = getDeploymentEntity();
        given(mMockDeploymentDatabaseHelper
                .getDeployment(TestEntityFixtures.DEPLOYMENT_ID)).willReturn(
                Observable.just(deploymentEntity));
        mDeploymentDatabaseDataSource.getDeploymentEntity(TestEntityFixtures.DEPLOYMENT_ID);
        verify(mMockDeploymentDatabaseHelper).getDeployment(TestEntityFixtures.DEPLOYMENT_ID);
    }

    @Test
    public void shouldGetDeploymentByItsStatus() {
        DeploymentEntity deploymentEntity = getDeploymentEntity();

        given(mMockDeploymentDatabaseHelper.getByStatus(DeploymentEntity.Status.ACTIVATED))
                .willReturn(
                        Observable.just(deploymentEntity));
        mDeploymentDatabaseDataSource.getByStatus(DeploymentEntity.Status.ACTIVATED);
        verify(mMockDeploymentDatabaseHelper).getByStatus(DeploymentEntity.Status.ACTIVATED);
    }

    @Test
    public void shouldAddDeploymentEntity() {
        DeploymentEntity deploymentEntity = getDeploymentEntity();
        given(mMockDeploymentDatabaseHelper.put(deploymentEntity))
                .willReturn(Observable.just(NUM_ROW));
        mDeploymentDatabaseDataSource.addDeploymentEntity(deploymentEntity);
        verify(mMockDeploymentDatabaseHelper).put(deploymentEntity);
    }

    @Test
    public void shouldUpdateDeploymentEntity() {
        DeploymentEntity deploymentEntity = getDeploymentEntity();
        given(mMockDeploymentDatabaseHelper.put(deploymentEntity))
                .willReturn(Observable.just(NUM_ROW));
        mDeploymentDatabaseDataSource.updateDeploymentEntity(deploymentEntity);
        verify(mMockDeploymentDatabaseHelper).put(deploymentEntity);
    }

    @NonNull
    private DeploymentEntity getDeploymentEntity() {
        DeploymentEntity deploymentEntity = TestEntityFixtures.getDeploymentEntity();
        assertThat(deploymentEntity).isNotNull();
        return deploymentEntity;
    }

    @Test
    public void shouldDeleteDeploymentEntity() {
        given(mMockDeploymentDatabaseHelper.deleteDeployment(TestEntityFixtures.DEPLOYMENT_ID))
                .willReturn(Observable.just(NUM_ROW));
        mDeploymentDatabaseDataSource.deleteDeploymentEntity(TestEntityFixtures.DEPLOYMENT_ID);
        verify(mMockDeploymentDatabaseHelper).deleteDeployment(TestEntityFixtures.DEPLOYMENT_ID);
    }
}
