package com.richer.myhospital.home.homepage.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.richer.myhospital.R;
import com.richer.myhospital.home.homepage.adapter.ExpertAdapter;
import com.richer.myhospital.home.model.Expert;
import com.richer.myhospital.home.model.Reserve;
import com.richer.myhospital.net.HttpRequest;
import com.richer.myhospital.net.ResponseBody;
import com.richer.myhospital.util.ToastUtil;
import com.richer.myhospital.util.UserManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertActivity extends AppCompatActivity implements ExpertAdapter.IExpertClick {

    RecyclerView mExpertRecyclerView;

    int hospitalId;
    String hospitalName;
    List<Expert> expertList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert);

        Intent intent = getIntent();
        hospitalId = intent.getIntExtra("hospitalId", 0);
        hospitalName = intent.getStringExtra("hospitalName");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(hospitalName);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mExpertRecyclerView = findViewById(R.id.recyclerview_expert_list);

        getExpertList(hospitalId);

    }

    private void getExpertList(int hospitalId) {
        HttpRequest.getAllExpert(hospitalId, new Callback<ResponseBody<List<Expert>>>() {
            @Override
            public void onResponse(Call<ResponseBody<List<Expert>>> call, Response<ResponseBody<List<Expert>>> response) {
                ResponseBody<List<Expert>> body = response.body();
                if (body != null) {
                    if (body.getStatus() == 200) {
                        if (body.getData() != null && !body.getData().isEmpty()) {
                            expertList = body.getData();
                            LinearLayoutManager manager = new LinearLayoutManager(ExpertActivity.this);
                            mExpertRecyclerView.setLayoutManager(manager);
                            ExpertAdapter adapter = new ExpertAdapter(expertList, ExpertActivity.this);
                            mExpertRecyclerView.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<List<Expert>>> call, Throwable t) {

            }
        });
    }

//    private void getExpertList(String hospitalId) {
//        Map<String, String> condition = new HashMap<>();
//        condition.put("HospitalId", hospitalId);
//        BmobUtil.equalQuery(condition, new FindListener<Expert>() {
//            @Override
//            public void done(List<Expert> list, BmobException e) {
//                if (e == null) {
//                    if (list != null && list.size() > 0) {
//                        expertList = list;
//                        LinearLayoutManager manager = new LinearLayoutManager(ExpertActivity.this);
//                        mExpertRecyclerView.setLayoutManager(manager);
//                        ExpertAdapter adapter = new ExpertAdapter(expertList, ExpertActivity.this);
//                        mExpertRecyclerView.setAdapter(adapter);
//                    }
//                }
//            }
//        });
//    }

    @Override
    public void onClickListener(Expert expert) {
        showDialog(expert);
    }

    private void showDialog(Expert expert) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExpertActivity.this);
        LayoutInflater inflater = LayoutInflater.from(ExpertActivity.this);
        View viewDialog = inflater.inflate(R.layout.dialog_reserve, null);
        EditText mPhoneEt = viewDialog.findViewById(R.id.et_phone_reserve);
        DatePicker datePicker = viewDialog.findViewById(R.id.dp_reserve);
        TimePicker timePicker = viewDialog.findViewById(R.id.tp_reserve);
        if (!UserManager.getUser().getPhone().isEmpty()) {
            mPhoneEt.setText(UserManager.getUser().getPhone());
        }
        builder.setView(viewDialog);
        builder.setPositiveButton("预约", (dialog, which) -> {
            if (mPhoneEt.getText().toString().isEmpty()) {
                ToastUtil.show(ExpertActivity.this, "请留下您的手机号方便后续联系哦~");
            }else {
                String phone = mPhoneEt.getText().toString();
                String date = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
                String time = timePicker.getHour()+":"+timePicker.getMinute();
                reserve(UserManager.getUser().getId(), expert.getId(), date, time, phone);
            }
        });
        builder.setNegativeButton("取消",null);
        builder.create().show();
    }

    private void reserve(int userId, int expertId, String date, String time, String phone) {
        HttpRequest.reserve(new Reserve(userId, expertId, date, time, phone), new Callback<ResponseBody<Integer>>() {
            @Override
            public void onResponse(Call<ResponseBody<Integer>> call, Response<ResponseBody<Integer>> response) {
                ResponseBody<Integer> body = response.body();
                if (body != null) {
                    ToastUtil.show(ExpertActivity.this, body.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<Integer>> call, Throwable t) {

            }
        });
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

}