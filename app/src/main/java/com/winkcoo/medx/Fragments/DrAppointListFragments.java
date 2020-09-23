package com.winkcoo.medx.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.VideoCallReqListAdapter;
import com.winkcoo.medx.adapter.VideoCallReqListAdapterPatient;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.VideoAppointmentModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;
import static com.winkcoo.medx.Data.DataStore.USER_TYPE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrAppointListFragments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrAppointListFragments extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Map<String, String> data_ = new HashMap<>();
    View view;
    Context context ;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    public DrAppointListFragments() {
        // Required empty public constructor
    }

    public DrAppointListFragments(Map<String, String> d) {
        this.data_ =d ;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrAppointListFragments.
     */
    // TODO: Rename and change types and number of parameters
    public static DrAppointListFragments newInstance(String param1, String param2) {
        DrAppointListFragments fragment = new DrAppointListFragments();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dr_appoint_list_fragments, container, false);
        ButterKnife.bind(this,view);
        context = view.getContext();

        //recyclerview
      // Toast.makeText(context, data_.get("date"), Toast.LENGTH_SHORT).show();

        Api.getInstance().get_video_appointment_list(TOKEN, "doctor", USER_ID,data_.get("date"), new ApiListener.VideoCallReqListDownlaodListener() {
            @Override
            public void onVideoCallReqListDownlaodSuccess(List<VideoAppointmentModel> data) {
               // Toast.makeText(context, ""+data.size(), Toast.LENGTH_SHORT).show();

                VideoCallReqListAdapter adapter = new VideoCallReqListAdapter(data);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                recyclerview.setLayoutManager(mLayoutManager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                //recycler_view_confirmed.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

                recyclerview.setAdapter(adapter);


            }

            @Override
            public void onVideoCallReqListDownlaodFailed(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }
        });



        return  view;
    }
}