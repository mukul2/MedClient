package com.winkcoo.medx.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.ConfirmedAppointmentAdapterPatientNew;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.AppointmentModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.DataStore.USER_ID;

public class PatientConfirmedActivity extends AppCompatActivity implements ApiListener.CommonappointmentDownloadListener{
    Context context=this;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_confirmed);
        ButterKnife.bind(this);
        Api.getInstance().downlaodPaConfirmed(USER_ID,this);


    }

    public void Back(View view) {
        onBackPressed();
    }

    @Override
    public void onAppointmentDownloadSuccess(List<AppointmentModel> list) {
        ConfirmedAppointmentAdapterPatientNew mAdapter = new ConfirmedAppointmentAdapterPatientNew(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onAppointmentDownloadFailed(String msg) {

    }
}
