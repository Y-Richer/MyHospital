package com.richer.myhospital.home.homepage.view;

import com.richer.myhospital.home.model.Hospital;

import java.util.List;

public interface IHomeView {

    void showMessage(String message);
    void setCity(String city);
    void setNearByHospitals(List<Hospital> hospitals);
    void setCollectionHospitals(List<Hospital> hospitals);

}
