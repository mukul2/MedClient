package com.winkcoo.medx.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.doForMe;
import com.winkcoo.medx.adapter.PaymentMethodListAdapter;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.PaymentMethodsModel;
import com.winkcoo.medx.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.winkcoo.medx.Data.Data.CURRENCY_USD;
import static com.winkcoo.medx.Data.Data.PHOTO_BASE;
import static com.winkcoo.medx.Data.DataStore.NOW_SHOWING_ONLINE_DOC;

public class ConfirmVideoCallAppointment extends BaseActivity {
    @BindView(R.id.circleImage)
    CircleImageView circleImage;
    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_des)
    TextView tv_des;

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_date)
    TextView tv_date;
    Context context = this;
    int fees = 100 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_video_call_appointment);
        ButterKnife.bind(this);
        Glide.with(this).load(PHOTO_BASE+NOW_SHOWING_ONLINE_DOC.getPhoto()).into(circleImage);

        tv_name.setText(NOW_SHOWING_ONLINE_DOC.getName());
        tv_des.setText(NOW_SHOWING_ONLINE_DOC.getDesignation_title());
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String j =(String) b.get("date");
            tv_date.setText(j);
            tv_time.setText((String) b.get("slot"));
        }
    }
    public  void PaymentInitiate(View view){
        Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
        CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
        TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
        String ptext = "This service will charge you " +fees + "  " + CURRENCY_USD + " .Complete the payment to proceed.";

        tv_chargeShow.setText(ptext);
        RecyclerView paymentMethodsRecycler = (RecyclerView) dialog_.findViewById(R.id.recycler);
        showList(paymentMethodsRecycler,fees,"videoAppointment");
        linearPaypal.setOnClickListener((View v) -> {
            Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

            i.putExtra("credit", fees);
            i.putExtra("type", "videoAppointment");
            context.startActivity(i);
            dialog_.dismiss();
        });


    }

    private void showList(RecyclerView paymentMethodsRecycler,int fees, String type) {
        List<PaymentMethodsModel> data = new ArrayList<>();
        PaymentMethodListAdapter mAdapter = new PaymentMethodListAdapter(data,fees,type,null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        paymentMethodsRecycler.setLayoutManager(mLayoutManager);
        paymentMethodsRecycler.setItemAnimator(new DefaultItemAnimator());
        paymentMethodsRecycler.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
        paymentMethodsRecycler.setAdapter(mAdapter);


        Api.getInstance().get_payment_methods_list(new ApiListener.PaymentMethodsDownloadListener() {
            @Override
            public void onPaymentMethodsDownloadSuccess(List<PaymentMethodsModel> d) {
                Toast.makeText(context, "methods found " + data.size(), Toast.LENGTH_SHORT).show();
                data.addAll(d);
                mAdapter.notifyDataSetChanged();
                // PaymentMethodListAdapter
            }

            @Override
            public void onPaymentMethodsDownloadFailed(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }
        });
    }
}