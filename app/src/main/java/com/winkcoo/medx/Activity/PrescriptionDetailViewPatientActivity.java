package com.winkcoo.medx.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.MedicinesListAdapter;
import com.winkcoo.medx.widgets.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.Data.PHOTO_BASE;
import static com.winkcoo.medx.Data.DataStore.NOW_SHOWING_PRESCRIPTION;

public class PrescriptionDetailViewPatientActivity extends AppCompatActivity {
    @BindView(R.id.prescriptionImage)
    ImageView prescriptionImage;
    @BindView(R.id.tv_diseases)
    TextView tv_diseases;
    @BindView(R.id.linerPhotoBody)
    LinearLayout linerPhotoBody;
    @BindView(R.id.linearDigitalPrescription)
    LinearLayout linearDigitalPrescription;
    @BindView(R.id.recyclerView)
    RecyclerView recycler_view;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_detail_view_patient);
        ButterKnife.bind(this);
        setUpStatusbar();
        //NOW_SHOWING_PRESCRIPTION
        if (NOW_SHOWING_PRESCRIPTION.getAttachment() != null && NOW_SHOWING_PRESCRIPTION.getAttachment().size() > 0 && NOW_SHOWING_PRESCRIPTION.getAttachment().get(0) != null && NOW_SHOWING_PRESCRIPTION.getAttachment().get(0).getFile() != null) {
            linearDigitalPrescription.setVisibility(View.GONE);
            Glide.with(context).load(PHOTO_BASE + NOW_SHOWING_PRESCRIPTION.getAttachment().get(0).getFile()).into(prescriptionImage);
            prescriptionImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ImageFullScreenActivity.class);
                    intent.putExtra("link", PHOTO_BASE + NOW_SHOWING_PRESCRIPTION.getAttachment().get(0).getFile());
                    startActivity(intent);

                }
            });

        } else {
            linerPhotoBody.setVisibility(View.GONE);
            tv_diseases.setText(NOW_SHOWING_PRESCRIPTION.getDiseases_name());
            if (NOW_SHOWING_PRESCRIPTION.getMedicineInfo() != null) {
                MedicinesListAdapter mAdapter = new MedicinesListAdapter(NOW_SHOWING_PRESCRIPTION.getMedicineInfo());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
                recycler_view.setAdapter(mAdapter);
                recycler_view.setClickable(false);
                recycler_view.setEnabled(false);

        /*    mAdapter.setClickListener(new MedicinesListAdapter.ClickListener() {
                @Override
                public void onClicked() {
                    if (PRESCRIPTION_VIEW_TYPE.equals("review")) {
                        pListener.onPrescriptionSelected(date);


                    } else if (PRESCRIPTION_VIEW_TYPE.equals("justView")) {
                        show_dialog(date);

                    }
                }
            });
            */
            } else {


            }

        }
    }

    public void Back(View view) {
        onBackPressed();
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

}
