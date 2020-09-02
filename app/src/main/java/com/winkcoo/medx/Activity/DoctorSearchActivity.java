package com.winkcoo.medx.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.DoctorsSearchAdapter;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.DoctorModel;
import com.winkcoo.medx.model.SearchDoctorModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.Data.TYPE_OF_ACTIVITY;
import static com.winkcoo.medx.Data.DataStore.TOKEN;

public class DoctorSearchActivity extends BaseActivity  {
    @BindView(R.id.ed_key)
    EditText ed_key;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context = this ;
    List<SearchDoctorModel> data = new ArrayList<>();
    DoctorsSearchAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_search);
        ButterKnife.bind(this);
        progressbar.setVisibility(View.GONE);

        initAdapter();
        ed_key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                data.clear();

                String name = charSequence.toString();
                progressbar.setVisibility(View.VISIBLE);
                Api.getInstance().searchDoctors(TOKEN, name, null, new ApiListener.DocSearchListener() {
                    @Override
                    public void onDocSearchSuccess(List<SearchDoctorModel> list) {
                        progressbar.setVisibility(View.GONE);

                        data.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onDocSearchFailed(String msg) {

                    }
                });


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initAdapter() {

        mAdapter = new DoctorsSearchAdapter(data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(staggeredGridLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        // recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);

    }


    public void back(View view) {
        onBackPressed();
    }
}