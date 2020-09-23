package com.winkcoo.medx.api;


import com.google.gson.JsonElement;
import com.winkcoo.medx.model.AllCollectionWithdraModel;
import com.winkcoo.medx.model.AmbulanceModel;
import com.winkcoo.medx.model.AppointmentAddResponse;
import com.winkcoo.medx.model.AppointmentModel;
import com.winkcoo.medx.model.AppointmentModel2;
import com.winkcoo.medx.model.AppointmentModelNew;
import com.winkcoo.medx.model.AppointmentResponse;
import com.winkcoo.medx.model.BasicByDrResponse;
import com.winkcoo.medx.model.BasicInfoModel;
import com.winkcoo.medx.model.BillItem;
import com.winkcoo.medx.model.BillSummery;
import com.winkcoo.medx.model.BlogCategoryNameID;
import com.winkcoo.medx.model.BlogModel;
import com.winkcoo.medx.model.CallHistoryPatient;
import com.winkcoo.medx.model.DepartmentModel;
import com.winkcoo.medx.model.DeptModel;
import com.winkcoo.medx.model.DiseasesModel;
import com.winkcoo.medx.model.DoctorModel;
import com.winkcoo.medx.model.DocumentModel;
import com.winkcoo.medx.model.DrChamberResponse;
import com.winkcoo.medx.model.DrEduChInfoModel;
import com.winkcoo.medx.model.DrOnlineServiceModel;
import com.winkcoo.medx.model.DrServiceModel;
import com.winkcoo.medx.model.EducationSkillModel;
import com.winkcoo.medx.model.FetchProfileResponse;
import com.winkcoo.medx.model.LoginResponse;
import com.winkcoo.medx.model.MedicineModel;
import com.winkcoo.medx.model.NoticeModel;
import com.winkcoo.medx.model.NotificationResponse;
import com.winkcoo.medx.model.OnlineDoctorModel;
import com.winkcoo.medx.model.OnlineDoctorsModel;
import com.winkcoo.medx.model.PaymentMethodsModel;
import com.winkcoo.medx.model.PrescriptionModel;
import com.winkcoo.medx.model.PrescriptionRequestModel;
import com.winkcoo.medx.model.PrescriptionReviewModel;
import com.winkcoo.medx.model.ProfileUpdateResponse;
import com.winkcoo.medx.model.QueryModel;
import com.winkcoo.medx.model.RecomentationModel;
import com.winkcoo.medx.model.SearchDoctorModel;
import com.winkcoo.medx.model.ServiceNameInfo;
import com.winkcoo.medx.model.SignUpResponse;
import com.winkcoo.medx.model.StatusId;
import com.winkcoo.medx.model.StatusMessage;
import com.winkcoo.medx.model.StatusResponse;
import com.winkcoo.medx.model.SubscriptionViewResponse;
import com.winkcoo.medx.model.SubscriptionsModel;
import com.winkcoo.medx.model.Success;
import com.winkcoo.medx.model.TestList;
import com.winkcoo.medx.model.TestModel;
import com.winkcoo.medx.model.TestRecomendationModel;
import com.winkcoo.medx.model.TrackModel;
import com.winkcoo.medx.model.TreatmentHistoryModel;
import com.winkcoo.medx.model.UserProfileResponse;
import com.winkcoo.medx.model.VideoAppointmentModel;
import com.winkcoo.medx.model.VideoCallHistoryModel;
import com.winkcoo.medx.model.VideoCallModel;

import java.util.List;

public class ApiListener {
    public interface UserBillDownloadListener {
        void onUserBillDownloadSuccess(List<BillItem> list);

        void onUserBillDownloadFailed(String msg);
    }

    public interface doctorSearchListener {
        void onSearchSuccess(List<DoctorModel> list);

        void onSuccessFailed(String msg);
    }

    public interface UserBillSummeryDownloadListener {
        void onUserBillSummeryDownloadSuccess(BillSummery list);

        void onUserBillSummeryDownloadFailed(String msg);
    }

    public interface profileFetchListener {
        void onprofileFetchSuccess(FetchProfileResponse list);

        void onprofileFetchFailed(String msg);
    }

    public interface updatePassword {
        void onUpdatePasswordSuccess(StatusMessage data);

        void onUpdatePasswordFailed(String msg);
    }

    public interface BlogCategoryDownloadListener {
        void onBlogCategoryDownloadSuccess(List<BlogCategoryNameID> list);

        void onBlogCategoryDownloadFailed(String msg);
    }

    public interface NoticesDownloadListener {
        void onNoticesDownloadSuccess(List<NoticeModel> list);

        void onNoticesDownloadFailed(String msg);
    }

