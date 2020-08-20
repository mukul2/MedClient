package com.winkcoo.medx.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.winkcoo.medx.Activity.RecomendationDetailActivity;
import com.winkcoo.medx.R;
import com.winkcoo.medx.model.TestRecomendationModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.winkcoo.medx.Data.Data.PHOTO_BASE;
import static com.winkcoo.medx.Data.Data.testList;

/**
 * Created by mukul on 3/10/2019.
 */


public class RecomendedTestAppointmentAdapterPatient extends RecyclerView.Adapter<RecomendedTestAppointmentAdapterPatient.MyViewHolder> {
    List<TestRecomendationModel>list=new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_detail, tv_appointmentfor, tv_date;
        RecyclerView recycler_view;
        CircleImageView image;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            image = (CircleImageView) view.findViewById(R.id.image);



        }
    }


    public RecomendedTestAppointmentAdapterPatient(List<TestRecomendationModel> lists ) {
        list.clear();
        this.list=lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recomended_test_patient, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final TestRecomendationModel movie = list.get(position);
        context = holder.tv_name.getContext();
        Resources res =context. getResources();
        String text = res.getString(R.string.text);
        holder.tv_name.setText(movie.getDr_info().getName());
        String body="";
        body+=movie.getTestRecommendationInfo().size()+" new Test is recommened";
        holder.tv_detail.setText(body);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testList=movie;
               context.startActivity(new Intent(context, RecomendationDetailActivity.class));

            }
        });
        Glide.with(context).load(PHOTO_BASE+movie.getDr_info().getPhoto()).into(holder.image);



    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}