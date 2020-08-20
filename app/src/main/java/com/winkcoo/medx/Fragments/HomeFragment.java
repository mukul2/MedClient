package com.winkcoo.medx.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.winkcoo.medx.Activity.AmbulanceActivity;
import com.winkcoo.medx.Activity.ChatListActivity;
import com.winkcoo.medx.Activity.GuideLineActivity;
import com.winkcoo.medx.Activity.OnlineDoctorsActivity;
import com.winkcoo.medx.Activity.SpecialistActivity;
import com.winkcoo.medx.Activity.SubscriptionActivityPatient;
import com.winkcoo.medx.Activity.VideoCallAppointmentList;
import com.winkcoo.medx.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends Fragment {
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


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_fragment, container, false);
        context = v.getContext();

        ButterKnife.bind(this, v);
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
