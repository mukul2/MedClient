package com.winkcoo.medx.adapter;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.Activity.AmbulanceDetailActivity;
import com.winkcoo.medx.Fragments.OnlineTimesScheduleFragment;
import com.winkcoo.medx.R;
import com.winkcoo.medx.model.AmbulanceModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.winkcoo.medx.Data.Data.NOW_SHOWING_AMBULANCE;
import static com.winkcoo.medx.Data.DataStore.convertToWeekDay;

/**
 * Created by mukul on 3/10/2019.
 */


public class OnlineScheduleAdapter extends RecyclerView.Adapter<OnlineScheduleAdapter.MyViewHolder> {
    List<JSONObject> list = new ArrayList<>();

    Context context;
    OnlineTimesScheduleFragment.changeScheduleListener changeScheduleListener;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_open, tv_close,tv_day;
        RelativeLayout relative_container;
        TextView switchDay,switchDay2;


        public MyViewHolder(View view) {
            super(view);
            tv_open = (TextView) view.findViewById(R.id.tv_open);
            tv_close = (TextView) view.findViewById(R.id.tv_close);
            tv_day = (TextView) view.findViewById(R.id.tv_day);
            switchDay = (TextView) view.findViewById(R.id.switchDay);
            switchDay2 = (TextView) view.findViewById(R.id.switchDay2);


        }
    }

    public void updateData(List<JSONObject> l){
        this.list.clear();
        this.list.addAll(l);
        notifyDataSetChanged();
        Log.i("mklm",this.list.toString());


    }


    public OnlineScheduleAdapter(List<JSONObject> lists, OnlineTimesScheduleFragment.changeScheduleListener listener) {
        this.list = lists;
        this.changeScheduleListener = listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.online_schedule_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final JSONObject movie = list.get(position);
        context = holder.tv_open.getContext();
        try {
            holder.tv_open.setText(movie.getString("starts"));
            holder.tv_close.setText(movie.getString("ends"));
            holder.tv_day.setText(convertToWeekDay(movie.getString("day")));
           // holder.switchDay.setText(movie.getString("status").equals("1")?"Turned ON":"Turned OFF");
            try {
                if (movie.getString("status").equals("1")) {
                    holder.switchDay.setTextColor(context.getResources().getColor(R.color.colorPrimary));

                    holder.switchDay2.setTextColor(context.getResources().getColor(R.color.grayLite));
                } else {
                    holder.switchDay.setTextColor(context.getResources().getColor(R.color.grayLite));

                    holder.switchDay2.setTextColor(context.getResources().getColor(R.color.colorPrimary));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  NOW_SHOWING_AMBULANCE = movie;
              //  context.startActivity(new Intent(context, AmbulanceDetailActivity.class));
            }
        });
        holder.tv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
               int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                              //  String formatedTime = formateDate(hourOfDay, minute);
                                String time  = ""+doubleDigit(hourOfDay)+":"+doubleDigit(minute);
                                changeScheduleListener.OnOpeningChanged(time,position);



                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        holder.tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
               int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                              //  String formatedTime = formateDate(hourOfDay, minute);
                                String time  = ""+doubleDigit(hourOfDay)+":"+doubleDigit(minute);
                                changeScheduleListener.OnCloseChanged(time,position);



                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
//        holder.switchDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    changeScheduleListener.OndayChanged(1,position);
//
//                }else {
//                    changeScheduleListener.OndayChanged(0,position);
//
//                }
//            }
//        });
        holder.switchDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeScheduleListener.OndayChanged(1, position);

                holder.switchDay.setTextColor(context.getResources().getColor(R.color.colorPrimary));

                holder.switchDay2.setTextColor(context.getResources().getColor(R.color.grayLite));
            }
        });

        holder.switchDay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeScheduleListener.OndayChanged(0, position);
                holder.switchDay.setTextColor(context.getResources().getColor(R.color.grayLite));

                holder.switchDay2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });



    }
    private String doubleDigit(int value) {

        String s1 = "0" + value;
        String s2 = "" + value;
        return value < 10 ? s1 : s2;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}