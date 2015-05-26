package com.ushahidi.android.data.repository;

import com.ushahidi.android.domain.entity.Deployment;
import com.ushahidi.android.domain.repository.DeploymentRepository;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

import static com.google.common.truth.Truth.assertThat;

/**
 * Test suite for {@link DeploymentDataRepository}
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class DeploymentDataRepositoryTest implements DeploymentRepository {

    Deployment mDeployment = new Deployment();

    @Inject
    public DeploymentDataRepositoryTest() {
        // Seed some deployment Data
        mDeployment._id = 2l;
        mDeployment.setStatus(Deployment.Status.ACTIVATED);
        mDeployment.setTitle("title");
        mDeployment.setUrl("http://test.com");
    }

    @Test
    public void testGetByStatus() {
        assertThat(getByStatus(Deployment.Status.ACTIVATED)).isNotNull();
    }

    @Override
    public Observable<Deployment> getByStatus(Deployment.Status status) {
        return getDeployment();
    }

    @Override
    public Observable<List<Deployment>> getEntities() {
        return Observable.create(new Observable.OnSubscribe<List<Deployment>>() {
            @Override
            public void call(Subscriber<? super List<Deployment>> subscriber) {
                List<Deployment> deployments = new ArrayList<>();
                deployments.add(mDeployment);
                subscriber.onNext(deployments);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Deployment> getEntity(Long aLong) {
        return getDeployment();
    }

    @Override
    public Observable<Long> addEntity(Deployment deployment) {
        return deployment();
    }

    @Override
    public Observable<Long> updateEntity(Deployment deployment) {
        return deployment();
    }

    @Override
    public Observable<Long> deleteEntity(Long aLong) {
        return deployment();
    }

    private Observable<Long> deployment() {
        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                subscriber.onNext(1l);
                subscriber.onCompleted();
            }
        });
    }

    private Observable<Deployment> getDeployment() {
        return Observable.create(new Observable.OnSubscribe<Deployment>() {
            @Override
            public void call(Subscriber<? super Deployment> subscriber) {
                subscriber.onNext(mDeployment);
                subscriber.onCompleted();
            }
        });
    }
}
