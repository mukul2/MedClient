package com.winkcoo.medx.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.GeneralListener;
import com.winkcoo.medx.Utils.MyProgressBar;
import com.winkcoo.medx.adapter.EducationsAdapterDoctor;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.StatusMessage;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;
import static com.winkcoo.medx.Fragments.HomeFragmentDrTwo.EDUCATION;


public class EducationFragment extends Fragment {
    View v;
    Context context;
    @BindView(R.id.upload)
    ExtendedFloatingActionButton upload;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;



    public static EducationFragment newInstance() {
        EducationFragment fragment = new EducationFragment();
        return fragment;
    }

    public EducationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_education, container, false);
        ButterKnife.bind(this,v);
        context=v.getContext();
        ButterKnife.bind(this,v);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.add_education_info_dialog);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                CardView cardDone=(CardView)dialog.findViewById(R.id.cardDone);
                TextView ed_title=(TextView)dialog.findViewById(R.id.ed_title);
                TextView ed_body=(TextView)dialog.findViewById(R.id.ed_body);
                cardDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title=ed_title.getText().toString().trim();
                        String body=ed_body.getText().toString().trim();
                        if (title.length()>0 && body.length()>0){
                            MyProgressBar.with(getContext()).show();
                            Api.getInstance().postEducationInfo(TOKEN, USER_ID, title, body, new ApiListener.PostEducationInfoListener() {
                                @Override
                                public void onPostEducationInfoSuccess(StatusMessage status) {
                                    MyProgressBar.dismiss();
                                    if (status.getStatus()==true){
                                        Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        GeneralListener.needRefresh.doRefresh(0);
                                    }else {
                                        Toast.makeText(getContext(), status.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onPostEducationInfoFailed(String msg) {
                                    MyProgressBar.dismiss();
                                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();


                                }
                            });
                        }
                    }
                });


            }
        });

        initRecyclerView();




        return v;
    }

    private void initRecyclerView() {
        EducationsAdapterDoctor mAdapter = new EducationsAdapterDoctor(EDUCATION);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }


}
