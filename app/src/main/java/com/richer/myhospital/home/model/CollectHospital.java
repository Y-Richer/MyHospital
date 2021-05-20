package com.richer.myhospital.home.model;

public class CollectHospital {

    int id;
    int userId;
    int hospitalId;

    public CollectHospital() {
    }

    public CollectHospital(int userId, int hospitalId) {
        this.userId = userId;
        this.hospitalId = hospitalId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

}
