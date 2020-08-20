package com.winkcoo.medx.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.MyProgressBar;
import com.winkcoo.medx.Utils.doForMe;
import com.winkcoo.medx.adapter.PrescriptionAdapterPatientViewOnly;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.PrescriptionModel;
import com.winkcoo.medx.model.StatusMessage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.winkcoo.medx.Data.DataStore.PRESCRIPTION_VIEW_TYPE;
import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;

public class PrescriptionActivityPatient extends AppCompatActivity implements ApiListener.PresCriptionDownloadListenerPatient {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.upload)
    FloatingActionButton upload;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_patient);
        ButterKnife.bind(this);
        setUpStatusbar();
        //getPresCriptionsByPatient
        downloadPrescriptions();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermission();
            }
        });

    }

    private void downloadPrescriptions() {
        Api.getInstance().getPresCriptionsByPatient(TOKEN, USER_ID, "patient", this);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
             /*  resultUri = result.getUri();
                imageView.setImageURI(resultUri);
                */

                initPhotoAddedListener(result.getUri());

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void initPhotoAddedListener(Uri uri) {

        Dialog dialog = doForMe.showDialog(context, R.layout.upload_doc_dialog);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.image);
        if (uri != null) {
            Glide.with(context).load(uri).into(imageView);
        }
        TextView tv_upload = (TextView) dialog.findViewById(R.id.tv_upload);
        EditText ed_title = (EditText) dialog.findViewById(R.id.ed_title);
        TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uri != null) {
                    String title = ed_title.getText().toString().trim();
                    MyProgressBar.with(context);
                    File f = new File(uri.getPath());
                    MultipartBody.Part photo = null;
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), f);
                    photo = MultipartBody.Part.createFormData("photo", f.getName(), requestFile);
                    Api.getInstance().addPrescriptionPhoto(TOKEN, c_m_b(USER_ID), c_m_b(title), photo, new ApiListener.prescriptionUploadListener() {
                        @Override
                        public void onPrescriptionUploadSuccess(StatusMessage data) {
                            MyProgressBar.dismiss();
                            if (data.getStatus() == true) {
                                dialog.dismiss();
                                Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
                                downloadPrescriptions();

                            }
                        }

                        @Override
                        public void onPrescriptionUploadFailed(String msg) {
                            MyProgressBar.dismiss();
                            Toast.makeText(context, "Add failed.Try again later", Toast.LENGTH_SHORT).show();


                        }
                    });

                }
            }
        });


    }

    private RequestBody c_m_b(String aThis) {
        return
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), aThis);
    }


    private void askPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            openCamera();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void openCamera() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }


    public void setUpStatusbar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onPrescriptionDownloadSuccess(List<PrescriptionModel> data) {
        PRESCRIPTION_VIEW_TYPE = "justView";

        PrescriptionAdapterPatientViewOnly mAdapter = new PrescriptionAdapterPatientViewOnly(data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PrescriptionActivityPatient.this);
        StaggeredGridLayoutManager _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    public void onPrescriptionDownloadFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
