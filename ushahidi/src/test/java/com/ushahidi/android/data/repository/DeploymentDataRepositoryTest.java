package com.ushahidi.android.data.repository;

import com.ushahidi.android.BaseTestCase;
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
        given(mMockDeploymentDataSourceFactory.createDatabaseDataSource())
                .willReturn(mMockDataSource);
    }

    @Test
    public void shouldSuccessfullyAddADeployment() {
        given(mMockDataSource.addDeploymentEntity(mMockDeploymentEntity)).willReturn(
                Observable.just(1l));
        given(mMockDeploymentEntityMapper.map(mMockDeployment)).willReturn(mMockDeploymentEntity);
        mDeploymentDataRepository.addEntity(mMockDeployment);

        verify(mMockDeploymentDataSourceFactory).createDatabaseDataSource();
        verify(mMockDataSource).addDeploymentEntity(mMockDeploymentEntity);
    }
}
