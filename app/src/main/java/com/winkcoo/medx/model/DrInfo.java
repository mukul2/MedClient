package com.winkcoo.medx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("video_call_available_time")
    @Expose
    private String video_call_available_time = "";
    @SerializedName("department_info")
    @Expose
    private DepartmentModel department_info = null;

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

    public DepartmentModel getDepartment_info() {
        return department_info;
    }

    public void setDepartment_info(DepartmentModel department_info) {
        this.department_info = department_info;
    }

    public String getVideo_call_available_time() {
        return video_call_available_time;
    }

    public void setVideo_call_available_time(String video_call_available_time) {
        this.video_call_available_time = video_call_available_time;
    }
}