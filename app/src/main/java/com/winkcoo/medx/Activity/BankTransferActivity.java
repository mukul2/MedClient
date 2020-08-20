package com.winkcoo.medx.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.gson.JsonElement;
import com.winkcoo.medx.R;
import com.winkcoo.medx.Utils.MyProgressBar;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.AppointmentAddResponse;
import com.winkcoo.medx.model.NotificationResponse;
import com.winkcoo.medx.model.StatusMessage;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.winkcoo.medx.Data.Data.CURRENCY_USD_SIGN;
import static com.winkcoo.medx.Data.Data.USER_NAME;
import static com.winkcoo.medx.Data.DataStore.NOW_SHOWING_DOC;
import static com.winkcoo.medx.Data.DataStore.NOW_SHOWING_ONLINE_DOC;
import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;

public class BankTransferActivity extends BaseActivity {
    @BindView(R.id.img)
    ImageView imageView;
    @BindView(R.id.tv_marchant)
    TextView tv_marchant;
    @BindView(R.id.tv_fees)
    TextView tv_fees;
    @BindView(R.id.ed_trans_id)
    EditText ed_trans_id;
    @BindView(R.id.cardSubmit)
    CardView cardSubmit;
    Uri resultUri;
    String type;
    String startMonth, endMonth;
    Calendar calendar;
    Context context = this;
    String amount, marchant;
    String paymentDetails, chamberApID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer);
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askPermission();
            }
        });

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            int j = (int) b.get("credit");
            type = (String) b.get("type");
            marchant = (String) b.get("marchant");
            tv_marchant.setText(marchant);
            amount = "" + j;
            tv_fees.setText(amount + " " + CURRENCY_USD_SIGN);
            chamberApID = (String) b.get("ref");


        }

        cardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentDetails = ed_trans_id.getText().toString().trim();

                if (type.equals("prescriptionRequest"))
                    processToThePrescriptionRequest(paymentDetails);
                if (type.equals("videoAppointment"))
                    processToTheAddVideoAppointment(paymentDetails);
                if (type.equals("prescriptionReview"))
                    processToThePrescriptioReview(paymentDetails);
                if (type.equals("chat"))
                    processToTheChatRequest(paymentDetails);

                if (type.equals("1MonthSubscription"))
                    processToThe1MonthSubscriptionRequest(paymentDetails, 1);
                if (type.equals("3MonthSubscription"))
                    processToThe1MonthSubscriptionRequest(paymentDetails, 3);
                if (type.equals("6MonthSubscription"))
                    processToThe1MonthSubscriptionRequest(paymentDetails, 6);
                if (type.equals("12MonthSubscription"))
                    processToThe1MonthSubscriptionRequest(paymentDetails, 12);
                if (type.equals("chamber")) {
                    processToChamberPayment(paymentDetails, NOW_SHOWING_DOC.getId());

                }
            }
        });
    }

    private void processToChamberPayment(String payID, int doc_id) {
        MyProgressBar.with(context);
        //chamberApID
        ApiListener.basicApiListener listener = new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                Toast.makeText(context, "ccc "+response.getMessage(), Toast.LENGTH_LONG).show();

                Intent i = new Intent(getBaseContext(), PatientHomeActivity.class);
                i.putExtra("paymentInfo", payID);
                i.putExtra("amount", amount);
                i.putExtra("paymentType", "1");
                i.putExtra("paypal_id", response.getMessage());
                //paymentInfo
                Log.i("mkl","goint to hit  notie");
                Log.i("mkl","noti 11");

                Api.getInstance().appNotification("admin", "New transaction", "New Pending Payment", "pending_payment", null, "a", new ApiListener.NotificationSentListener() {
                    @Override
                    public void onNotificationSentSuccess(JsonElement status) {
                       Toast.makeText(context, ""+status.toString(), Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finishAffinity();
                    }

                    @Override
                    public void onNotificationSentFailed(String msg) {
                      //  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    }
                });
                //startActivity(i);
                //finishAffinity();
                MyProgressBar.dismiss();
            }

            @Override
            public void onBasicApiFailed(String msg) {
                MyProgressBar.dismiss();

            }
        };

        MultipartBody.Part photo = null;
        if (resultUri != null) {
            File f = new File(resultUri.getPath());

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), f);
            photo = MultipartBody.Part.createFormData("photo", f.getName(), requestFile);
            Api.getInstance().add_payment_info_only_for_chamber_appoiontment(TOKEN, c_m_b(USER_ID), c_m_b("" + doc_id), c_m_b(amount), c_m_b("0"), c_m_b("Chamber Appointment"), c_m_b(payID), c_m_b(chamberApID), photo, listener);

        } else {
            Api.getInstance().add_payment_info_only_for_chamber_appoiontment(TOKEN, c_m_b(USER_ID), c_m_b("" + doc_id), c_m_b(amount), c_m_b("0"), c_m_b("Chamber Appointment"), c_m_b(payID), c_m_b(chamberApID), null, listener);

        }


    }

    private void askPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            openCamera();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void openCamera() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
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


                Api.getInstance().appNotification("admin", "New transaction", "New Pending Payment", "pending_payment", null, "a", new ApiListener.NotificationSentListener() {
                    @Override
                    public void onNotificationSentSuccess(JsonElement status) {
                        Toast.makeText(context, ""+status.toString(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, PatientHomeActivity.class));
                        finishAffinity();
                    }

                    @Override
                    public void onNotificationSentFailed(String msg) {
                        //  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    }
                });





            }

            @Override
            public void onBasicApiFailed(String msg) {
                MyProgressBar.dismiss();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            }
        };


        MultipartBody.Part photo = null;
        if (resultUri != null) {
            File f = new File(resultUri.getPath());

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), f);
            photo = MultipartBody.Part.createFormData("photo", f.getName(), requestFile);
            Api.getInstance().add_subscription_info(TOKEN, c_m_b(USER_ID), c_m_b("" + NOW_SHOWING_ONLINE_DOC.getId()), c_m_b(paymentDetails), c_m_b("" + month), c_m_b(startMonth), c_m_b(endMonth), c_m_b("0"), c_m_b(amount), photo, listener);

        } else {
            Api.getInstance().add_subscription_info(TOKEN, c_m_b(USER_ID), c_m_b("" + NOW_SHOWING_ONLINE_DOC.getId()), c_m_b(paymentDetails), c_m_b("" + month), c_m_b(startMonth), c_m_b(endMonth), c_m_b("0"), c_m_b(amount), null, listener);

        }


    }

