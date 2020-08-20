package com.winkcoo.medx.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.MySubscribeddAdapterDoctor;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.SubscriptionsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;

public class MySubsCribedPatients extends BaseActivity implements ApiListener.SubscriptionListDownlaodListener {
    Context context = this ;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subs_cribed_patients);
        ButterKnife.bind(this);
        Api.getInstance().get_subscription_list(TOKEN,"doctor",USER_ID,this);
    }

    @Override
    public void onSubscriptionListDownlaodSuccess(List<SubscriptionsModel> data) {
      MySubscribeddAdapterDoctor mAdapter = new MySubscribeddAdapterDoctor(data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);

    }

    @Override
    public void onSubscriptionListDownlaodFailed(String msg) {

    }


    public void back(View view) {
        onBackPressed();
    }
}
