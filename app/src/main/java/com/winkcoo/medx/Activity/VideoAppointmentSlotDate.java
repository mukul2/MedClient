package com.winkcoo.medx.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sinch.gson.Gson;
import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.MyProgressBar;
import com.winkcoo.medx.adapter.DepartmentsAdapterGrid;
import com.winkcoo.medx.adapter.SlotDatesAdapterPatient;
import com.winkcoo.medx.adapter.SlotTimesAdapterPatient;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.StatusMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.winkcoo.medx.Data.Data.PHOTO_BASE;
import static com.winkcoo.medx.Data.Data.SELECTEDED_SLOT_DATE;
import static com.winkcoo.medx.Data.Data.SELECTEDED_SLOT_TIME;
import static com.winkcoo.medx.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;

public class VideoAppointmentSlotDate extends BaseActivity {
    @BindView(R.id.circleImage)
    CircleImageView circleImage;

    @BindView(R.id.recycler_view_slots)
    RecyclerView recycler_view_slots;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_des)
    TextView tv_des;

    @BindView(R.id.tv_available)
    TextView tv_available;
    List<Map<String,String>> datesData = new ArrayList<Map<String,String>>();
    List<StatusMessage> slotes = new ArrayList<>();
    Context context = this ;
    String selectedDate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_appointment_slot_date);
        ButterKnife.bind(this);
        Glide.with(this).load(PHOTO_BASE+NOW_SHOWING_ONLINE_DOC.getPhoto()).into(circleImage);

        tv_name.setText(NOW_SHOWING_ONLINE_DOC.getName());
        tv_des.setText(NOW_SHOWING_ONLINE_DOC.getDesignation_title());
        //tv_available.setText("Usualy Online at : "+NOW_SHOWING_ONLINE_DOC.getVideo_call_available_time());
        //tv_name.setText(NOW_SHOWING_ONLINE_DOC.getName());
        Calendar calendar = Calendar.getInstance();
        Map<String,String> data_=new HashMap<>();

        DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = formatter.format(calendar.getTime());
        data_.put("datebig",dateFormatted);
        data_.put("date",""+calendar.get(Calendar.YEAR)+"-"+formateString(1+calendar.get(Calendar.MONTH))+"-"+formateString(calendar.get(Calendar.DATE)));
        data_.put("day",""+(-1+calendar.get(Calendar.DAY_OF_WEEK)));
        data_.put("isSelected","1");
        data_.put("dayIndex",String.valueOf(-1+calendar.get(Calendar.DAY_OF_WEEK)));

        datesData.add(data_);
        calendar.add(Calendar.DATE,1);
        for(int i = 0 ;i<20;i++){
            Map<String,String> data=new HashMap<>();

            DateFormat formatter_ = new SimpleDateFormat("E, dd MMM yyyy");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String dateFormatted_ = formatter_.format(calendar.getTime());

            data.put("datebig",dateFormatted_);
            data.put("date",""+calendar.get(Calendar.YEAR)+"-"+formateString(1+calendar.get(Calendar.MONTH))+"-"+formateString(calendar.get(Calendar.DATE)));
            data.put("day",""+String.valueOf(-1+calendar.get(Calendar.DAY_OF_WEEK)));
            data.put("dayIndex",String.valueOf(-1+calendar.get(Calendar.DAY_OF_WEEK)));
            data.put("isSelected","0");
            datesData.add(data);
            calendar.add(Calendar.DATE,1);
        }
        //SlotDatesAdapterPatient

        tv_date.setText(datesData.get(0).get("datebig"));
        selectedDate = datesData.get(0).get("date");
        //recycler_view_slots
        SlotSelectListsnerSlot slotSelectListsnerSlot = new SlotSelectListsnerSlot() {
            @Override
            public void onClicked(String slot) {

                SELECTEDED_SLOT_DATE = selectedDate;
               // SELECTEDED_SLOT_TIME = slot;
                SELECTEDED_SLOT_TIME = slot;
                Intent intent = new Intent(context,ConfirmVideoCallAppointment.class);
                intent.putExtra("date",selectedDate);
                intent.putExtra("slot",slot);
                startActivity(intent);



            }
        };
        SlotTimesAdapterPatient mAdapter = new SlotTimesAdapterPatient(slotes,slotSelectListsnerSlot);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context,3);
        recycler_view_slots.setLayoutManager(mLayoutManager);
        recycler_view_slots.setItemAnimator(new DefaultItemAnimator());
        recycler_view_slots.setAdapter(mAdapter);
        MyProgressBar.with(context);
        Api.getInstance().get_vdo_appointment_slot(TOKEN, NOW_SHOWING_ONLINE_DOC.getId().toString(), USER_ID, datesData.get(0).get("date"),datesData.get(0).get("dayIndex") , new ApiListener.SlotSearchListener() {
            @Override
            public void onSlotSearchSuccess(List<StatusMessage> data) {
                MyProgressBar.dismiss();
                Gson gson = new Gson();
                Log.i("mkl",gson.toJson(data));

                // Toast.makeText(context, "count " +gson.toJson(data), Toast.LENGTH_SHORT).show();
                slotes.clear();
                slotes.addAll(data);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onSlotSearchFailed(String msg) {
                MyProgressBar.dismiss();

                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }
        });



        DateSelectListsnerSlot dateSelectListsnerSlot = new DateSelectListsnerSlot() {
            @Override
            public void onClicked(String date,String dayIndex,String dateBig) {
                selectedDate = date;
                tv_date.setText(dateBig);
                MyProgressBar.with(context);
                Log.i("mkl", NOW_SHOWING_ONLINE_DOC.getId().toString());
                Log.i("mkl",USER_ID);
                Log.i("mkl",date);
                Log.i("mkl",dayIndex);
               // Toast.makeText(context, ""+dayIndex, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, ""+dayIndex, Toast.LENGTH_SHORT).show();

                Api.getInstance().get_vdo_appointment_slot(TOKEN, NOW_SHOWING_ONLINE_DOC.getId().toString(), USER_ID, date, dayIndex, new ApiListener.SlotSearchListener() {
                    @Override
                    public void onSlotSearchSuccess(List<StatusMessage> data) {
                        MyProgressBar.dismiss();
                        Gson gson = new Gson();
                        Log.i("mkl",gson.toJson(data));

                        // Toast.makeText(context, "count " +gson.toJson(data), Toast.LENGTH_SHORT).show();
                        slotes.clear();
                        slotes.addAll(data);
                        mAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onSlotSearchFailed(String msg) {
                        MyProgressBar.dismiss();

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    }
                });

            }
        };

        SlotDatesAdapterPatient mAdapter_ = new SlotDatesAdapterPatient(datesData,dateSelectListsnerSlot);
        RecyclerView.LayoutManager mLayoutManager_ = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        recycler_view.setLayoutManager(mLayoutManager_);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter_);


    }
    public  String formateString(int input){
        return input> 9? ""+input:( "0"+String.valueOf(input));
    }
   public interface  DateSelectListsnerSlot{
        void onClicked(String date,String dayIndex,String dateBig);
    }

    public interface  SlotSelectListsnerSlot{
        void onClicked(String date);
    }
}