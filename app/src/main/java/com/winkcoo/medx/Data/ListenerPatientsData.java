package com.winkcoo.medx.Data;

import com.winkcoo.medx.api.ApiListener;

/**
 * Created by mukul on 3/20/2019.
 */

public class ListenerPatientsData {
    public static ApiListener.patientAllDataDownloadListener PatientALLDataDownloadListener;
    public static ApiListener.patientNotificationDataDownloadListener PatientNotificationDataDownloadListener;
    public  static void setPatientALLDataDownloadListener(ApiListener.patientAllDataDownloadListener liste) {
        PatientALLDataDownloadListener = liste;
    }

    public static void setPatientNotificationDataDownloadListener(ApiListener.patientNotificationDataDownloadListener patientNotificationDataDownloadListener) {
        PatientNotificationDataDownloadListener = patientNotificationDataDownloadListener;
    }
}
