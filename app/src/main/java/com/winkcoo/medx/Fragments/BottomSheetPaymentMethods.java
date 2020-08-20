package com.winkcoo.medx.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.PaymentMethodListAdapter;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.PaymentMethodsModel;
import com.winkcoo.medx.widgets.DividerItemDecoration;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomSheetPaymentMethods#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetPaymentMethods extends BottomSheetDialogFragment {
    View view;
    Context context;
    @BindView(R.id.paymentMethodsRecycler)
    RecyclerView paymentMethodsRecycler;
    String fees ;
    String ref ;


    public BottomSheetPaymentMethods(String f,String r) {
        this.fees = f;
        this.ref = r;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment BottomSheetPaymentMethods.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomSheetPaymentMethods newInstance(String f,String r) {

        BottomSheetPaymentMethods fragment = new BottomSheetPaymentMethods(f,r);
        //fees = f;

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_bottom_sheet_payment_methods, container, false);
        context = view.getContext() ;
        ButterKnife.bind(this,view);
        List<PaymentMethodsModel> data = new ArrayList<>();
        PaymentMethodListAdapter mAdapter = new PaymentMethodListAdapter(data,Integer.parseInt(fees),"chamber",ref);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        paymentMethodsRecycler.setLayoutManager(mLayoutManager);
        paymentMethodsRecycler.setItemAnimator(new DefaultItemAnimator());
        paymentMethodsRecycler.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        paymentMethodsRecycler.setAdapter(mAdapter);


        Api.getInstance().get_payment_methods_list(new ApiListener.PaymentMethodsDownloadListener() {
            @Override
            public void onPaymentMethodsDownloadSuccess(List<PaymentMethodsModel> d) {
                Toast.makeText(context, "methods found " + data.size(), Toast.LENGTH_SHORT).show();
                data.addAll(d);
                mAdapter.notifyDataSetChanged();
                // PaymentMethodListAdapter
            }

            @Override
            public void onPaymentMethodsDownloadFailed(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }
        });
        return  view;
    }
}