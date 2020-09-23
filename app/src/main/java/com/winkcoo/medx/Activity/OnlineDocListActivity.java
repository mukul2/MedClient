package com.winkcoo.medx.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.OnlineDoctorsSearchAdapter;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.OnlineDoctorsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.Data.TYPE_OF_ACTIVITY;
import static com.winkcoo.medx.Data.DataStore.TOKEN;

public class OnlineDocListActivity extends BaseActivity implements ApiListener.DownloadOnlineDocListener {
    Context context = this;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.shimmmar)
    ShimmerFrameLayout shimmmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_doc_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String depID = bundle.getString("depID");
            shimmmar.startShimmer();
            Api.getInstance().getOnLineDoctors(TOKEN, depID, this);
        }
    }

    @Override
    public void onOnlineDocSearchSuccess(List<OnlineDoctorsModel> status) {
        shimmmar.stopShimmer();
        shimmmar.setVisibility(View.GONE);
        TYPE_OF_ACTIVITY = "OnlineDoc";
        OnlineDoctorsSearchAdapter mAdapter = new OnlineDoctorsSearchAdapter(status);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(staggeredGridLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        // recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);
        tv_title.setText("Doctors (" + status.size() + ")");
        if (status.size() == 0) {
            tv_title.setText("Sorry No Doctors found");
        }


    }

    @Override
    public void onOnlineDocSearchFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public void back(View view) {
        onBackPressed();
    }
}
