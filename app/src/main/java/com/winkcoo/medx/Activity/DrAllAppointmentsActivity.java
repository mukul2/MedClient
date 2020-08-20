package com.winkcoo.medx.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.SessionManager;
import com.winkcoo.medx.model.AppointmentModel;

import java.util.ArrayList;
import java.util.List;

public class DrAllAppointmentsActivity extends AppCompatActivity {
    SessionManager sessionManager;
    public  static List<AppointmentModel> PENDING=new ArrayList<>();
    public  static List<AppointmentModel> CONFIRMED=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_all_appointments);
        sessionManager=new SessionManager(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
       // progressDialog.show();
        //Api.getInstance().getAppointmentsByDoctor(sessionManager.getUserId(), this);

    }

    public void openApprovedDr(View view) {
        startActivity(new Intent(this,DrConfirmedActivity.class));

    }

    public void openPendingDr(View view) {
        startActivity(new Intent(this,DrPendingActivity.class));
    }

    public void back(View view) {
        onBackPressed();
    }


}
