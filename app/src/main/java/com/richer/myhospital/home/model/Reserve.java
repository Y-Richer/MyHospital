package com.richer.myhospital.home.model;

public class Reserve {
    int id;
    int userId;
    int expertId;
    String date;
    String time;
    String phone;

    public Reserve(int userId, int expertId) {
        this.userId = userId;
        this.expertId = expertId;
    }

    public Reserve(int userId, int expertId, String date, String time, String phone) {
        this.userId = userId;
        this.expertId = expertId;
        this.date = date;
        this.time = time;
        this.phone = phone;
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

    public int getExpertId() {
        return expertId;
    }

    public void setExpertId(int expertId) {
        this.expertId = expertId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
