package com.winkcoo.medx.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.MyProgressBar;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.AppointmentAddResponse;
import com.winkcoo.medx.model.StatusMessage;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.winkcoo.medx.Data.Data.CURRENCY_USD_SIGN;
import static com.winkcoo.medx.Data.Data.PAY_PAL_CLIENT_ID;
import static com.winkcoo.medx.Data.DataStore.NOW_SHOWING_DOC;
import static com.winkcoo.medx.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;

public class ProjectPaypalPaymentActivity extends AppCompatActivity {
    private static final int PAYPAL_REQUEST_CODE = 7777;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAY_PAL_CLIENT_ID);


    String amount = "";
    String type;
    Context context = this;
    String startMonth,endMonth,chamberApID;
    Calendar calendar;
    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_paypal_payment);

        //

        calendar = Calendar.getInstance();
        startMonth = ""+calendar.get(Calendar.YEAR)+"-"+(1+calendar.get(Calendar.MONTH))+"-"+(1+calendar.get(Calendar.DATE));

        //start paypal service

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            int j = (int) b.get("credit");
            type = (String) b.get("type");
            chamberApID = (String) b.get("ref");
            amount = "" + j;
            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            startService(intent);
            processPayment();
        }

    }

    private void processPayment() {

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
                "Service Fees", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {

                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        JSONObject object =new JSONObject(paymentDetails);
                        JSONObject ob2 = object.getJSONObject("response");
                        String state = ob2.getString("state");
                        if (state.equals("approved")){
                            String payID = ob2.getString("id");
                            if (type.equals("prescriptionRequest"))
                                processToThePrescriptionRequest(payID);
                            if (type.equals("videoAppointment"))
                                processToTheAddVideoAppointment(payID);
                            if (type.equals("prescriptionReview"))
                                processToThePrescriptioReview(payID);
                            if (type.equals("chat"))
                                processToTheChatRequest(payID);
                            if (type.equals("1MonthSubscription"))
                                processToThe1MonthSubscriptionRequest(paymentDetails, 1);
                            if (type.equals("3MonthSubscription"))
                                processToThe1MonthSubscriptionRequest(paymentDetails, 3);
                            if (type.equals("6MonthSubscription"))
                                processToThe1MonthSubscriptionRequest(paymentDetails, 6);
                            if (type.equals("12MonthSubscription"))
                                processToThe1MonthSubscriptionRequest(paymentDetails, 12);
                            if (type.equals("chamber")){
                                processToChamberPayment(payID,NOW_SHOWING_DOC.getId());
                                //startActivity(new Intent(context,PatientHomeActivity.class));

                            }
                        }
                        Log.i("mkl", paymentDetails);
//                        startActivity(new Intent(this, PaymentDetails.class)
//                                .putExtra("Payment Details", paymentDetails)
//                                .putExtra("Amount", amount));




                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("mkl", e.getLocalizedMessage());
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }

    private void processToChamberPayment(String payID,int doc_id) {
        //chamberApID
        //Log.i("mkl",""+NOW_SHOWING_ONLINE_DOC.getId()+"="+amount+"="+payID+"="+chamberApID);

        Api.getInstance().add_payment_info_only_for_chamber_appoiontment(TOKEN, c_m_b(USER_ID), c_m_b("" + doc_id), c_m_b(amount), c_m_b("1"), c_m_b("Chamber Appointment"),c_m_b(payID), c_m_b(chamberApID),null,new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                Toast.makeText(context, response.getMessage(), Toast.LENGTH_LONG).show();

                Intent i = new Intent(getBaseContext(), PatientHomeActivity.class);
                i.putExtra("paymentInfo", payID);
                i.putExtra("amount", amount);
                i.putExtra("paymentType", "1");
                i.putExtra("paypal_id", response.getMessage());
                //paymentInfo
                startActivity(i);
                finishAffinity();
            }

            @Override
            public void onBasicApiFailed(String msg) {

            }
        });

    }

    private void processToThe1MonthSubscriptionRequest(String paymentDetails, int month) {
        startMonth = "" + calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-" + (1 + calendar.get(Calendar.DATE));
        MyProgressBar.with(context);
        calendar.add(Calendar.MONTH, month);
        endMonth = "" + calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-" + (1 + calendar.get(Calendar.DATE));
        Log.i("mkl", USER_ID + "==" + NOW_SHOWING_ONLINE_DOC.getId() + "==" + paymentDetails + "==" + startMonth + "" + endMonth + "==" + amount);


        ApiListener.basicApiListener listener = new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                MyProgressBar.dismiss();
                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, PatientHomeActivity.class));
                finishAffinity();
            }

            @Override
            public void onBasicApiFailed(String msg) {
                MyProgressBar.dismiss();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }
        };



            Api.getInstance().add_subscription_info(TOKEN, c_m_b(USER_ID), c_m_b("" + NOW_SHOWING_ONLINE_DOC.getId()), c_m_b(paymentDetails), c_m_b("" + month), c_m_b(startMonth), c_m_b(endMonth), c_m_b("1"), c_m_b(amount), null, listener);




    }
    private void processToTheChatRequest(String paymentDetails) {
        Api.getInstance().addChatRequest(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), paymentDetails,"1",amount, new ApiListener.AppointmentPOstListener() {
            @Override
            public void onAppointmentPOStSuccess(AppointmentAddResponse data) {
                Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, ChatActivityCommon.class);
                i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                i.putExtra("initMessage",amount+CURRENCY_USD_SIGN+" has been paid for Chat service");
                context.startActivity(i);
                finish();


            }

            @Override
            public void onAppointmentPOStFailed(String msg) {

            }
        });
    }
    private RequestBody c_m_b(String aThis) {
        return
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), aThis);
    }
    private void processToThePrescriptionRequest(String paymentDetails) {

        Api.getInstance().add_payment_info_only(TOKEN, c_m_b(USER_ID), c_m_b("" + NOW_SHOWING_ONLINE_DOC.getId()), c_m_b(amount), c_m_b("0"), c_m_b("Prescription request"),c_m_b(paymentDetails), null,new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getBaseContext(), PrescriptonRequestMakingActivity.class);
                i.putExtra("paymentInfo", paymentDetails);
                i.putExtra("amount", amount);
                i.putExtra("paymentType", "1");
                i.putExtra("paypal_id", response.getMessage());
                //paymentInfo
                startActivity(i);
            }

            @Override
            public void onBasicApiFailed(String msg) {

            }
        });

    }

    private void doNothing() {
        Toast.makeText(this, "Nothing to do", Toast.LENGTH_SHORT).show();
    }

    private void processToTheAddVideoAppointment(String paymentDetails) {
        MyProgressBar.with(context);

        Api.getInstance().addVideoAppointmentInfo(TOKEN, c_m_b(USER_ID), c_m_b("" + NOW_SHOWING_ONLINE_DOC.getId()), c_m_b(paymentDetails), c_m_b("1"), c_m_b(amount),null, new ApiListener.AppointmentPOstListener() {
            @Override
            public void onAppointmentPOStSuccess(AppointmentAddResponse data) {
                MyProgressBar.dismiss();
                Toast.makeText(context, "Appointment ID  " + data.getAppointmentId(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(context, PatientHomeActivity.class));
                finishAffinity();
            }

            @Override
            public void onAppointmentPOStFailed(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void processToThePrescriptioReview(String paymentDetails) {

        Api.getInstance().add_payment_info_only(TOKEN, c_m_b(USER_ID), c_m_b("" + NOW_SHOWING_ONLINE_DOC.getId()), c_m_b(amount), c_m_b("0"), c_m_b("Prescription review"),c_m_b(paymentDetails), null,new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                Intent intent = new Intent(context, PrescriptionReviewSendingActivity.class);

                intent.putExtra("paymentInfo", paymentDetails);
                intent.putExtra("doctorID", "" + NOW_SHOWING_ONLINE_DOC.getId());
                intent.putExtra("partnerName", NOW_SHOWING_ONLINE_DOC.getName());
                intent.putExtra("partnerPhoto", NOW_SHOWING_ONLINE_DOC.getPhoto());
                intent.putExtra("amount",amount);
                intent.putExtra("paymentType","1");
                context.startActivity(intent);
            }

            @Override
            public void onBasicApiFailed(String msg) {

            }
        });





    }
}
