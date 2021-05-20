package com.richer.myhospital.home.model;

public class Expert {

    int id;
    String sex;
    String name;
    String position;
    String hospitalId;
    String department;

    public Expert(String sex, String name, String position, String hospitalId, String department) {
        this.sex = sex;
        this.name = name;
        this.position = position;
        this.hospitalId = hospitalId;
        this.department = department;
    }

    public String getSex() {
        return sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
