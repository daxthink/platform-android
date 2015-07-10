/*
 * Copyright (c) 2015 Ushahidi Inc
 *
 * This program is free software: you can redistribute it and/or modify it under
 *  the terms of the GNU Affero General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program in the file LICENSE-AGPL. If not, see
 *  https://www.gnu.org/licenses/agpl-3.0.html
 */

package com.ushahidi.android.presentation.ui.activity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.ushahidi.android.R;
import com.ushahidi.android.presentation.model.DeploymentModel;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Activity for reading a barcode
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class QrcodeReaderActivity extends AppCompatActivity
        implements QRCodeReaderView.OnQRCodeReadListener {

    public static final String INTENT_EXTRA_PARAM_BARCODE_DEPLOYMENT_MODEL
            = "com.ushahidi.android.INTENT_PARAM_BARCODE_DEPLOYMENT_MODEL";

    public static final int QRCODE_READER_REQUEST_CODE = 1;

    @Bind(R.id.qrdecoderview)
    QRCodeReaderView mQRCodeReaderView;

    public static Intent getIntent(final Context context) {
        return new Intent(context, QrcodeReaderActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);
        ButterKnife.bind(this);
        mQRCodeReaderView.setOnQRCodeReadListener(this);
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if (!TextUtils.isEmpty(text)) {
            Gson gson = new Gson();
            DeploymentModel deploymentModel = null;
            try {
                deploymentModel = gson.fromJson(text, DeploymentModel.class);
            } catch (JsonSyntaxException e) {
                // Do nothing
            }

            Intent returnIntent = new Intent();
            if (deploymentModel != null) {
                returnIntent.putExtra(INTENT_EXTRA_PARAM_BARCODE_DEPLOYMENT_MODEL, deploymentModel);
                setResult(RESULT_OK, returnIntent);
            } else {
                setResult(RESULT_CANCELED, returnIntent);
            }
        }
        finish();
    }

    // Called when your device have no camera
    @Override
    public void cameraNotFound() {
        finish();
    }

    // Called when there's no QR codes in the camera preview image
    @Override
    public void QRCodeNotFoundOnCamImage() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mQRCodeReaderView.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mQRCodeReaderView.getCameraManager().stopPreview();
    }
}