    public interface NoticesStatusUpdateListener {
        void onNoticesStatusUpdateSuccess(StatusMessage list);

        void onNoticesStatusUpdateFailed(String msg);
    }

    public interface singlePrescriptionDownloadListener {
        void onPrescriptionDownloadSuccess(PrescriptionModel list);

        void onPrescriptionDownloaFailed(String msg);
    }

    public interface PrescriptionAddListener {
        void onrescriptionAddSuccess(StatusMessage data);

        void onrescriptionAddFailed(String msg);
    }

    public interface ReplyPrescriptionRequestListener {
        void onReplyPrescriptionRequestSuccess(StatusMessage data);

        void onReplyPrescriptionRequestFailed(String msg);
    }

    public interface DrAddServiceListener {
        void onDrAddServiceSuccess(StatusMessage data);

        void onDrAddServiceFailed(String msg);
    }

    public interface DrdeleteServiceListener {
        void onDrDeleteServiceSuccess(StatusMessage data);

        void onDrDeleteServiceFailed(String msg);
    }

    public interface updateDrServiceListener {
        void onDrUpdateServiceSuccess(StatusMessage data);

        void onDrUpdateServiceFailed(String msg);
    }

    public interface PatientSignUPListener {
        void onPatientSignUPSuccess(SignUpResponse list);

        void onPatientSignUPSuccessFailed(String msg);
    }

    public interface BlogDownloadListener {
        void onBlogDownloaSuccess(List<BlogModel> list);

        void onBlogDownloaSuccessFailed(String msg);
    }

    public interface DocDocUploadListener {
        void onDocDocUploadSuccess(StatusMessage data);

        void onDocDocUploadFailed(String msg);
    }

    public interface prescriptionUploadListener {
        void onPrescriptionUploadSuccess(StatusMessage data);

        void onPrescriptionUploadFailed(String msg);
    }

    public interface DiseasesDownloadListener {
        void onDiseasesDownloadSuccess(List<DiseasesModel> list);

        void onDiseasesDownloadSuccessFailed(String msg);
    }

    public interface successListener {
        void Onsuccess(Success response);

        void Onfailed(String message);

    }

    public interface diseasesAddListener {
        void onDiseasesAddSuccess(StatusMessage list);

        void onDiseasesAddSuccessFailed(String msg);
    }

    public interface DocSearchListener {
        void onDocSearchSuccess(List<SearchDoctorModel> list);

        void onDocSearchFailed(String msg);
    }

    public interface doctorEduSkillDownloadListener {
        void ondoctorEduSkillDownloadSuccess(EducationSkillModel list);

        void ondoctorEduSkillDownloadSuccessFailed(String msg);
    }

    public interface CommonappointmentDownloadListener {
        void onAppointmentDownloadSuccess(List<AppointmentModel> list);

        void onAppointmentDownloadFailed(String msg);
    }
    public interface AvailableInfoDownloadListener {
        void onAvailableInfoDownloadSuccess(JsonElement data);

        void onAvailableInfoDownloadFailed(String msg);
    }

    public interface DrRecomentationDownloadListener {
        void onRecomendationDownloadSuccess(List<RecomentationModel> list);

        void onRecomendationFailed(String msg);
    }

    public interface patientTreatmentHistoryListener {
        void onpatientTreatmentHistorySearchSuccess(List<TreatmentHistoryModel> list);

        void onpatientTreatmentHistorySuccessFailed(String msg);
    }

    public interface servePostListener {
        void onServePostSuccess(StatusMessage response);

        void onServePostFailed(String msg);
    }

    public interface drServicePostListener {
        void ondrServicePostSuccess(StatusMessage response);

        void ondrServicePostFailed(String msg);
    }

    public interface patientCallLogListener {
        void onPatientCallLogSuccess(List<CallHistoryPatient> list);

        void onPatientCallLogFailed(String msg);
    }

    public interface doctorCallLogListener {
        void onDoctorCallLogSuccess(List<CallHistoryPatient> list);

        void onDoctorCallLogFailed(String msg);
    }

    public interface doctorOnlineStatusChangeListener {
        void ondoctorOnlineStatusChangeSuccess(StatusMessage statusMessage);

        void ondoctorOnlineStatusChangeFailed(String msg);
    }

    public interface PushCallLogListener {
        void onPushCallLogSuccess(StatusMessage statusMessage);

        void onPushCallLogFailed(String msg);
    }

    public interface PresCriptionDownloadListenerPatient {
        void onPrescriptionDownloadSuccess(List<PrescriptionModel> data);

        void onPrescriptionDownloadFailed(String msg);
    }

    public interface DownloadAmbulanceListInfoListener {
        void onDownloadAmbulanceListInfoSuccess(List<AmbulanceModel> status);

