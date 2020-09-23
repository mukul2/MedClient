package com.winkcoo.medx.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.winkcoo.medx.Activity.AmbulanceActivity;
import com.winkcoo.medx.Activity.ChatListActivity;
import com.winkcoo.medx.Activity.GuideLineActivity;
import com.winkcoo.medx.Activity.OnlineDoctorsActivity;
import com.winkcoo.medx.Activity.SpecialistActivity;
import com.winkcoo.medx.Activity.SubscriptionActivityPatient;
import com.winkcoo.medx.Activity.VideoCallAppointmentList;
import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.DepartmentsAdapter;
import com.winkcoo.medx.adapter.DepartmentsAdapterGrid;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.DeptModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winkcoo.medx.Data.Data.TYPE_OF_ACTIVITY;
import static com.winkcoo.medx.Data.DataStore.TOKEN;


public class HomeFragmentAlternativeMedX extends Fragment {
    View v;
    Context context;
    @BindView(R.id.cardChember)
    CardView cardChember;
    @BindView(R.id.cardOnline)
    CardView cardOnline;

    @BindView(R.id.cardChat)
    CardView cardChat;
    @BindView(R.id.cardGuide)
    CardView cardGuide;
    @BindView(R.id.cardVideoAppointment)
    CardView cardVideoAppointment;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.shimmar)
    ShimmerFrameLayout shimmar;


    public static HomeFragmentAlternativeMedX newInstance() {
        HomeFragmentAlternativeMedX fragment = new HomeFragmentAlternativeMedX();
        return fragment;
    }

    public HomeFragmentAlternativeMedX() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_fragment_alter, container, false);
        context = v.getContext();

        ButterKnife.bind(this, v);
        shimmar.startShimmer();

        cardChember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SpecialistActivity.class));
                //  Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
            }
        });
        cardChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ChatListActivity.class));
                //  Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
            }
        });

        cardVideoAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, VideoCallAppointmentList.class));
                //  Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
            }
        });
        //TYPE_OF_ACTIVITY
        Api.getInstance().getDepList(TOKEN, new ApiListener.DeptDownloadListener() {
            @Override
            public void onDepartmentDownloadSuccess(List<DeptModel> list) {
                //Toast.makeText(context, ""+list.size(), Toast.LENGTH_SHORT).show();
                TYPE_OF_ACTIVITY="OnlineDoc";
                shimmar.stopShimmer();
                shimmar.setVisibility(View.GONE);
                DepartmentsAdapterGrid mAdapter = new DepartmentsAdapterGrid(list);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,2);
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.setAdapter(mAdapter);

            }

            @Override
            public void onDepartmentDownloadFailed(String msg) {

            }
        });
        return v;
    }
    //GuideLineActivity


    @OnClick(R.id.cardOnline)
    public void cardGuide() {
        startActivity(new Intent(context, OnlineDoctorsActivity.class));
    }

    @OnClick(R.id.cardGuide)
    public void openGuideline() {
        startActivity(new Intent(context, GuideLineActivity.class));
    }

    @OnClick(R.id.cardChat)
    public void openChatList() {

        //startActivity(new Intent(context, DrChatActivity.class));
    }

    @OnClick(R.id.cardSubscription)
    public void openSubscriptions() {
        startActivity(new Intent(context, SubscriptionActivityPatient.class));
    }

    @OnClick(R.id.cardAmbulance)
    public void openAmbulance() {
        startActivity(new Intent(context, AmbulanceActivity.class));
    }


}
