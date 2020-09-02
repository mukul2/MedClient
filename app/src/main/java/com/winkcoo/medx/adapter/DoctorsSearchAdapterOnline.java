package com.winkcoo.medx.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.winkcoo.medx.Activity.DoctorsFullProfileView;
import com.winkcoo.medx.R;
import com.winkcoo.medx.model.OnlineDoctorsModel;
import com.winkcoo.medx.model.SearchDoctorModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.winkcoo.medx.Data.Data.PHOTO_BASE;
import static com.winkcoo.medx.Data.Data.TYPE_OF_ACTIVITY;
import static com.winkcoo.medx.Data.DataStore.NOW_SHOWING_DOC;
import static com.winkcoo.medx.Data.DataStore.NOW_SHOWING_ONLINE_DOC;

/**
 * Created by mukul on 3/10/2019.
 */


public class DoctorsSearchAdapterOnline extends RecyclerView.Adapter<DoctorsSearchAdapterOnline.MyViewHolder> {

    Context context;
    List<OnlineDoctorsModel> list = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_hospitalName, tv_time, tv_lastDegree, tv_epacialist, tv_address, tv_department;
        CircleImageView profile;
        RelativeLayout relative_container;
        LinearLayout linerBody;


        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_department = (TextView) view.findViewById(R.id.tv_department);
            profile = (CircleImageView) view.findViewById(R.id.profile);


        }
    }


    public DoctorsSearchAdapterOnline(List<OnlineDoctorsModel> data) {
        this.list = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_dr_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final OnlineDoctorsModel data = list.get(position);
        context = holder.tv_title.getContext();
        holder.tv_title.setText(data.getName());

        if (data.getDesignation_title() != null) {
            if (data.getDesignation_title().length() == 0) {
                holder.tv_department.setText("Not Given");

            } else {
                holder.tv_department.setText(data.getDesignation_title());

            }

        }else {
            holder.tv_department.setText("Not Given");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NOW_SHOWING_ONLINE_DOC = data;

                    TYPE_OF_ACTIVITY = "OnlineDoc";
                    context.startActivity(new Intent(context, DoctorsFullProfileView.class));


            }
        });
        if (data.getPhoto() != null) {
            Glide.with(context).load(PHOTO_BASE + data.getPhoto()).into(holder.profile);
        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}