        void onDownloadAmbulanceListFailed(String msg);
    }

    public interface VideoCallHistoryDownloadListenerPatient {
        void onVideoCallHistoryDownloadSuccess(List<VideoCallHistoryModel> data);

        void onVideoCallHistoryDownloadFailed(String msg);
    }

    public interface publicQueryPostListenerPatient {
        void onPublicQueryPostSuccess(StatusMessage data);

        void onPublicQueryPostFailed(String msg);
    }

    public interface publicQueryDownloadListenerPatient {
        void onPublicQueryDownloadSuccess(List<QueryModel> data);

        void onPublicQueryDownloadFailed(String msg);
    }

    public interface PRofileUpdateListenerPatient {
        void onPRofileUpdateSuccess(ProfileUpdateResponse data);

        void onPRofileUpdateFailed(String msg);
    }

    public interface DrServicesDownloadListener {
        void onDrServicesDownloadSuccess(List<DrOnlineServiceModel> data);

        void onDrServicesDownloadFailed(String msg);
    }

    public interface AllServiceDownloadListener {
        void onAllServiceDownloadSuccess(List<ServiceNameInfo> data);

        void onAllServiceDownloadFailed(String msg);
    }

    public interface onlineDoctorListener {
        void onOnlineDoctorSearchSuccess(List<VideoCallModel> list);

        void onOnlineDoctorSearchFailed(String msg);
    }

    public interface TestDownloadListener {
        void onTestDownloadSuccess(List<TestList> list);

        void onTestDownloadFailed(String msg);
    }

    public interface OnlineDoctorsDownloadListener {
        void onOnlineDoctorsDownloadSuccess(List<OnlineDoctorModel> list);

        void onOnlineDoctorsDownloadFailed(String msg);
    }

    public interface DrServiceDownloadListener {
        void onDrServiceDownloadSuccess(List<DrServiceModel> list);

        void onDrServiceDownloadFailed(String msg);
    }

    public interface appointmentSearchListener {
        void onAppointmentSearchSuccess(List<AppointmentModel2> list);

        void onAppointmentSearchFailed(String msg);
    }

    public interface DeptDownloadListener {
        void onDepartmentDownloadSuccess(List<DeptModel> list);

        void onDepartmentDownloadFailed(String msg);
    }

    public interface chamberListDownloadListener {
        void onChamberListDownloadSuccess(List<DrChamberResponse> list);

        void onChamberListDownloadFailed(String msg);
    }

    public interface drChamberEduSkillDownloadListener {
        void onChamberEduSkillDownloadSuccess(DrEduChInfoModel list);

        void onChamberEduSkillDownloadFailed(String msg);
    }

    public interface departmentsDownloadListener {
        void onDepartmentsListDownloadSuccess(List<DepartmentModel> list);

        void onDepartmentsListDownloadFailed(String msg);
    }

    public interface basicInfoDownloadListener {
        void onBasicInfoDownloadSuccess(BasicInfoModel data);

        void onBasicInfoDownloadFailed(String msg);
    }

    public interface AppointmentPOstListener {
        void onAppointmentPOStSuccess(AppointmentAddResponse data);

        void onAppointmentPOStFailed(String msg);
    }
    public interface SlotSearchListener {
        void onSlotSearchSuccess(List<StatusMessage> data);

        void onSlotSearchFailed(String msg);
    }

    public interface SubscriptionViewListener {
        void onSubscriptionViewSuccess(SubscriptionViewResponse data);

        void onSubscriptionViewFailed(String msg);
    }

    public interface SubscriptionListDownlaodListener {
        void onSubscriptionListDownlaodSuccess(List<SubscriptionsModel> data);

        void onSubscriptionListDownlaodFailed(String msg);
    }

    public interface VideoCallReqListDownlaodListener {
        void onVideoCallReqListDownlaodSuccess(List<VideoAppointmentModel> data);

        void onVideoCallReqListDownlaodFailed(String msg);
    }

    public interface TestRecomDownloadListener {
        void onTestRecomDownloadSuccess(List<TestRecomendationModel> data);

        void onTestRecomDownloadFailed(String msg);
    }

    public interface MyPrescriptionRequestDownloadListener {
        void onMyPrescriptionRequestDownloadSuccess(List<PrescriptionRequestModel> data);

        void onMyPrescriptionRequestDownloadFailed(String msg);
    }

    public interface drBasicInfoPostListener {
        void onBasicInfoPostSuccess(StatusId data);

        void onBasicInfoPostFailed(String msg);
    }

    public interface drSchedulePostListener {
        void ondrSchedulePostSuccess(StatusMessage data);

        void ondrSchedulePostFailed(String msg);
    }

    public interface CheckMobileListener {
        void onMobileCheckSuccess(StatusResponse status);

