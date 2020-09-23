package com.winkcoo.medx.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.winkcoo.medx.Activity.ChatActivityCommon;
import com.winkcoo.medx.Activity.ProjectPaypalPaymentActivity;
import com.winkcoo.medx.Activity.VideoAppointmentSlotDate;
import com.winkcoo.medx.Fragments.PatientDoctorsOnlineServiceFragment;
import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.doForMe;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.OnlineDoctorsServiceInfo;
import com.winkcoo.medx.model.PaymentMethodsModel;
import com.winkcoo.medx.widgets.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.winkcoo.medx.Data.Data.CURRENCY_USD;
import static com.winkcoo.medx.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.winkcoo.medx.Fragments.PatientDoctorsOnlineServiceFragment.IS_12_SUBSCRIBED;
import static com.winkcoo.medx.Fragments.PatientDoctorsOnlineServiceFragment.IS_1_SUBSCRIBED;
import static com.winkcoo.medx.Fragments.PatientDoctorsOnlineServiceFragment.IS_3_SUBSCRIBED;
import static com.winkcoo.medx.Fragments.PatientDoctorsOnlineServiceFragment.IS_6_SUBSCRIBED;

/**
 * Created by mukul on 3/10/2019.
 */


public class DrOnlineServicesAdapterBigItem extends RecyclerView.Adapter<DrOnlineServicesAdapterBigItem.MyViewHolder> {
    List<OnlineDoctorsServiceInfo> list = new ArrayList<>();

    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_action, tv_fees, tv_not_available;
        RelativeLayout relativeView;


        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_action = (TextView) view.findViewById(R.id.tv_action);
            tv_fees = (TextView) view.findViewById(R.id.tv_fees);
            tv_not_available = (TextView) view.findViewById(R.id.tv_not_available);
            relativeView = (RelativeLayout) view.findViewById(R.id.relativeView);


        }
    }


    public DrOnlineServicesAdapterBigItem(List<OnlineDoctorsServiceInfo> lists) {
        this.list = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_item_big, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final OnlineDoctorsServiceInfo movie = list.get(position);
        context = holder.tv_name.getContext();
        holder.tv_action.setVisibility(View.VISIBLE);
        holder.tv_name.setText(movie.getServiceNameInfo().getName());
        holder.tv_fees.setText("" + movie.getFees_per_unit() + " " + CURRENCY_USD);


        if (movie.getStatus() == 1) {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.tv_not_available.setVisibility(View.GONE);
            holder.relativeView.setAlpha(1);
            holder.itemView.setClickable(true);
            holder.itemView.setActivated(true);
            holder.itemView.setEnabled(true);
        } else {
            holder.relativeView.setAlpha(0.3f);
            holder.tv_not_available.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
            holder.itemView.setActivated(false);
            holder.itemView.setEnabled(false);

        }
        if (movie.getOnlineServiceId() == 5) {
            //prescrion request
            holder.itemView.setOnClickListener((View view) -> {
                Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                String ptext = "This service will charge you " + movie.getFees_per_unit() + "  " + CURRENCY_USD + " .Complete the payment to proceed.";
                tv_chargeShow.setText(ptext);
                RecyclerView paymentMethodsRecycler = (RecyclerView) dialog_.findViewById(R.id.recycler);
                showList(paymentMethodsRecycler,movie.getFees_per_unit(),"prescriptionRequest");
                linearPaypal.setOnClickListener((View v) -> {
                    Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                    i.putExtra("credit", movie.getFees_per_unit());
                    i.putExtra("type", "prescriptionRequest");
                    context.startActivity(i);
                    dialog_.dismiss();
                });
            });
        }
        if (movie.getOnlineServiceId() == 1) {
            holder.itemView.setOnClickListener((View view) -> {


                Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                String ptext = "This service will charge you " + movie.getFees_per_unit() + "  " + CURRENCY_USD + " .Complete the payment to proceed.";

                tv_chargeShow.setText(ptext);
                RecyclerView paymentMethodsRecycler = (RecyclerView) dialog_.findViewById(R.id.recycler);
                showList(paymentMethodsRecycler,movie.getFees_per_unit(),"prescriptionReview");

                linearPaypal.setOnClickListener((View v) -> {
                    Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                    i.putExtra("credit", movie.getFees_per_unit());
                    i.putExtra("type", "prescriptionReview");
                    context.startActivity(i);
                    dialog_.dismiss();
                });

            });
        }
        if (movie.getOnlineServiceId() == 6) {
            if (PatientDoctorsOnlineServiceFragment.IS_CHAT_SUBSCRIBED) {
                holder.tv_fees.setText("Payment Done");
                holder.tv_action.setText("");

                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", String.valueOf(movie.getDoctorId()));
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });


            } else {
                holder.itemView.setOnClickListener((View view) -> {


                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you " + movie.getFees_per_unit() + "  " + CURRENCY_USD + " .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    RecyclerView paymentMethodsRecycler = (RecyclerView) dialog_.findViewById(R.id.recycler);
                    showList(paymentMethodsRecycler,movie.getFees_per_unit(),"chat");
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "chat");
                        i.putExtra("partner_id", String.valueOf(movie.getDoctorId()));
                        i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                        i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }
        }
        if (movie.getOnlineServiceId() == 2) {
            Toast.makeText(context, "here 1", Toast.LENGTH_SHORT).show();
           // holder.tv_fees.setText("Usualy Available at "+NOW_SHOWING_ONLINE_DOC.getVideo_call_available_time());

            if (PatientDoctorsOnlineServiceFragment.IS_VIDEO_CALL_SUBSCRIBED) {
               // Toast.makeText(context, "here 2", Toast.LENGTH_SHORT).show();

             //   String timeSts = "Payment Done"+"\n"+"Usualy Available at "+NOW_SHOWING_ONLINE_DOC.getVideo_call_available_time();
                String timeSts = "Payment Done";
                holder.tv_fees.setText(timeSts);
                holder.tv_action.setVisibility(View.GONE);
                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });
            } else {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, VideoAppointmentSlotDate.class));

                    }
                });
            }


               /*

                String timeSts =   "" + movie.getFees_per_unit() + " " + CURRENCY_USD+"\nUsualy Available at "+NOW_SHOWING_ONLINE_DOC.getVideo_call_available_time();
                holder.tv_fees.setText(timeSts);
                holder.itemView.setOnClickListener((View view) -> {



                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you " + movie.getFees_per_unit() + "  " + CURRENCY_USD + " .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    RecyclerView paymentMethodsRecycler = (RecyclerView) dialog_.findViewById(R.id.recycler);
                    showList(paymentMethodsRecycler,movie.getFees_per_unit(),"videoAppointment");
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "videoAppointment");
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }

                */
        }
        if (movie.getOnlineServiceId() == 8) {
            if (IS_1_SUBSCRIBED) {
                holder.tv_fees.setText("Subscription Done");
                holder.tv_action.setVisibility(View.GONE);
                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });
            } else {
                holder.itemView.setOnClickListener((View view) -> {
                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you " + movie.getFees_per_unit() + "  " + CURRENCY_USD + " .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    RecyclerView paymentMethodsRecycler = (RecyclerView) dialog_.findViewById(R.id.recycler);
                    showList(paymentMethodsRecycler,movie.getFees_per_unit(),"1MonthSubscription");
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "1MonthSubscription");
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }
        }
        if (movie.getOnlineServiceId() == 9) {
            if (IS_3_SUBSCRIBED) {
                holder.tv_fees.setText("Subscription Done");
                holder.tv_action.setVisibility(View.GONE);
                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });
            } else {
                holder.itemView.setOnClickListener((View view) -> {
                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you " + movie.getFees_per_unit() + "  " + CURRENCY_USD + " .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    RecyclerView paymentMethodsRecycler = (RecyclerView) dialog_.findViewById(R.id.recycler);
                    showList(paymentMethodsRecycler,movie.getFees_per_unit(),"3MonthSubscription");
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "3MonthSubscription");
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }
        }
        if (movie.getOnlineServiceId() == 10) {
            if (IS_6_SUBSCRIBED) {
                holder.tv_fees.setText("Subscription Done");
                holder.tv_action.setVisibility(View.GONE);
                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });
            } else {
                holder.itemView.setOnClickListener((View view) -> {
                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you " + movie.getFees_per_unit() + "  " + CURRENCY_USD + " .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    RecyclerView paymentMethodsRecycler = (RecyclerView) dialog_.findViewById(R.id.recycler);
                    showList(paymentMethodsRecycler,movie.getFees_per_unit(),"6MonthSubscription");
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "6MonthSubscription");
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }
        }
        if (movie.getOnlineServiceId() == 11) {
            if (IS_12_SUBSCRIBED) {
                holder.tv_fees.setText("Subscription Done");
                holder.tv_action.setVisibility(View.GONE);
                holder.itemView.setOnClickListener((View view) -> {
                    Intent i = new Intent(context, ChatActivityCommon.class);
                    i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                    i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                    i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                    context.startActivity(i);
                });
            } else {
                holder.itemView.setOnClickListener((View view) -> {
                    Dialog dialog_ = doForMe.showDialog(context, R.layout.choose_payment_method_dialog);
                    CardView linearPaypal = (CardView) dialog_.findViewById(R.id.linearPaypal);
                    TextView tv_chargeShow = (TextView) dialog_.findViewById(R.id.tv_chargeShow);
                    String ptext = "This service will charge you " + movie.getFees_per_unit() + "  " + CURRENCY_USD + " .Complete the payment to proceed.";

                    tv_chargeShow.setText(ptext);
                    RecyclerView paymentMethodsRecycler = (RecyclerView) dialog_.findViewById(R.id.recycler);
                    showList(paymentMethodsRecycler,movie.getFees_per_unit(),"12MonthSubscription");
                    linearPaypal.setOnClickListener((View v) -> {
                        Intent i = new Intent(context, ProjectPaypalPaymentActivity.class);

                        i.putExtra("credit", movie.getFees_per_unit());
                        i.putExtra("type", "12MonthSubscription");
                        context.startActivity(i);
                        dialog_.dismiss();
                    });
                });
            }
        }


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


    @Override
    public int getItemCount() {
        return list.size();
    }
}