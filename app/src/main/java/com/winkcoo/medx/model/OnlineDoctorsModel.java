package com.winkcoo.medx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnlineDoctorsModel {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("photo")
@Expose
private String photo;
@SerializedName("designation_title")
@Expose
private String designation_title;

@SerializedName("video_call_available_time")
@Expose
private String video_call_available_time;

@SerializedName("department")
@Expose
private Integer department;
@SerializedName("online_doctors_service_info")
@Expose
private List<OnlineDoctorsServiceInfo> onlineDoctorsServiceInfo = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getPhoto() {
return photo;
}

public void setPhoto(String photo) {
this.photo = photo;
}

public Integer getDepartment() {
return department;
}

public void setDepartment(Integer department) {
this.department = department;
}

public List<OnlineDoctorsServiceInfo> getOnlineDoctorsServiceInfo() {
return onlineDoctorsServiceInfo;
}

    public String getVideo_call_available_time() {
        return video_call_available_time;
    }

    public void setVideo_call_available_time(String video_call_available_time) {
        this.video_call_available_time = video_call_available_time;
    }

    public void setOnlineDoctorsServiceInfo(List<OnlineDoctorsServiceInfo> onlineDoctorsServiceInfo) {
this.onlineDoctorsServiceInfo = onlineDoctorsServiceInfo;
}

    public String getDesignation_title() {
        return designation_title;
    }

    public void setDesignation_title(String designation_title) {
        this.designation_title = designation_title;
    }
}