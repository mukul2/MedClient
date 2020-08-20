package com.winkcoo.medx.Activity;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.VideoCallReqListAdapter;
import com.winkcoo.medx.adapter.VideoCallReqListAdapterPatient;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.VideoAppointmentModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;
import static com.winkcoo.medx.Data.DataStore.USER_TYPE;

public class VideoCallAppointmentList extends BaseActivity implements ApiListener.VideoCallReqListDownlaodListener {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call_appointment_list);
        ButterKnife.bind(this);

        if (USER_TYPE.equals("d")) {
            Api.getInstance().get_video_appointment_list(TOKEN, "doctor", USER_ID, this);

        } else {
            Api.getInstance().get_video_appointment_list(TOKEN, "patient", USER_ID, this);

        }

    }


    @Override
    public void onVideoCallReqListDownlaodSuccess(List<VideoAppointmentModel> data) {

        if (USER_TYPE.equals("d")) {
            VideoCallReqListAdapter adapter = new VideoCallReqListAdapter(data);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            //recycler_view_confirmed.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            recycler_view.setAdapter(adapter);

        } else {
            VideoCallReqListAdapterPatient adapter = new VideoCallReqListAdapterPatient(data);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            //recycler_view_confirmed.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

            recycler_view.setAdapter(adapter);
        }


    }

    @Override
    public void onVideoCallReqListDownlaodFailed(String msg) {

    }
}
