package com.winkcoo.medx.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.Activity.AddNewChamberActivity;
import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.ChambersListAdapterDr;
import com.winkcoo.medx.model.DaysTimeModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Fragments.HomeFragmentDrTwo.CHAMBERLIST;


public class ChamberFragment extends Fragment {
    View v;
    Context context;
    @BindView(R.id.upload)
    ExtendedFloatingActionButton upload;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    List<DaysTimeModel> list = new ArrayList<>();
    List<DaysTimeModel> listReserved = new ArrayList<>();
    List<String> days = new ArrayList<>();
    List<String> timeRange = new ArrayList<>();


    public static ChamberFragment newInstance() {
        ChamberFragment fragment = new ChamberFragment();
        return fragment;
    }

    public ChamberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.chamber_fragment, container, false);
        context = v.getContext();

        ButterKnife.bind(this, v);

        //dayAddDialog
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context, AddNewChamberActivity.class));
            }
        });
       //CHAMBERLIST
        ChambersListAdapterDr mAdapter=new  ChambersListAdapterDr(CHAMBERLIST);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);

        return v;
    }


}
