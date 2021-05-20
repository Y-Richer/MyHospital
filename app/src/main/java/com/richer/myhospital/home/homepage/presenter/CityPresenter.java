package com.richer.myhospital.home.homepage.presenter;

import com.richer.myhospital.home.homepage.view.SwitchCityActivity;
import com.richer.myhospital.home.model.City;
import com.richer.myhospital.net.HttpRequest;
import com.richer.myhospital.net.ResponseBody;
import com.richer.myhospital.util.ToastUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityPresenter {

    SwitchCityActivity activity;

    public CityPresenter(SwitchCityActivity activity) {
        this.activity = activity;
    }

    public void getCities() {
        HttpRequest.getCities(new Callback<ResponseBody<List<City>>>() {
            @Override
            public void onResponse(Call<ResponseBody<List<City>>> call, Response<ResponseBody<List<City>>> response) {
                ResponseBody<List<City>> body = response.body();
                if (body != null) {
                    if (body.getStatus() == 200 && body.getData() != null) {
                        activity.setCityList(body.getData());
                    }else {
                        ToastUtil.show(activity, body.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<List<City>>> call, Throwable t) {

            }
        });
    }

}
