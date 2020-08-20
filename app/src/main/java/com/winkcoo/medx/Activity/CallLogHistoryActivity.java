package com.winkcoo.medx.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.SessionManager;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.CallHistoryPatient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CallLogHistoryActivity extends AppCompatActivity implements ApiListener.patientCallLogListener {
    SessionManager sessionManager;
    String USER_ID;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_log_history);
        sessionManager = new SessionManager(this);
        ButterKnife.bind(this);
        setTitle("Call History");
        USER_ID = sessionManager.getUserId();
        Api.getInstance().downloadCallLog(USER_ID, this);
    }

    @Override
    public void onPatientCallLogSuccess(List<CallHistoryPatient> list) {
      /*  CallLogHistoryAdapter mAdapter = new CallLogHistoryAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);
        */
    }

    @Override
    public void onPatientCallLogFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    public void back(View view) {
        onBackPressed();
    }
}
