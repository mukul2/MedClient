package com.winkcoo.medx.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.MyProgressBar;
import com.winkcoo.medx.Utils.doForMe;
import com.winkcoo.medx.adapter.VideoCallReqListAdapter;
import com.winkcoo.medx.adapter.VideoCallReqListAdapterPatient;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.DaysTimeModel;
import com.winkcoo.medx.model.FetchProfileResponse;
import com.winkcoo.medx.model.StatusMessage;
import com.winkcoo.medx.model.VideoAppointmentModel;
import com.winkcoo.medx.widgets.MyDialogList;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;
import static com.winkcoo.medx.Data.DataStore.USER_TYPE;

public class VideoCallAppointmentList extends BaseActivity implements ApiListener.VideoCallReqListDownlaodListener {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;


    @BindView(R.id.cardTime)
    CardView cardTime;
    @BindView(R.id.tv_time)
    TextView tv_time;
    Context context = this;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call_appointment_list);
        ButterKnife.bind(this);

        if (USER_TYPE.equals("d")) {
            Api.getInstance().get_video_appointment_list(TOKEN, "doctor", USER_ID, this);
            cardTime.setVisibility(View.VISIBLE);
            Api.getInstance().get_user_info(USER_ID, new ApiListener.profileFetchListener() {
                @Override
                public void onprofileFetchSuccess(FetchProfileResponse list) {
                    tv_time.setText(list.getProfile().getVideo_call_available_time());

                }

                @Override
                public void onprofileFetchFailed(String msg) {

                }
            });
            cardTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = doForMe.showDialog(context, R.layout.video_appointment_time_set_dialog);
                    TextView tv_changeDate = dialog.findViewById(R.id.tv_changeDate);
                    TextView tv_time_ = dialog.findViewById(R.id.tv_time);
                    CardView cardSave = dialog.findViewById(R.id.cardSave);
                    time  = tv_time.getText().toString().trim();
                    cardSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyProgressBar.with(context);
                            Api.getInstance().update_video_call_available_time(TOKEN, USER_ID, time, new ApiListener.basicApiListener() {
                                @Override
                                public void onBasicSuccess(StatusMessage response) {
                                    MyProgressBar.dismiss();
                                    dialog.dismiss();
                                    tv_time.setText(time);

                                }

                                @Override
                                public void onBasicApiFailed(String msg) {
                                    MyProgressBar.dismiss();
                                    dialog.dismiss();



                                }
                            });

                        }
                    });
                    tv_changeDate.setOnClickListener(new View.OnClickListener() {
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
                                            Toast.makeText(VideoCallAppointmentList.this, ""+hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();

                                            tv_time_.setText(hourOfDay + ":" + minute);
                                            time = hourOfDay + ":" + minute;
                                            //tv_time.setText(hourOfDay + ":" + minute);
                                        }
                                    }, mHour, mMinute, false);
                            timePickerDialog.show();
                        }

                    });


                }
            });

        } else {
            Api.getInstance().get_video_appointment_list(TOKEN, "patient", USER_ID, this);
            cardTime.setVisibility(View.GONE);


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
