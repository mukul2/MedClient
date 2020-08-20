package com.winkcoo.medx.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.winkcoo.medx.R;

import butterknife.ButterKnife;


public class MedicineFragment extends Fragment {
    View v;
    Context context;


    public static MedicineFragment newInstance() {
        MedicineFragment fragment = new MedicineFragment();
        return fragment;
    }

    public MedicineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_medicine, container, false);
        context=v.getContext();

        ButterKnife.bind(this,v);



        return v;
    }





}
