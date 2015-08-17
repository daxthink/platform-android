package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.BaseTestCase;
import com.ushahidi.android.data.entity.DeploymentEntity;
import com.ushahidi.android.data.entity.mapper.DeploymentEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.deployment.DeploymentDataSource;
import com.ushahidi.android.data.repository.datasource.deployment.DeploymentDataSourceFactory;
import com.ushahidi.android.domain.entity.Deployment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Test suite for {@link DeploymentDataRepository}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentDataRepositoryTest extends BaseTestCase {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private DeploymentDataRepository mDeploymentDataRepository;

    @Mock
    private DeploymentDataSourceFactory mMockDeploymentDataSourceFactory;

    @Mock
    private DeploymentEntityDataMapper mMockDeploymentEntityMapper;

    @Mock
    private DeploymentDataSource mMockDataSource;

    @Mock
    private Deployment mMockDeployment;

    @Mock
    private DeploymentEntity mMockDeploymentEntity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        clearSingleton(DeploymentDataRepository.class);
        mDeploymentDataRepository = new DeploymentDataRepository(mMockDeploymentDataSourceFactory,
                mMockDeploymentEntityMapper);
        given(mMockDeploymentDataSourceFactory.createDatabaseDataSource()).willReturn(
                mMockDataSource);
    }

    @Test
    public void shouldSuccessfullyAddADeployment() {
        given(mMockDataSource.addDeploymentEntity(mMockDeploymentEntity))
                .willReturn(Observable.just(1l));
        given(mMockDeploymentEntityMapper.map(mMockDeployment)).willReturn(mMockDeploymentEntity);

        mDeploymentDataRepository.addEntity(mMockDeployment);

        verify(mMockDeploymentDataSourceFactory).createDatabaseDataSource();
        verify(mMockDataSource).addDeploymentEntity(mMockDeploymentEntity);
    }

    @Test
    public void shouldSuccessfullyUpdateADeployment() {
        given(mMockDataSource.updateDeploymentEntity(mMockDeploymentEntity)).willReturn(
                Observable.just(1l));
        given(mMockDeploymentEntityMapper.map(mMockDeployment)).willReturn(mMockDeploymentEntity);

        mDeploymentDataRepository.updateEntity(mMockDeployment);

        verify(mMockDeploymentDataSourceFactory).createDatabaseDataSource();
        verify(mMockDataSource).updateDeploymentEntity(mMockDeploymentEntity);
    }

    @Test
    public void shouldSuccessfullyDeleteADeployment() {
        given(mMockDataSource.deleteDeploymentEntity(1l)).willReturn(
                Observable.just(1l));
        mDeploymentDataRepository.deleteEntity(1l);

        verify(mMockDeploymentDataSourceFactory).createDatabaseDataSource();
        verify(mMockDataSource).deleteDeploymentEntity(1l);
    }

    @Test
    public void shouldSuccessfullyGetADeployment() {
        given(mMockDataSource.getDeploymentEntity(1l)).willReturn(
                Observable.just(mMockDeploymentEntity));
        given(mMockDeploymentEntityMapper.map(mMockDeployment)).willReturn(mMockDeploymentEntity);

        mDeploymentDataRepository.getEntity(1l);

        verify(mMockDeploymentDataSourceFactory).createDatabaseDataSource();
        verify(mMockDataSource).getDeploymentEntity(1l);
    }

    @Test
    public void shouldSuccessfullyGetAListOfDeployments() {
        List<DeploymentEntity> deploymentEntities = new ArrayList<>();
        deploymentEntities.add(new DeploymentEntity());
        List<Deployment> deployments = new ArrayList<>();
        deployments.add(new Deployment());

        given(mMockDataSource.getDeploymentEntityList()).willReturn(
                Observable.just(deploymentEntities));
        given(mMockDeploymentEntityMapper.map(deploymentEntities)).willReturn(deployments);

        mDeploymentDataRepository.getEntities();

        verify(mMockDeploymentDataSourceFactory).createDatabaseDataSource();
        verify(mMockDataSource).getDeploymentEntityList();
    }

    @Test
    public void shouldSuccessfullyGetADeploymentByItsStatus() {
        DeploymentEntity deploymentEntity = new DeploymentEntity();
        deploymentEntity.setStatus(DeploymentEntity.Status.ACTIVATED);
        Deployment deployment = new Deployment();
        deployment.setStatus(Deployment.Status.ACTIVATED);

        given(mMockDataSource.getByStatus(DeploymentEntity.Status.ACTIVATED)).willReturn(
                Observable.just(deploymentEntity));
        given(mMockDeploymentEntityMapper.map(Deployment.Status.ACTIVATED))
                .willReturn(DeploymentEntity.Status.ACTIVATED);
        given(mMockDeploymentEntityMapper.map(deployment)).willReturn(deploymentEntity);

        mDeploymentDataRepository.getByStatus(Deployment.Status.ACTIVATED);

        verify(mMockDeploymentDataSourceFactory).createDatabaseDataSource();
        verify(mMockDeploymentEntityMapper).map(Deployment.Status.ACTIVATED);
        verify(mMockDataSource).getByStatus(DeploymentEntity.Status.ACTIVATED);
    }
}
