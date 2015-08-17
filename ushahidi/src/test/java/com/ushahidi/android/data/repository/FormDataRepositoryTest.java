package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.BaseTestCase;
import com.ushahidi.android.data.entity.FormEntity;
import com.ushahidi.android.data.entity.TestEntityFixtures;
import com.ushahidi.android.data.entity.mapper.FormEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.form.FormDataSource;
import com.ushahidi.android.data.repository.datasource.form.FormDataSourceFactory;
import com.ushahidi.android.domain.entity.Form;
import com.ushahidi.android.domain.entity.From;

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
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormDataRepositoryTest extends BaseTestCase {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private FormDataRepository mFormDataRepository;

    @Mock
    private FormDataSourceFactory mMockFormDataSourceFactory;

    @Mock
    private FormEntityDataMapper mMockFormEntityMapper;

    @Mock
    private FormDataSource mMockDataSource;

    @Mock
    private Form mMockForm;

    @Mock
    private FormEntity mMockFormEntity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        clearSingleton(FormDataRepository.class);
        mFormDataRepository = new FormDataRepository(mMockFormDataSourceFactory,
                mMockFormEntityMapper);
        given(mMockFormDataSourceFactory.createDatabaseDataSource())
                .willReturn(mMockDataSource);
    }

    @Test
    public void getFormFromDatabase() {
        List<FormEntity> formEntityList = new ArrayList<>();
        formEntityList.add(new FormEntity());
        given(mMockDataSource.getForms(TestEntityFixtures.DEPLOYMENT_ID))
                .willReturn(Observable.just(formEntityList));

        mFormDataRepository.getForms(TestEntityFixtures.DEPLOYMENT_ID, From.DATABASE);

        verify(mMockFormDataSourceFactory).createDatabaseDataSource();
        verify(mMockDataSource).getForms(TestEntityFixtures.DEPLOYMENT_ID);

    }

    @Test
    public void shouldSuccessfullyAddAForm() {
        given(mMockDataSource.putForm(mMockFormEntity)).willReturn(
                Observable.just(1l));
        given(mMockFormEntityMapper.map(mMockForm)).willReturn(mMockFormEntity);

        mFormDataRepository.putForm(mMockForm);

        verify(mMockFormDataSourceFactory).createDatabaseDataSource();
        verify(mMockDataSource).putForm(mMockFormEntity);
    }
}
