package com.winkcoo.medx.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.winkcoo.medx.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchNewFragments extends Fragment {

    public SearchNewFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_new_fragments, container, false);
    }
}
