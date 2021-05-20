package com.richer.myhospital.home.homepage.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.richer.myhospital.R;
import com.richer.myhospital.home.homepage.adapter.HospitalAdapter;
import com.richer.myhospital.home.model.Hospital;
import com.richer.myhospital.net.HttpRequest;
import com.richer.myhospital.net.ResponseBody;
import com.richer.myhospital.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, HospitalAdapter.IHospitalClick {

    List<Hospital> hospitalList = new ArrayList<>();
    EditText mSearchEt;
    ImageView mSearchImg;
    ImageView mBackImg;
    RecyclerView mHospitalRecyclerView;

    LinearLayoutManager manager;
    HospitalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initController();
        initRecyclerView();

    }

    private void initController() {
        mSearchEt = findViewById(R.id.et_search_hospital);
        mSearchImg = findViewById(R.id.img_search_hospital);
        mSearchImg.setOnClickListener(this);
        mBackImg = findViewById(R.id.img_back_search_hospital);
        mBackImg.setOnClickListener(this);
        mHospitalRecyclerView = findViewById(R.id.recyclerview_hospital_list_search);
    }
    private void initRecyclerView() {
        manager = new LinearLayoutManager(this);
        mHospitalRecyclerView.setLayoutManager(manager);
        adapter = new HospitalAdapter(hospitalList, this);
        mHospitalRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_search_hospital:
                String condition = mSearchEt.getText().toString();
                if (condition.isEmpty()) {
                    ToastUtil.show(this, "搜索内容不能为空!");
                }else {
                    search(condition);
                }
                break;
            case R.id.img_back_search_hospital:
                onBackPressed();
                break;
            default:break;
        }
    }

    private void search(String condition) {
        HttpRequest.searchHospital(condition, new Callback<ResponseBody<List<Hospital>>>() {
            @Override
            public void onResponse(Call<ResponseBody<List<Hospital>>> call, Response<ResponseBody<List<Hospital>>> response) {
                ResponseBody<List<Hospital>> body = response.body();
                if (body != null) {
                    if (body.getStatus() == 200 && body.getData() != null) {
                        hospitalList = body.getData();
                        initRecyclerView();
                    }else {
                        ToastUtil.show(SearchActivity.this, body.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<List<Hospital>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClickListener(Hospital hospital) {
        Intent intent = new Intent(this, HospitalDetailActivity.class);
        intent.putExtra("hospitalId", hospital.getId());
        startActivity(intent);
    }
}