package com.winkcoo.medx.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.MyProgressBar;
import com.winkcoo.medx.adapter.AmbulanceLitsAdapterAdmin;
import com.winkcoo.medx.adapter.OnlineScheduleAdapter;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.StatusMessage;
import com.winkcoo.medx.widgets.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnlineTimesScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnlineTimesScheduleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
  //  List<Map<String, String>> data = new ArrayList<>();
    List<JSONObject> data = new ArrayList<>();
    OnlineScheduleAdapter mAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    View view;
    Context context;

    public OnlineTimesScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnlineTimesScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OnlineTimesScheduleFragment newInstance(String param1, String param2) {
        OnlineTimesScheduleFragment fragment = new OnlineTimesScheduleFragment();
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
       // Map<String, String> d = new HashMap<>();
        JSONObject d = new JSONObject();

        try {
            d.put("day", "0");
            d.put("starts", "0");
            d.put("ends", "0");
            d.put("status", "0");
            data.add(d);

            d = new JSONObject();
            d.put("day", "1");
            d.put("starts", "0");
            d.put("ends", "0");
            d.put("status", "0");

            data.add(d);
            d = new JSONObject();
            d.put("day", "2");
            d.put("starts", "0");
            d.put("ends", "0");
            d.put("status", "0");

            data.add(d);
            d = new JSONObject();
            d.put("day", "3");
            d.put("starts", "0");
            d.put("ends", "0");
            d.put("status", "0");

            data.add(d);
            d = new JSONObject();
            d.put("day", "4");
            d.put("starts", "0");
            d.put("ends", "0");
            d.put("status", "0");

            data.add(d);
            d = new JSONObject();
            d.put("day", "5");
            d.put("starts", "0");
            d.put("ends", "0");
            d.put("status", "0");

            data.add(d);
            d = new JSONObject();
            d.put("day", "6");
            d.put("starts", "0");
            d.put("ends", "0");
            d.put("status", "0");

            data.add(d);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        view = inflater.inflate(R.layout.fragment_online_times_schedule, container, false);
        ButterKnife.bind(this, view);
        context = view.getContext();

        //OnlineScheduleAdapter
        changeScheduleListener listener = new changeScheduleListener() {


            @Override
            public void OndayChanged(int val, int pos) {
                Log.i("mkl","= "+val+"="+pos);

                try {
                    data.get(pos).put("status", ""+val);
                    updateToApi(data.toString());
                    Log.i("mkl",data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void OnOpeningChanged(String time, int pos) {
                try {
                    data.get(pos).put("starts", time);
                    mAdapter.notifyDataSetChanged();
                    updateToApi(data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void OnCloseChanged(String time, int pos) {
                try {
                    data.get(pos).put("ends", time);
                    mAdapter.notifyDataSetChanged();
                    updateToApi(data.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        mAdapter = new OnlineScheduleAdapter(data, listener);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        Api.getInstance().get_video_call_available_time(TOKEN, USER_ID, new ApiListener.AvailableInfoDownloadListener() {
            @Override
            public void onAvailableInfoDownloadSuccess(JsonElement newDa) {
                List<JSONObject>newArray = new ArrayList<>();
                try {
                    JSONArray array = new JSONArray(newDa.toString());

                    for (int i = 0 ; i<array.length();i++){
                        newArray.add(array.getJSONObject(i));

                    }
                    Log.i("mklm",newDa.toString());

                    mAdapter.updateData(newArray);
                    //Toast.makeText(context, "size "+array.length(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "err "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onAvailableInfoDownloadFailed(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    private void updateToApi(String data) {
        Api.getInstance().update_video_call_available_time(TOKEN, USER_ID, data, new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                MyProgressBar.dismiss();
                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onBasicApiFailed(String msg) {
                MyProgressBar.dismiss();
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();



            }
        });
    }

    public interface changeScheduleListener {
        void OndayChanged(int val, int pos);

        void OnOpeningChanged(String time, int pos);

        void OnCloseChanged(String time, int pos);

    }
}