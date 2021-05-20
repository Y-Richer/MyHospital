package com.richer.myhospital.home.homepage.presenter;

import com.richer.myhospital.home.homepage.view.IHomeView;
import com.richer.myhospital.home.model.Hospital;
import com.richer.myhospital.net.HttpRequest;
import com.richer.myhospital.net.ResponseBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 2021/04/17 Richer
 * HomePagePresenter 首页相关网络请求
 */
public class HomePagePresenter {

    IHomeView view;

    public HomePagePresenter(IHomeView view) {
        this.view = view;
    }

    /**
     * 根据经纬度获取位置信息 https://api.yonyoucloud.com/apis/dst/apilinkstaticgeo/regecode
     * @param longitude 经度
     * @param latitude 纬度
     */
//    public void getLocation(String longitude, String latitude) {
//        Map<String, String> header = new HashMap<>();
//        header.put("apicode", KEY_GET_LOCATION);
//        Map<String, String> params = new HashMap<>();
//        params.put("location", longitude+","+latitude);
//        NetWorkUtil.enqueueGet(URL_GET_LOCATION, header, params, new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                view.showMessage("获取位置信息失败!");
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                String city = getCity(response.body().string());
//                view.setCity(city);
//            }
//        });
//    }

    /**
     * 解析JSON数据获得城市
     * @param
     * @return
     */
//    private String getCity(String response) {
//        try {
//            JSONObject object = new JSONObject(response);
//            JSONObject result = object.optJSONObject("regeocode");
//            if (result != null) {
//                JSONObject address = result.optJSONObject("addressComponent");
//                String city = address.optString("city", "北京");
//                return city;
//            }
//        } catch (JSONException e) {
//            view.showMessage("解析位置信息失败!");
//        }
//        return "北京";
//    }

    public void getHospital(String city) {
        HttpRequest.getAllHospital(city, new Callback<ResponseBody<List<Hospital>>>() {
            @Override
            public void onResponse(Call<ResponseBody<List<Hospital>>> call, Response<ResponseBody<List<Hospital>>> response) {
                ResponseBody<List<Hospital>> body = response.body();
                if (response.body() != null) {
                    if (body.getStatus() == 200) {
                        view.setNearByHospitals(body.getData());
                    }else {
                        view.showMessage(body.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<List<Hospital>>> call, Throwable t) {

            }
        });
    }
    /**
     * 根据城市获取医院列表 https://api.yonyoucloud.com/apilink/tempServicePages/34340367-565e-4026-936b-0fe75ffdc309_true.html
     * @param
     */
//    public void getNearByHospital(String city) {
//        //Bmob数据库
//        Map<String, String> condition = new HashMap<>();
//        condition.put("city", city);
//        BmobUtil.equalQuery(condition, new FindListener<Hospital>() {
//            @Override
//            public void done(List<Hospital> list, BmobException e) {
//                if(e == null) {
//                    if(list != null && list.size() > 0) {
//                        view.setNearByHospitals(list);
//                    }
////                    else {
////                        getHospitalByApi(city);
////                    }
//                }else {
//                    view.showMessage(e.getMessage());
//                }
//            }
//        });
//    }

    public void getCollectedHospital(int userId) {
        HttpRequest.getCollectedHospital(userId, new Callback<ResponseBody<List<Hospital>>>() {
            @Override
            public void onResponse(Call<ResponseBody<List<Hospital>>> call, Response<ResponseBody<List<Hospital>>> response) {
                ResponseBody<List<Hospital>> body = response.body();
                if (response.body() != null) {
                    if (body.getStatus() == 200) {
                        view.setCollectionHospitals(body.getData());
                    }else {
                        view.showMessage(body.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<List<Hospital>>> call, Throwable t) {

            }
        });
    }

//    public void getCollectionHospital(int userId) {
//        List<Hospital> hospitals = new ArrayList<>();
//        Map<String, String> condition = new HashMap<>();
//        condition.put("UserId", userId);
//        BmobUtil.equalQuery(condition, new FindListener<CollectHospital>() {
//            @Override
//            public void done(List<CollectHospital> list, BmobException e) {
//                if (e == null) {
//                    if (list != null && list.size() > 0) {
//                        for (CollectHospital collectHospital:list) {
//                            String hospitalId = collectHospital.getHospitalId();
//                            BmobQuery<Hospital> query = new BmobQuery<>();
//                            query.getObject(hospitalId, new QueryListener<Hospital>() {
//                                @Override
//                                public void done(Hospital hospital, BmobException e) {
//                                    if (e == null) {
//                                        hospitals.add(hospital);
//                                        view.setCollectionHospitals(hospitals);
//                                    }else {
//                                        view.showMessage(e.getMessage());
//                                    }
//                                }
//                            });
//                        }
//                    }
//                }else {
//                    view.showMessage(e.getMessage());
//                }
//            }
//        });
//    }

//    private void getHospitalByApi(String city) {
//        //用友api
//        Map<String, String> params = new HashMap<>();
//        params.put("name", city);
//        Map<String, String> header = new HashMap<>();
//        header.put("apicode", KEY_GET_HOSPITAL);
//        NetWorkUtil.enqueueGet(URL_GET_HOSPITAL, header, params, new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                view.showMessage("获取医院列表失败!");
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                String data = response.body().string();
//                view.setNearByHospitals(analyzeHospitals(data));
//            }
//        });
//    }

    /**
     * 解析医院信息
     * @param data
     * @return
     * {
     * 			"province": "福建",
     * 			"city": "福州",
     * 			"type": "综合医院 ",
     * 			"name": "福州市鼓楼区水部街道闽都社区卫生服务中心",
     * 			"rank": "一级丙等",
     * 			"address": "福州市古乐路5号 ",
     * 			"phone": "0591- ",
     * 			"key_point": "",
     * 			"expert": "",
     * 			"content": ""
     * }
     */
//    private List<Hospital> analyzeHospitals(String data) {
//        List<Hospital> hospitals = new ArrayList<>();
//        try {
//            JSONObject object = new JSONObject(data);
//            JSONArray result = object.getJSONArray("result");
//            for (int i=0;i<result.length();i++) {
//                JSONObject hospitalObject = result.getJSONObject(i);
//                String name = hospitalObject.optString("name");
//                String province = hospitalObject.optString("province");
//                String city = hospitalObject.optString("city");
//                String type = hospitalObject.optString("type");
//                String rank = hospitalObject.optString("rank");
//                String address = hospitalObject.optString("address");
//                String phone = hospitalObject.optString("phone");
//                String content = hospitalObject.optString("content");
//                Hospital hospital = new Hospital(name, province, city, type, rank, address, phone, content);
//                hospitals.add(hospital);
//            }
//        }catch (JSONException e) {
//            view.showMessage("解析医院信息失败!");
//        }
//        return hospitals;
//    }

}
