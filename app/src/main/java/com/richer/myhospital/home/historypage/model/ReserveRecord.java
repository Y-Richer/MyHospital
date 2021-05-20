package com.richer.myhospital.home.historypage.model;

public class ReserveRecord {

    String hospitalName;
    String departmentName;
    String expertName;
    String date;
    String time;
    String userId;
    String expertId;

    public ReserveRecord(String hospitalName, String departmentName, String expertName, String date, String time, String userId, String expertId) {
        this.hospitalName = hospitalName;
        this.departmentName = departmentName;
        this.expertName = expertName;
        this.date = date;
        this.time = time;
        this.userId = userId;
        this.expertId = expertId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }
}
