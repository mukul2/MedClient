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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.SessionManager;
import com.winkcoo.medx.adapter.DepartmentsAdapter;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.DeptModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.Data.TYPE_OF_ACTIVITY;
import static com.winkcoo.medx.Data.DataStore.TOKEN;

public class SpecialistActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.cardSearch)
    CardView cardSearch;


    Context context = this;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        //Api.getInstance().getDepartMentsList(TOKEN,this);
        setUpStatusbar();
        Api.getInstance().getDepList(TOKEN, new ApiListener.DeptDownloadListener() {
            @Override
            public void onDepartmentDownloadSuccess(List<DeptModel> list) {
             //   Toast.makeText(context, ""+list.size(), Toast.LENGTH_SHORT).show();
                        TYPE_OF_ACTIVITY="Chambers";

        DepartmentsAdapter mAdapter = new DepartmentsAdapter(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);

            }

            @Override
            public void onDepartmentDownloadFailed(String msg) {

            }
        });
        cardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TYPE_OF_ACTIVITY="Chambers";
                startActivity(new Intent(context,DoctorSearchActivity.class));
            }
        });


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


//
//    @Override
//    public void onAppointmentSearchSuccess(List<DepartmentModel> list) {
//        TYPE_OF_ACTIVITY="Chambers";
//
//        DepartmentsAdapter mAdapter = new DepartmentsAdapter(list);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//        recycler_view.setLayoutManager(mLayoutManager);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//        recycler_view.setAdapter(mAdapter);
//
//    }
//
//    @Override
//    public void onAppointmentSearchFailed(String msg) {
//        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//
//    }
}
