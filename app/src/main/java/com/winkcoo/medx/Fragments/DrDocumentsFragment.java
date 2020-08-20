package com.winkcoo.medx.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.winkcoo.medx.Data.sharedPhotoListener;
import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.GeneralListener;
import com.winkcoo.medx.Utils.MyProgressBar;
import com.winkcoo.medx.Utils.doForMe;
import com.winkcoo.medx.adapter.DocumentLitsAdapterDoctor;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.DocumentModel;
import com.winkcoo.medx.model.StatusMessage;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
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

import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrDocumentsFragment extends Fragment implements View.OnClickListener, ApiListener.DoctorDocDownloadListener {
    View view;
    @BindView(R.id.extendedButton)
    ExtendedFloatingActionButton extendedButton;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context;


    public DrDocumentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dr_documents, container, false);
        ButterKnife.bind(this, view);
        context = view.getContext();
        extendedButton.setOnClickListener(this);
        initPhotoAddedListener();
        download();
        GeneralListener.setNeedRefresh(new GeneralListener.youNeedRefresh() {
            @Override
            public void doRefresh(int value) {
                download();

            }
        });
        return view;
    }

    private void download() {
        Api.getInstance().getAllDocumentOFSingleDoc(TOKEN, USER_ID, this);

    }

    private void initPhotoAddedListener() {
        sharedPhotoListener.setpListenerUri(new sharedPhotoListener.PhotoPickedListenerUri() {
            @Override
            public void onPicSelected(Uri data) {
                Dialog dialog = doForMe.showDialog(context, R.layout.upload_doc_dialog);
                ImageView imageView = (ImageView) dialog.findViewById(R.id.image);
                if (data != null) {
                    Glide.with(context).load(data).into(imageView);
                }
                TextView tv_upload = (TextView) dialog.findViewById(R.id.tv_upload);
                EditText ed_title = (EditText) dialog.findViewById(R.id.ed_title);
                TextView tv_cancel = (TextView) dialog.findViewById(R.id.tv_cancel);
                tv_upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (data != null) {
                            String title = ed_title.getText().toString().trim();
                            MyProgressBar.with(context);
                            File f = new File(data.getPath());
                            MultipartBody.Part photo = null;
                            RequestBody requestFile =
                                    RequestBody.create(MediaType.parse("multipart/form-data"), f);
                            photo = MultipartBody.Part.createFormData("photo", f.getName(), requestFile);
                            Api.getInstance().doctorDocumentUpload(TOKEN, c_m_b(USER_ID), c_m_b(title), photo, new ApiListener.DocDocUploadListener() {
                                @Override
                                public void onDocDocUploadSuccess(StatusMessage data) {
                                    MyProgressBar.dismiss();
                                    if (data.getStatus() == true) {
                                        dialog.dismiss();
                                        Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
                                        GeneralListener.needRefresh.doRefresh(0);


                                    }
                                }

                                @Override
                                public void onDocDocUploadFailed(String msg) {
                                    MyProgressBar.dismiss();
                                    Toast.makeText(context, "Add failed.Try again later", Toast.LENGTH_SHORT).show();


                                }
                            });

                        }
                    }
                });


            }
        });
    }

    private RequestBody c_m_b(String aThis) {
        return
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), aThis);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.extendedButton: {
                askPermission();
                break;

            }
        }

    }

    private void askPermission() {
        Dexter.withActivity(getActivity())
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
                .start(getActivity());
    }

    @Override
    public void onDoctorDocDownloadSuccess(List<DocumentModel> data) {
        //DocumentLitsAdapterDoctor
        


        //

        LinearLayoutManager layoutManager
                = new GridLayoutManager(context, 2);
        recycler_view.setLayoutManager(layoutManager);
        DocumentLitsAdapterDoctor    mAdapter = new DocumentLitsAdapterDoctor(data);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    public void onDoctorDocDownloadFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }
}
