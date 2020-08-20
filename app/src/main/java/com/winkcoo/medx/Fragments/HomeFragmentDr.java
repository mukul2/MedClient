package com.winkcoo.medx.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.winkcoo.medx.Activity.ChatListActivity;
import com.winkcoo.medx.Activity.DrConfirmedActivity;
import com.winkcoo.medx.Activity.DrPendingActivity;
import com.winkcoo.medx.Activity.DrPrescriptionListActivity;
import com.winkcoo.medx.Activity.LoginActivity;
import com.winkcoo.medx.Activity.MySubsCribedPatients;
import com.winkcoo.medx.Activity.PatientPersonalInfoActivity;
import com.winkcoo.medx.Activity.PaymentsHistoryActivity;
import com.winkcoo.medx.Activity.RecheckActivityDr;
import com.winkcoo.medx.Activity.VideoCallAppointmentList;
import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.CustomDrawerButton;
import com.winkcoo.medx.Utils.SessionManager;
import com.winkcoo.medx.adapter.AppointmentSearchDrAdapter;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.TrackModel;
import com.winkcoo.medx.widgets.DividerItemDecoration;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.winkcoo.medx.Data.Data.PHOTO_BASE;
import static com.winkcoo.medx.Data.Data.SESSION_MANAGER;
import static com.winkcoo.medx.Data.Data.USER_ENABLED;
import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;


public class HomeFragmentDr extends Fragment {
    View v;
    Context context;
    @BindView(R.id.linearNew)
    LinearLayout linearNew;
    @BindView(R.id.linerChat)
    ExtendedFloatingActionButton linerChat;
    @BindView(R.id.profilePic)
    CircleImageView profilePic;
    @BindView(R.id.linerVideoCallRequest)
    ExtendedFloatingActionButton linerVideoCallRequest;
    @BindView(R.id.linearPrescription)
    LinearLayout linearPrescription;

    @BindView(R.id.linearPending)
    LinearLayout linearPending;

    @BindView(R.id.ed_search)
    TextView ed_search;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.tv_logout)
    TextView tv_logout;
    @BindView(R.id.linerBodyToHide)
    LinearLayout linerBodyToHide;
    @BindView(R.id.divider)
    View divider;


    @BindView(R.id.customDrawer)
    CustomDrawerButton customDrawerButton;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @BindView(R.id.confirmed)
    ExtendedFloatingActionButton confirmed;
    @BindView(R.id.pending)
    ExtendedFloatingActionButton pending;
    @BindView(R.id.recheck)
    ExtendedFloatingActionButton recheck;
    @BindView(R.id.request)
    ExtendedFloatingActionButton request;
    @BindView(R.id.floatSubscription)
    ExtendedFloatingActionButton floatSubscription;
    @BindView(R.id.piggyBank)
    ExtendedFloatingActionButton piggyBank;


    SessionManager sessionManager;

    List<TrackModel> searchModelList = new ArrayList<>();


    AppointmentSearchDrAdapter mAdapter;

    public static HomeFragmentDr newInstance() {
        HomeFragmentDr fragment = new HomeFragmentDr();
        return fragment;
    }

    public HomeFragmentDr() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dr_fragment_one, container, false);
        context = v.getContext();
        ButterKnife.bind(this, v);
        sessionManager = new SessionManager(context);
        // Glide.with(context).load(PHOTO_BASE + sessionManager.get_userPhoto()).into(profile);
        //  tv_name.setText(sessionManager.getUserName());

        ButterKnife.bind(this, v);
        customDrawerButton.setDrawerLayout(drawer_layout);
        customDrawerButton.getDrawerLayout().addDrawerListener(customDrawerButton);
        customDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDrawerButton.changeState();
            }
        });
        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (USER_ENABLED == true) {
                    startActivity(new Intent(context, DrConfirmedActivity.class));
                } else {
                    Toast.makeText(context, "Your account is not verified yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        linerChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (USER_ENABLED == true) {
                    startActivity(new Intent(context, ChatListActivity.class));
                } else {
                    Toast.makeText(context, "Your account is not verified yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        linerVideoCallRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, VideoCallAppointmentList.class));
            }
        });
//DrPrescriptionListActivity
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (USER_ENABLED == true) {
                    startActivity(new Intent(context, DrPrescriptionListActivity.class));
                } else {
                    Toast.makeText(context, "Your account is not verified yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
//RecheckActivityDr

        recheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (USER_ENABLED == true) {
                    startActivity(new Intent(context, RecheckActivityDr.class));
                } else {
                    Toast.makeText(context, "Your account is not verified yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
//DrPendingActivity
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (USER_ENABLED == true) {
                    startActivity(new Intent(context, DrPendingActivity.class));
                } else {
                    Toast.makeText(context, "Your account is not verified yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().subscribeToTopic(USER_ID);

                sessionManager.setLoggedIn(false);
                startActivity(new Intent(context, LoginActivity.class));
                ((Activity) context).finishAffinity();
            }
        });
        //DrConfirmedActivity
        initRecycler();
        init_search();
//        linerVideoCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (USER_ENABLED == true) {
//                    context.startActivity(new Intent(context, VideoCallActivityDr.class));
//                } else {
//                    Toast.makeText(context, "Your account is not verified yet", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        Glide.with(context).load(PHOTO_BASE + SESSION_MANAGER.get_userPhoto()).into(profilePic);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, PatientPersonalInfoActivity.class));
            }
        });
        floatSubscription.setOnClickListener((View view) -> {
            startActivity(new Intent(context, MySubsCribedPatients.class));

        });
        piggyBank.setOnClickListener((View view) -> {
            startActivity(new Intent(context, PaymentsHistoryActivity.class));
        });
        return v;
    }


    private void initRecycler() {
        mAdapter = new AppointmentSearchDrAdapter(searchModelList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(context).load(PHOTO_BASE + SESSION_MANAGER.get_userPhoto()).into(profilePic);

    }

    private void init_search() {
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String track_id = charSequence.toString();
                if (USER_ENABLED == true) {

                    if (track_id.length() > 0) {

                        Api.getInstance().track(TOKEN, track_id, USER_ID, new ApiListener.TrackListener() {
                            @Override
                            public void onTrackSuccess(List<TrackModel> response) {
                                if (response != null && response.size() > 0 && (response.get(0) != null)) {
                                    Toast.makeText(context, "data has", Toast.LENGTH_SHORT).show();
                                    searchModelList.clear();
                                    searchModelList.addAll(response);
                                    mAdapter.notifyDataSetChanged();
                                    divider.setVisibility(View.VISIBLE);
                                    linerBodyToHide.setVisibility(View.GONE);
                                } else {
                                    divider.setVisibility(View.VISIBLE);
                                    searchModelList.clear();
                                    TrackModel model = new TrackModel();
                                    model.setId(0);
                                    model.setProblems("No result found");
                                    searchModelList.add(model);
                                    mAdapter.notifyDataSetChanged();
                                    Toast.makeText(context, "no result", Toast.LENGTH_SHORT).show();
                                    linerBodyToHide.setVisibility(View.GONE);


                                }

                            }

                            @Override
                            public void onTrackFailed(String msg) {

                            }
                        });

                    } else {
                        linerBodyToHide.setVisibility(View.VISIBLE);
                        divider.setVisibility(View.GONE);

                        searchModelList.clear();
                        mAdapter.notifyDataSetChanged();


                    }
                } else {
                    Toast.makeText(context, "Your account is not verified yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}