        void onMobileCheckFailed(String msg);
    }

    public interface LoginUserListener {
        void onUserLoginSuccess(LoginResponse status);

        void onUserLoginFailed(String msg);
    }

    public interface NotificationSentListener {
        void onNotificationSentSuccess(JsonElement status);

        void onNotificationSentFailed(String msg);
    }

    public interface PostEducationInfoListener {
        void onPostEducationInfoSuccess(StatusMessage status);

        void onPostEducationInfoFailed(String msg);
    }
    public interface PaymentMethodsDownloadListener {
        void onPaymentMethodsDownloadSuccess(List<PaymentMethodsModel> data);

        void onPaymentMethodsDownloadFailed(String msg);
    }

    public interface DownloadMedicinesListInfoListener {
        void onDownloadMedicinesListInfoSuccess(List<MedicineModel> status);

        void onDownloadMedicinesListFailed(String msg);
    }

    public interface DownloadTestListInfoListener {
        void onDownloadTestListInfoSuccess(List<TestModel> status);

        void onDownloadTestListFailed(String msg);
    }

    public interface addTestRecListener {
        void onAddTestRecSuccess(StatusMessage status);

        void onAddTestRecFailed(String msg);
    }

    public interface DoctorDocDownloadListener {
        void onDoctorDocDownloadSuccess(List<DocumentModel> status);

        void onDoctorDocDownloadFailed(String msg);
    }

    public interface NumberUniqueCheckListener {
        void onNumberUniqueCheckSuccess(StatusMessage status);

        void onNumberUniqueCheckFailed(String msg);
    }

    public interface DownloadOnlineDocListener {
        void onOnlineDocSearchSuccess(List<OnlineDoctorsModel> status);

        void onOnlineDocSearchFailed(String msg);
    }

    public interface PostSkillInfoListener {
        void onPostSkillInfoSuccess(StatusMessage status);

        void onPostSkillInfoFailed(String msg);
    }

    public interface appoinetmentPOstListener {
        void onAppointmentPostSuccess(StatusResponse status);

        void onAppointmentPostFailed(String msg);
    }

    public interface appoinetmentsDownloadListener {
        void onAppointmentDownloadSuccess(List<AppointmentModelNew> status);

        void onAppointmentDownloadFailed(String msg);
    }

    public interface dataDownloadListener {
        void onDownloaded(List<AppointmentModel> status);
    }

    public interface patientAllDataDownloadListener {
        void onDownloaded(AppointmentResponse status);
    }

    public interface patientNotificationDataDownloadListener {
        void onNotificationDownloaded(List<RecomentationModel> status);
    }

    public interface appointmentStateChangeListener {
        void onAppointmentChangeSuccess(StatusMessage list);

        void onPppointmentChangeFailed(String msg);
    }

    public interface profileDownloadListener {
        void onprofileDownloadSuccess(UserProfileResponse list);

        void onprofileDownloadFailed(String msg);
    }

    public interface drprofileUpdateListener {
        void ondrprofileUpdateSuccess(StatusResponse list);

        void ondrprofileUpdateFailed(String msg);
    }

    public interface testNamesDownloadListener {
        void ontestNamesDownloadSuccess(BasicByDrResponse data);

        void ontestNamesDownloadFailed(String msg);
    }

    public interface recomendationTestPostListener {
        void onrecomendationTestPostSuccess(StatusResponse response);

        void onrecomendationTestPostFailed(String msg);
    }

    public interface prescriptionPostListener {
        void onPrescriptionPostSuccess(StatusMessage response);

        void onPrescriptionPostFailed(String msg);
    }
    public interface basicApiListener {
        void onBasicSuccess(StatusMessage response);

        void onBasicApiFailed(String msg);
    }

    public interface PaymentListDownloadListener {
        void onPaymentListDownloadSuccess(AllCollectionWithdraModel response);

        void onPaymentListDownloadFailed(String msg);
    }

    public interface VideoCallPostListener {
        void onVideoCallPostSuccess(StatusMessage response);

        void onVideoCallPostFailed(String msg);
    }

    public interface ReviewRequestDownloadListener {
        void onReviewRequestDownloadSuccess(List<PrescriptionReviewModel> response);

        void onReviewRequestDownloadFailed(String msg);
    }

    public interface BlogPostListener {
        void onBlogPostSuccess(StatusMessage response);

        void onBlogPostFailed(String msg);
    }

    public interface TrackListener {
        void onTrackSuccess(List<TrackModel> response);

        void onTrackFailed(String msg);
    }

    public interface PrescriptionRequestListener {
        void onPrescriptionRequestSuccess(StatusMessage response);

        void onPrescriptionRequestFailed(String msg);
    }
}
