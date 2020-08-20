package com.winkcoo.medx.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.SessionManager;
import com.winkcoo.medx.model.AppointmentModel;
import com.winkcoo.medx.model.RecomentationModel;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivityPatient extends AppCompatActivity {
    SessionManager sessionManager;
    Context context = this;
    public static List<AppointmentModel> PENDING_LIST=new ArrayList<>();
    public static List<AppointmentModel> CONFIRMED_LIST=new ArrayList<>();
    public static List<RecomentationModel> RECOMENDED_LIST=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_patient);
        sessionManager=new SessionManager(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait");
       // progressDialog.show();
        //Api.getInstance().getAppointmentsBypatient(sessionManager.getUserId(), this);

    }

    public void openApproved(View view) {
        startActivity(new Intent(this, PatientConfirmedActivity.class));
    }

    public void openPending(View view) {
        startActivity(new Intent(this, PatientPendingActivity.class));
    }

    public void openTestRecomendtions(View view) {
        startActivity(new Intent(this, PatientTestRecomActivity.class));

    }

    public void back(View view) {
        onBackPressed();
    }


}
