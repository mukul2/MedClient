package com.winkcoo.medx.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.Activity.DrListActivity;
import com.winkcoo.medx.Activity.DrListGridActivity;
import com.winkcoo.medx.Activity.OnlineDocListActivity;
import com.winkcoo.medx.Activity.VideoAppointmentSlotDate;
import com.winkcoo.medx.R;
import com.winkcoo.medx.model.DeptModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.winkcoo.medx.Data.Data.TYPE_OF_ACTIVITY;

/**
 * Created by mukul on 3/10/2019.
 */


public class SlotDatesAdapterPatient extends RecyclerView.Adapter<SlotDatesAdapterPatient.MyViewHolder> {
    List<Map<String,String>> list = new ArrayList<>();
    VideoAppointmentSlotDate.DateSelectListsnerSlot dateSelectListsner;




    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        CardView card;
        ImageView img;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            img = (ImageView) view.findViewById(R.id.img);
            card = (CardView) view.findViewById(R.id.card);


        }
    }




    public SlotDatesAdapterPatient(List<Map<String,String>> lists, VideoAppointmentSlotDate.DateSelectListsnerSlot s) {
        this.list = lists;
        this.dateSelectListsner = s;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dates_hori, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Map<String,String> data = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(data.get("datebig"));
       if( data.get("isSelected").equals("1"))
       {
           holder.img.setColorFilter(context.getResources().getColor(R.color.white),android.graphics.PorterDuff.Mode.MULTIPLY);
           holder.tv_name.setTextColor(context.getResources().getColor(R.color.white));

           holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
       }else {
           holder.img.setColorFilter(context.getResources().getColor(R.color.colorPrimary),android.graphics.PorterDuff.Mode.MULTIPLY);

           holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.white));
           holder.tv_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));


       }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dateSelectListsner.onClicked(data.get("date"),data.get("dayIndex"),data.get("datebig"));
               for(int i = 0;i<list.size();i++){
                   list.get(i).put("isSelected","0");
               }
               if( data.get("isSelected").equals("1"))
               {


                   list.get(position).put("isSelected","0");
                   notifyDataSetChanged();
                 //  holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.white));
               }else {
                   list.get(position).put("isSelected","1");
                   notifyDataSetChanged();


                   // holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));


               }
           }
       });

     ///   holder.card.setCardBackgroundColor(Color.parseColor(Data.getColorCode(position)));




    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}