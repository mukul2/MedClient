package com.winkcoo.medx.service;

import android.graphics.Bitmap;

import com.winkcoo.medx.Utils.SessionManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.winkcoo.medx.service.AsyncTask.returnBitmap;


public class NotificationService extends FirebaseMessagingService {
    public NotificationService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //Toast.makeText(this, "helo", Toast.LENGTH_SHORT).show();
        NotificationHelper notificationHelper = new NotificationHelper(NotificationService.this);
        String title = remoteMessage.getData().get("title").toString();
        String body = remoteMessage.getData().get("body").toString();
        String intent = remoteMessage.getData().get("intent").toString();
        String targetUserType = remoteMessage.getData().get("targetUserType").toString();
        SessionManager sessionManager=new SessionManager(NotificationService.this);

       if (true) {

           if (remoteMessage.getData().get("image") != null && remoteMessage.getData().get("image").length() > 0) {
               String link = remoteMessage.getData().get("image");
               returnBitmap(link, new AsyncTask.downloadListener() {
                   @Override
                   public void onDownloaded(Bitmap bitmap) {
                       notificationHelper.createNotification(title,  body,intent, bitmap,targetUserType);


                   }
               });


           } else {
             //  notificationHelper.createNotification(title, "Body", intent, null);

           }
       }

    }
}
