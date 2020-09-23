package com.winkcoo.medx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.Activity.VideoAppointmentSlotDate;
import com.winkcoo.medx.R;
import com.winkcoo.medx.model.StatusMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mukul on 3/10/2019.
 */


public class SlotTimesAdapterPatient extends RecyclerView.Adapter<SlotTimesAdapterPatient.MyViewHolder> {
    List<StatusMessage> list = new ArrayList<>();
    VideoAppointmentSlotDate.SlotSelectListsnerSlot slotSelectListsnerSlot;




    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        CardView card;
        ImageView img;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
           // img = (ImageView) view.findViewById(R.id.img);
            card = (CardView) view.findViewById(R.id.card);


        }
    }




    public SlotTimesAdapterPatient(List<StatusMessage> lists, VideoAppointmentSlotDate.SlotSelectListsnerSlot s) {
        this.list = lists;
        this.slotSelectListsnerSlot = s;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.times_grid, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final StatusMessage data = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(data.getMessage());
       if( data.getStatus())
       {
          // holder.img.setColorFilter(context.getResources().getColor(R.color.white),android.graphics.PorterDuff.Mode.MULTIPLY);
          // holder.tv_name.setTextColor(context.getResources().getColor(R.color.white));
           holder.card.setClickable(true);
           holder.card.setEnabled(true);
           holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.white));

       }else {
          // holder.img.setColorFilter(context.getResources().getColor(R.color.colorPrimary),android.graphics.PorterDuff.Mode.MULTIPLY);
           holder.card.setClickable(false);
           holder.card.setEnabled(false);
          // holder.tv_name.setTextColor(context.getResources().getColor(R.color.white));

           holder.card.setCardBackgroundColor(context.getResources().getColor(R.color.grayLite));

       }
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               slotSelectListsnerSlot.onClicked(data.getMessage());


           }
       });

     ///   holder.card.setCardBackgroundColor(Color.parseColor(Data.getColorCode(position)));




    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}