//    private void processToThe6MonthSubscriptionRequest(String paymentDetails) {
//        MyProgressBar.with(context);
//        startMonth = "" + calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-" + (1 + calendar.get(Calendar.DATE));
//
//        calendar.add(Calendar.MONTH, 6);
//        endMonth = "" + calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-" + (1 + calendar.get(Calendar.DATE));
//        Api.getInstance().add_subscription_info(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), paymentDetails, "6", startMonth, endMonth, "0", amount, new ApiListener.basicApiListener() {
//            @Override
//            public void onBasicSuccess(StatusMessage response) {
//                MyProgressBar.dismiss();
//                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
//
//                startActivity(new Intent(context, PatientHomeActivity.class));
//                finishAffinity();
//            }
//
//            @Override
//            public void onBasicApiFailed(String msg) {
//
//            }
//        });
//
//    }
//
//    private void processToThe3MonthSubscriptionRequest(String paymentDetails) {
//        MyProgressBar.with(context);
//        startMonth = "" + calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-" + (1 + calendar.get(Calendar.DATE));
//
//        calendar.add(Calendar.MONTH, 3);
//        endMonth = "" + calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-" + (1 + calendar.get(Calendar.DATE));
//        Api.getInstance().add_subscription_info(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), paymentDetails, "3", startMonth, endMonth, "0", amount, new ApiListener.basicApiListener() {
//            @Override
//            public void onBasicSuccess(StatusMessage response) {
//                MyProgressBar.dismiss();
//                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
//
//                startActivity(new Intent(context, PatientHomeActivity.class));
//                finishAffinity();
//            }
//
//            @Override
//            public void onBasicApiFailed(String msg) {
//
//            }
//        });
//
//    }
//
//    private void processToThe12MonthSubscriptionRequest(String paymentDetails) {
//        MyProgressBar.with(context);
//        startMonth = "" + calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-" + (1 + calendar.get(Calendar.DATE));
//
//        calendar.add(Calendar.MONTH, 12);
//        endMonth = "" + calendar.get(Calendar.YEAR) + "-" + (1 + calendar.get(Calendar.MONTH)) + "-" + (1 + calendar.get(Calendar.DATE));
//        Api.getInstance().add_subscription_info(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), paymentDetails, "12", startMonth, endMonth, "0", amount, new ApiListener.basicApiListener() {
//            @Override
//            public void onBasicSuccess(StatusMessage response) {
//                MyProgressBar.dismiss();
//                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
//
//                startActivity(new Intent(context, PatientHomeActivity.class));
//                finishAffinity();
//            }
//
//            @Override
//            public void onBasicApiFailed(String msg) {
//
//            }
//        });
//
//    }

    private void processToTheChatRequest(String paymentDetails) {
        MyProgressBar.with(context);

        Api.getInstance().addChatRequest(TOKEN, USER_ID, "" + NOW_SHOWING_ONLINE_DOC.getId(), paymentDetails, "0", amount, new ApiListener.AppointmentPOstListener() {
            @Override
            public void onAppointmentPOStSuccess(AppointmentAddResponse data) {
                MyProgressBar.dismiss();
                Toast.makeText(context, data.getMessage(), Toast.LENGTH_SHORT).show();


                Api.getInstance().appNotification("admin", "New transaction", "New Pending Payment", "pending_payment", null, "a", new ApiListener.NotificationSentListener() {
                    @Override
                    public void onNotificationSentSuccess(JsonElement status) {
                        Intent i = new Intent(context, ChatActivityCommon.class);
                        i.putExtra("partner_id", "" + NOW_SHOWING_ONLINE_DOC.getId());
                        i.putExtra("partner_name", NOW_SHOWING_ONLINE_DOC.getName());
                        i.putExtra("partner_photo", NOW_SHOWING_ONLINE_DOC.getPhoto());
                        i.putExtra("initMessage", amount + CURRENCY_USD_SIGN + " has been paid for Chat service");
                        context.startActivity(i);
                        finish();
                    }

                    @Override
                    public void onNotificationSentFailed(String msg) {
                        //  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    }
                });


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
        MyProgressBar.with(context);
        MultipartBody.Part photo = null;
        if (resultUri != null) {
            File f = new File(resultUri.getPath());

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), f);
            photo = MultipartBody.Part.createFormData("photo", f.getName(), requestFile);
        }
        Api.getInstance().add_payment_info_only(TOKEN, c_m_b(USER_ID), c_m_b("" + NOW_SHOWING_ONLINE_DOC.getId()), c_m_b(amount), c_m_b("0"), c_m_b("Prescription request"), c_m_b(paymentDetails), photo, new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                MyProgressBar.dismiss();
                Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), PrescriptonRequestMakingActivity.class);
                i.putExtra("paymentInfo", paymentDetails);
                i.putExtra("paymentType", "0");
                i.putExtra("amount", amount);
                i.putExtra("paypal_id", response.getMessage());
                //paymentInfo
                startActivity(i);

                Api.getInstance().appNotification("admin", "New transaction", "New Pending Payment", "pending_payment", null, "a", new ApiListener.NotificationSentListener() {
                    @Override
                    public void onNotificationSentSuccess(JsonElement status) {

                    }

                    @Override
                    public void onNotificationSentFailed(String msg) {
                        //  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    }
                });


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
        ApiListener.AppointmentPOstListener listener = new ApiListener.AppointmentPOstListener() {
            @Override
            public void onAppointmentPOStSuccess(AppointmentAddResponse data) {
                MyProgressBar.dismiss();
                Toast.makeText(context, "Appointment ID  " + data.getAppointmentId(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(context, PatientHomeActivity.class));
                finishAffinity();

                Api.getInstance().appNotification("admin", "New transaction", "New Pending Payment", "pending_payment", null, "a", new ApiListener.NotificationSentListener() {
                    @Override
                    public void onNotificationSentSuccess(JsonElement status) {

                    }

                    @Override
                    public void onNotificationSentFailed(String msg) {
                        //  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onAppointmentPOStFailed(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


            }
        };
        MultipartBody.Part photo = null;
        if (resultUri != null) {

            File f = new File(resultUri.getPath());

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), f);
            photo = MultipartBody.Part.createFormData("photo", f.getName(), requestFile);
            Api.getInstance().addVideoAppointmentInfo(TOKEN, c_m_b(USER_ID), c_m_b("" + NOW_SHOWING_ONLINE_DOC.getId()), c_m_b(paymentDetails), c_m_b("0"), c_m_b(amount), photo, listener);

        } else {
            Api.getInstance().addVideoAppointmentInfo(TOKEN, c_m_b(USER_ID), c_m_b("" + NOW_SHOWING_ONLINE_DOC.getId()), c_m_b(paymentDetails), c_m_b("0"), c_m_b(amount), null, listener);

        }


    }

    private void processToThePrescriptioReview(String paymentDetails) {
        MyProgressBar.with(context);
        MultipartBody.Part photo = null;
        if (resultUri != null) {
            File f = new File(resultUri.getPath());

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), f);
            photo = MultipartBody.Part.createFormData("photo", f.getName(), requestFile);
        }
        Api.getInstance().add_payment_info_only(TOKEN, c_m_b(USER_ID), c_m_b("" + NOW_SHOWING_ONLINE_DOC.getId()), c_m_b(amount), c_m_b("0"), c_m_b("Prescription review"), c_m_b(paymentDetails), photo, new ApiListener.basicApiListener() {
            @Override
            public void onBasicSuccess(StatusMessage response) {
                MyProgressBar.dismiss();
                Intent intent = new Intent(context, PrescriptionReviewSendingActivity.class);

                intent.putExtra("paymentInfo", paymentDetails);
                intent.putExtra("doctorID", "" + NOW_SHOWING_ONLINE_DOC.getId());
                intent.putExtra("partnerName", NOW_SHOWING_ONLINE_DOC.getName());
                intent.putExtra("partnerPhoto", NOW_SHOWING_ONLINE_DOC.getPhoto());
                intent.putExtra("amount", amount);
                intent.putExtra("paymentType", "0");
                intent.putExtra("paypal_id", response.getMessage());

                context.startActivity(intent);


                Api.getInstance().appNotification("admin", "New transaction", "New Pending Payment", "pending_payment", null, "a", new ApiListener.NotificationSentListener() {
                    @Override
                    public void onNotificationSentSuccess(JsonElement status) {

                    }

                    @Override
                    public void onNotificationSentFailed(String msg) {
                        //  Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onBasicApiFailed(String msg) {

            }
        });


    }
}