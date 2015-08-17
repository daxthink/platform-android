package com.ushahidi.android.data.repository;

import com.ushahidi.android.data.BaseTestCase;
import com.ushahidi.android.data.entity.FormEntity;
import com.ushahidi.android.data.entity.mapper.FormEntityDataMapper;
import com.ushahidi.android.data.repository.datasource.form.FormDataSource;
import com.ushahidi.android.data.repository.datasource.form.FormDataSourceFactory;
import com.ushahidi.android.domain.entity.Form;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class FormDataRepositoryTest extends BaseTestCase {

    @Rule
    private ExpectedException expectedException = ExpectedException.none();

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

    }
}
