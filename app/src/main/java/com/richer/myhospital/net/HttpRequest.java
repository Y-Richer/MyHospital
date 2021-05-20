package com.richer.myhospital.net;

import com.richer.myhospital.home.historypage.model.ReserveRecord;
import com.richer.myhospital.home.model.City;
import com.richer.myhospital.home.model.CollectHospital;
import com.richer.myhospital.home.model.Hospital;
import com.richer.myhospital.home.model.Reserve;
import com.richer.myhospital.login.model.User;

import java.util.List;

import retrofit2.Callback;

public class HttpRequest {

    public static void login(String name, Callback<ResponseBody<User>> callback) {
        NetWork.api.login(name).enqueue(callback);
    }

    public static void register(User user, Callback<ResponseBody<Integer>> callback) {
        NetWork.api.register(user).enqueue(callback);
    }

    public static void getUser(int id, Callback<ResponseBody<User>> callback) {
        NetWork.api.getUser(id).enqueue(callback);
    }

    public static void getAllHospital(String city, Callback<ResponseBody<List<com.richer.myhospital.home.model.Hospital>>> callback) {
        NetWork.api.getAllHospital(city).enqueue(callback);
    }

    public static void getHospital(int id, Callback<ResponseBody<com.richer.myhospital.home.model.Hospital>> callback) {
        NetWork.api.getHospital(id).enqueue(callback);
    }

    public static void getAllExpert(int hospitalId, Callback<ResponseBody<List<com.richer.myhospital.home.model.Expert>>> callback) {
        NetWork.api.getAllExpert(hospitalId).enqueue(callback);
    }

    public static void reserve(Reserve reserve, Callback<ResponseBody<Integer>> callback) {
        NetWork.api.reserve(reserve).enqueue(callback);
    }

    public static void collectHospital(CollectHospital collectHospital, Callback<ResponseBody<Integer>> callback) {
        NetWork.api.collectHospital(collectHospital).enqueue(callback);
    }

    public static void getCollectedHospital(int userId, Callback<ResponseBody<List<com.richer.myhospital.home.model.Hospital>>> callback) {
        NetWork.api.getCollectedHospital(userId).enqueue(callback);
    }

    public static void unCollectHospital(CollectHospital collectHospital, Callback<ResponseBody<Integer>> callback) {
        NetWork.api.unCollectHospital(collectHospital).enqueue(callback);
    }

    public static void queryCollection(CollectHospital collectHospital, Callback<ResponseBody<List<CollectHospital>>> callback) {
        NetWork.api.queryCollection(collectHospital).enqueue(callback);
    }

    public static void getReserveRecord(int userId, Callback<ResponseBody<List<ReserveRecord>>> callback) {
        NetWork.api.getReserveRecord(userId).enqueue(callback);
    }

    public static void getCities(Callback<ResponseBody<List<City>>> callback) {
        NetWork.api.getCities().enqueue(callback);
    }

    public static void searchHospital(String condition, Callback<ResponseBody<List<Hospital>>> callback) {
        NetWork.api.searchHospital(condition).enqueue(callback);
    }

    public static void unReserve(ReserveRecord record, Callback<ResponseBody<Integer>> callback) {
        NetWork.api.unReserve(record).enqueue(callback);
    }

    public static void updateUser(User user, Callback<ResponseBody<Integer>> callback) {
        NetWork.api.updateUser(user).enqueue(callback);
    }

    public static void updatePassword(User user, Callback<ResponseBody<Integer>> callback) {
        NetWork.api.updatePassword(user).enqueue(callback);
    }

}
