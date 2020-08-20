package com.winkcoo.medx.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.Activity.BankTransferActivity;
import com.winkcoo.medx.Activity.BkashPaymentActivity;
import com.winkcoo.medx.Activity.ProjectPaypalPaymentActivity;
import com.winkcoo.medx.R;
import com.winkcoo.medx.model.PaymentMethodsModel;

import java.util.ArrayList;
import java.util.List;

import static com.winkcoo.medx.Data.Data.PAY_PAL_CLIENT_ID;

/**
 * Created by mukul on 3/10/2019.
 */


public class PaymentMethodListAdapter extends RecyclerView.Adapter<PaymentMethodListAdapter.MyViewHolder> {
    List<PaymentMethodsModel> list = new ArrayList<>();
    int fees;
    String type;
    String ref;

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        RelativeLayout relative_container;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);


        }
    }


    public PaymentMethodListAdapter(List<PaymentMethodsModel> lists, int f, String ty,String r) {
        this.list = lists;
        this.fees = f;
        this.type = ty;
        this.ref = r;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_methods_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PaymentMethodsModel data = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_name.setText(data.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getName().equals("Paypal")){
                    PAY_PAL_CLIENT_ID = data.getMarchant();
                    Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                    i.putExtra("credit",fees);
                    i.putExtra("type", type);
                    i.putExtra("ref", ref);
                    context.startActivity(i);
                }else  if (data.getName().equals("bkash")){
                    //BkashPaymentActivity

                    Intent i = new Intent(context, BkashPaymentActivity.class);

                    i.putExtra("credit",fees);
                    i.putExtra("type", type);
                    i.putExtra("marchant", data.getMarchant());
                    i.putExtra("ref", ref);

                    context.startActivity(i);
                }else  if (data.getName().equals("Bank Transfer")){
                    //BkashPaymentActivity

                    Intent i = new Intent(context, BankTransferActivity.class);

                    i.putExtra("credit",fees);
                    i.putExtra("type", type);
                    i.putExtra("marchant", data.getMarchant());
                    i.putExtra("ref", ref);

                    context.startActivity(i);
                }


            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}