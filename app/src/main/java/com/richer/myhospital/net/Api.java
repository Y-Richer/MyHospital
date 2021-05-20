package com.richer.myhospital.net;

import com.richer.myhospital.home.historypage.model.ReserveRecord;
import com.richer.myhospital.home.model.City;
import com.richer.myhospital.home.model.CollectHospital;
import com.richer.myhospital.home.model.Hospital;
import com.richer.myhospital.home.model.Reserve;
import com.richer.myhospital.login.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @GET("login/{name}")
    Call<ResponseBody<User>> login(@Path("name") String name);

    @POST("register")
    Call<ResponseBody<Integer>> register(@Body User user);

    @GET("getUser/{id}")
    Call<ResponseBody<User>> getUser(@Path("id") int id);

    @GET("hospital/getAll/{city}")
    Call<ResponseBody<List<com.richer.myhospital.home.model.Hospital>>> getAllHospital(@Path("city") String city);

    @GET("getHospital/{id}")
    Call<ResponseBody<com.richer.myhospital.home.model.Hospital>> getHospital(@Path("id") int id);

    @GET("getExpert/{hospitalId}")
    Call<ResponseBody<List<com.richer.myhospital.home.model.Expert>>> getAllExpert(@Path("hospitalId") int hospitalId);

    @POST("reserve")
    Call<ResponseBody<Integer>> reserve(@Body Reserve reserve);

    @POST("collectHospital")
    Call<ResponseBody<Integer>> collectHospital(@Body CollectHospital collect);

    @GET("getCollectedHospital/{userId}")
    Call<ResponseBody<List<com.richer.myhospital.home.model.Hospital>>> getCollectedHospital(@Path("userId") int userId);

    @POST("unCollectHospital")
    Call<ResponseBody<Integer>> unCollectHospital(@Body CollectHospital collect);

    @POST("queryCollection")
    Call<ResponseBody<List<CollectHospital>>> queryCollection(@Body CollectHospital collect);

    @GET("getReserveRecord/{userId}")
    Call<ResponseBody<List<ReserveRecord>>> getReserveRecord(@Path("userId") int userId);

    @GET("getCities")
    Call<ResponseBody<List<City>>> getCities();

    @GET("searchHospital/{condition}")
    Call<ResponseBody<List<Hospital>>> searchHospital(@Path("condition") String condition);

    @POST("unReserve")
    Call<ResponseBody<Integer>> unReserve(@Body ReserveRecord record);

    @POST("updateUser")
    Call<ResponseBody<Integer>> updateUser(@Body User user);

    @POST("updatePassword")
    Call<ResponseBody<Integer>> updatePassword(@Body User user);

}
