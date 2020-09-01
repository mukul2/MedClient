package com.winkcoo.medx.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.winkcoo.medx.Activity.ChatActivityCommon;
import com.winkcoo.medx.R;
import com.winkcoo.medx.model.VideoAppointmentModel;

import java.util.ArrayList;
import java.util.List;

import static com.winkcoo.medx.Data.Data.PHOTO_BASE;

/**
 * Created by mukul on 3/10/2019.
 */


public class VideoCallReqListAdapterPatient extends RecyclerView.Adapter<VideoCallReqListAdapterPatient.MyViewHolder> {
    List<VideoAppointmentModel> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date, tv_name, tv_body, tv_lastDegree, tv_epacialist, tv_address,tv_status,tv_time;
        ImageView img_profile,img_status;


        public MyViewHolder(View view) {
            super(view);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            img_profile = (ImageView) view.findViewById(R.id.img_profile);
            img_status = (ImageView) view.findViewById(R.id.img_status);


        }
    }


    public VideoCallReqListAdapterPatient(List<VideoAppointmentModel> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_call_list_item_patient, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final VideoAppointmentModel movie = list.get(position);
        context = holder.tv_date.getContext();
        holder.tv_name.setText(movie.getDrInfo().getName());
        holder.tv_date.setVisibility(View.GONE);
        holder.tv_time.setText("Usualy Online at : "+movie.getDrInfo().getVideo_call_available_time());
        Glide.with(context).load(PHOTO_BASE+movie.getDrInfo().getPhoto()).into(holder.img_profile);

        if (movie.getPaymentStatus()==1) {
            holder.tv_status.setText("Send a Message");
            holder.img_status.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", String.valueOf(movie.getDoctorId()));
                    i.putExtra("partner_name", movie.getDrInfo().getName());
                    i.putExtra("partner_photo", movie.getDrInfo().getPhoto());
                    context.startActivity(i);
                }
            });
        }else {
            holder.img_status.setVisibility(View.GONE);
            holder.tv_status.setText("Payment is not verified yet");


        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}