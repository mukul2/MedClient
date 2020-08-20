package com.winkcoo.medx.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.winkcoo.medx.Data.lis;
import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.ConfirmedAppointmentAdapterDoctor;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.AppointmentModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AppointmentsListFragment extends Fragment implements ApiListener.dataDownloadListener {
    View v;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.tv_no_item)
    TextView tv_no_item;
    ConfirmedAppointmentAdapterDoctor mAdapter;

    public AppointmentsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_appointments_list, container, false);

        ButterKnife.bind(this, v);
        lis.setConfirmedlistener(this);

        return v;
    }


    @Override
    public void onDownloaded(List<AppointmentModel> status) {
        if (status.size() > 0) {
            tv_no_item.setVisibility(View.GONE);

        }
        //  mAdapter = new ConfirmedAppointmentAdapterDoctor(status);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        //recycler_view.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        recycler_view.setAdapter(mAdapter);
        if (status.size() > 0) {
            tv_no_item.setVisibility(View.GONE);
        } else {
            tv_no_item.setVisibility(View.VISIBLE);

        }
    }
}
