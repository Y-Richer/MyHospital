package com.richer.myhospital.home.homepage.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richer.myhospital.R;
import com.richer.myhospital.home.model.CollectHospital;
import com.richer.myhospital.home.model.Hospital;
import com.richer.myhospital.net.HttpRequest;
import com.richer.myhospital.net.ResponseBody;
import com.richer.myhospital.util.BmobUtil;
import com.richer.myhospital.util.ToastUtil;
import com.richer.myhospital.util.UserManager;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView mProvinceTv;
    TextView mAddressTv;
    TextView mTypeTv;
    TextView mRankTv;
    TextView mPhoneTv;
    LinearLayout mExpertLayout;
    TextView mContentTv;
    Button mCollectHospitalBtn;
    ActionBar actionBar;

    int hospitalId;
    boolean isCollected = false;
    Hospital hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        initController();
        initView();
    }

    private void initController() {
        mProvinceTv = findViewById(R.id.tv_province_hospital_detail);
        mAddressTv = findViewById(R.id.tv_address_hospital_detail);
        mTypeTv = findViewById(R.id.tv_type_hospital_detail);
        mRankTv = findViewById(R.id.tv_rank_hospital_detail);
        mPhoneTv = findViewById(R.id.tv_phone_hospital_detail);
        mExpertLayout = findViewById(R.id.layout_expert_hospital_detail);
        mExpertLayout.setOnClickListener(this);
        mContentTv = findViewById(R.id.tv_content_hospital_detail);
        mCollectHospitalBtn = findViewById(R.id.btn_collect_hospital_detail);
        mCollectHospitalBtn.setOnClickListener(this);
    }

    private void initView() {
        hospitalId = getIntent().getIntExtra("hospitalId", 0);
        getHospitalInfo(hospitalId);
    }

    private void getHospitalInfo(int hospitalId) {
        HttpRequest.getHospital(hospitalId, new Callback<ResponseBody<Hospital>>() {
            @Override
            public void onResponse(Call<ResponseBody<Hospital>> call, Response<ResponseBody<Hospital>> response) {
                ResponseBody<Hospital> body = response.body();
                if (body != null) {
                    if (body.getStatus() == 200) {
                        hospital = body.getData();
                        if (body.getData() != null) {
                            actionBar.setTitle(hospital.getName());
                            mProvinceTv.setText(hospital.getProvince());
                            mAddressTv.setText(hospital.getAddress());
                            mTypeTv.setText(hospital.getType());
                            mRankTv.setText(hospital.getRank());
                            mPhoneTv.setText(hospital.getPhone());
                            mContentTv.setText(hospital.getContent());
                            initCollection(UserManager.getUser().getId(), hospitalId);
                        }
                    }else {
                        ToastUtil.show(HospitalDetailActivity.this, body.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<Hospital>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_expert_hospital_detail:
                if (hospital != null) {
                    Intent intent = new Intent(this, ExpertActivity.class);
                    intent.putExtra("hospitalId", hospital.getId());
                    intent.putExtra("hospitalName", hospital.getName());
                    startActivity(intent);
                }
                break;
            case R.id.btn_collect_hospital_detail:
                if (!isCollected) {
                    collectHospital(UserManager.getUser().getId(), hospitalId);
                }else {
                    unCollectHospital(UserManager.getUser().getId(), hospitalId);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void unCollectHospital(int userId, int hospitalId) {
        HttpRequest.unCollectHospital(new CollectHospital(userId, hospitalId), new Callback<ResponseBody<Integer>>() {
            @Override
            public void onResponse(Call<ResponseBody<Integer>> call, Response<ResponseBody<Integer>> response) {
                ResponseBody<Integer> body = response.body();
                if (body != null) {
                    if (body.getStatus() == 200) {
                        ToastUtil.show(HospitalDetailActivity.this, body.getMessage());
                        initCollection(userId, hospitalId);
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<Integer>> call, Throwable t) {

            }
        });
    }

//    private void unCollectHospital() {
//        Map<String, String> condition = new HashMap<>();
//        condition.put("HospitalId", hospital.getObjectId());
//        condition.put("UserId", User.getInstance().getId());
//        BmobUtil.equalQuery(condition, new FindListener<CollectHospital>() {
//            @Override
//            public void done(List<CollectHospital> list, BmobException e) {
//                if (e == null) {
//                    if (list != null && list.size() > 0) {
//                        for (CollectHospital collectHospital : list) {
//                            CollectHospital c = new CollectHospital();
//                            c.delete(collectHospital.getObjectId(), new UpdateListener() {
//                                @Override
//                                public void done(BmobException e) {
//                                    if (e == null) {
//                                        ToastUtil.show(HospitalDetailActivity.this, "success");
//                                        initCollection(hospital);
//                                    }else {
//                                        ToastUtil.show(HospitalDetailActivity.this, e.getMessage());
//                                    }
//                                }
//                            });
//                        }
//                    }
//                }
//            }
//        });
//
//    }

    private void collectHospital(int userId, int hospitalId) {
        HttpRequest.collectHospital(new CollectHospital(userId, hospitalId), new Callback<ResponseBody<Integer>>() {
            @Override
            public void onResponse(Call<ResponseBody<Integer>> call, Response<ResponseBody<Integer>> response) {
                ResponseBody<Integer> body = response.body();
                if (body != null) {
                    if (body.getStatus() == 200) {
                        ToastUtil.show(HospitalDetailActivity.this, body.getMessage());
                        initCollection(userId, hospitalId);
                    }else {
                        ToastUtil.show(HospitalDetailActivity.this, body.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<Integer>> call, Throwable t) {

            }
        });
    }

//    private void collectHospital() {
//        CollectHospital collectHospital = new CollectHospital(User.getInstance().getId(), hospital.getObjectId());
//        BmobUtil.insert(collectHospital, new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    ToastUtil.show(HospitalDetailActivity.this, "收藏成功");
//                    initCollection(hospital);
//                }else {
//                    ToastUtil.show(HospitalDetailActivity.this, e.getMessage());
//                }
//            }
//        });
//    }

    private void initCollection(int userId, int hospitalId) {
        HttpRequest.queryCollection(new CollectHospital(userId, hospitalId), new Callback<ResponseBody<List<CollectHospital>>>() {
            @Override
            public void onResponse(Call<ResponseBody<List<CollectHospital>>> call, Response<ResponseBody<List<CollectHospital>>> response) {
                ResponseBody<List<CollectHospital>> body = response.body();
                if (body != null) {
                    if (body.getStatus() == 200) {
                        if (body.getData() != null && !body.getData().isEmpty()) {
                            isCollected = true;
                            mCollectHospitalBtn.setText("已收藏");
                        }else {
                            isCollected = false;
                            mCollectHospitalBtn.setText("收藏");
                        }
                    }else {
                        ToastUtil.show(HospitalDetailActivity.this, body.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<List<CollectHospital>>> call, Throwable t) {

            }
        });
    }

//    private void initCollection(Hospital hospital) {
//        Map<String, String> condition = new HashMap<>();
//        condition.put("UserId", User.getInstance().getId());
//        condition.put("HospitalId", hospital.getObjectId());
//        BmobUtil.equalQuery(condition, new FindListener<CollectHospital>() {
//            @Override
//            public void done(List<CollectHospital> list, BmobException e) {
//                if (e == null) {
//                    if (list != null && list.size() > 0) {
//                        isCollected = true;
//                        mCollectHospitalBtn.setText("已收藏");
//                    }else {
//                        isCollected = false;
//                        mCollectHospitalBtn.setText("收藏");
//                    }
//                }else {
//                    ToastUtil.show(HospitalDetailActivity.this, e.getMessage());
//                }
//            }
//        });
//    }

}