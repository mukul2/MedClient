package com.winkcoo.medx.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.SkillAdapterDoctor;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Activity.DoctorsFullProfileView.SKILLS;


public class PatientDoctorsSkillFragment extends Fragment {
    View v;
    Context context;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;





    public static PatientDoctorsSkillFragment newInstance() {
        PatientDoctorsSkillFragment fragment = new PatientDoctorsSkillFragment();
        return fragment;
    }

    public PatientDoctorsSkillFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.doctors_skill_fragment_by_patient, container, false);
        context=v.getContext();

        ButterKnife.bind(this,v);

        //DrConfirmedActivity
        initRecyclerView();

        return v;
    }



    private void initRecyclerView() {
        SkillAdapterDoctor mAdapter = new SkillAdapterDoctor(SKILLS);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }



}
