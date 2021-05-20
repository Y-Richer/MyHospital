package com.richer.myhospital.home.account_center_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.richer.myhospital.R;
import com.richer.myhospital.home.homepage.view.ExpertActivity;
import com.richer.myhospital.login.model.User;
import com.richer.myhospital.login.view.LoginActivity;
import com.richer.myhospital.net.HttpRequest;
import com.richer.myhospital.net.ResponseBody;
import com.richer.myhospital.util.ToastUtil;
import com.richer.myhospital.util.UserManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountCenterFragment extends Fragment implements View.OnClickListener {

    private static AccountCenterFragment accountCenterFragment;
    private AccountCenterFragment() {
    }
    public static AccountCenterFragment getInstance() {
        if (accountCenterFragment == null) {
            accountCenterFragment = new AccountCenterFragment();
        }
        return accountCenterFragment;
    }

    Button mExitBtn;
    Button mChangePasswordBtn;
    TextView mNameTv;
    TextView mEditInfoTv;
    TextView mSexTv;
    TextView mPhoneTv;
    TextView mBirthDayTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_center, container, false);
        initController(view);
        initView();
        return view;
    }

    private void initController(View view) {
        mExitBtn = view.findViewById(R.id.btn_exit_account_center);
        mExitBtn.setOnClickListener(this);
        mNameTv = view.findViewById(R.id.tv_name_account_center);
        mEditInfoTv = view.findViewById(R.id.tv_edit_info_account_center);
        mEditInfoTv.setOnClickListener(this);
        mSexTv = view.findViewById(R.id.tv_sex_account_center);
        mPhoneTv = view.findViewById(R.id.tv_phone_account_center);
        mBirthDayTv = view.findViewById(R.id.tv_birthday_account_center);
        mChangePasswordBtn = view.findViewById(R.id.btn_change_password_account_center);
        mChangePasswordBtn.setOnClickListener(this);
    }

    private void initView() {
        User user = UserManager.getUser();
        mNameTv.setText(user.getName());
        mSexTv.setText(user.getSex());
        mPhoneTv.setText(user.getPhone());
        mBirthDayTv.setText(user.getBirthday());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_account_center:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_edit_info_account_center:
                showEditDialog(UserManager.getUser());
                break;
            case R.id.btn_change_password_account_center:
                showChangePasswordDialog(UserManager.getUser());
        }
    }

    private void showChangePasswordDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View viewDialog = inflater.inflate(R.layout.dialog_change_password, null);
        EditText mOldPasswordEt = viewDialog.findViewById(R.id.et_old_password);
        EditText mNewPasswordEt = viewDialog.findViewById(R.id.et_new_password);
        EditText mRepeatNewPasswordEt = viewDialog.findViewById(R.id.et_repeat_new_password);
        builder.setView(viewDialog);
        builder.setPositiveButton("确定", (dialog, which) -> {
            if (!mOldPasswordEt.getText().toString().equals(user.getPassword())) {
                ToastUtil.show(getContext(), "原密码错误!");
            }else {
                if (!mNewPasswordEt.getText().toString().equals(mRepeatNewPasswordEt.getText().toString())) {
                    ToastUtil.show(getContext(), "两次输入密码不一致!");
                }else {
                    user.setPassword(mNewPasswordEt.getText().toString());
                    UserManager.setUser(user);
                    changePassword(user);
                }
            }

        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    private void changePassword(User user) {
        HttpRequest.updatePassword(user, new Callback<ResponseBody<Integer>>() {
            @Override
            public void onResponse(Call<ResponseBody<Integer>> call, Response<ResponseBody<Integer>> response) {
                ResponseBody<Integer> body = response.body();
                if (body != null) {
                    ToastUtil.show(getContext(), body.getMessage());
                    if (body.getData() != 0) {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<Integer>> call, Throwable t) {

            }
        });
    }

    private void showEditDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View viewDialog = inflater.inflate(R.layout.dialog_edit_info, null);
        EditText mNameEt = viewDialog.findViewById(R.id.et_name_edit);
        RadioGroup mSexRg = viewDialog.findViewById(R.id.rg_sex_edit);
        RadioButton mMaleRb = viewDialog.findViewById(R.id.rb_male_edit);
        RadioButton mFemaleRb = viewDialog.findViewById(R.id.rb_female_edit);
        EditText mPhoneEt = viewDialog.findViewById(R.id.et_phone_edit);
        DatePicker mBirthdayDp = viewDialog.findViewById(R.id.dp_birthday_edit);
        //init
        mNameEt.setText(user.getName());
        if (user.getSex().equals("女")) {
            mSexRg.check(R.id.rb_female_edit);
        }else {
            mSexRg.check(R.id.rb_male_edit);
        }
        mPhoneEt.setText(user.getPhone());
        if (user.getBirthday() != null && !user.getBirthday().isEmpty()) {
            String[] date = user.getBirthday().split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            mBirthdayDp.init(year, month, day, null);
        }

        builder.setView(viewDialog);
        builder.setPositiveButton("确定", (dialog, which) -> {
            user.setName(mNameEt.getText().toString());
            if (mMaleRb.isChecked()) {
                user.setSex("男");
            }else if (mFemaleRb.isChecked()){
                user.setSex("女");
            }
            user.setPhone(mPhoneEt.getText().toString());
            String date = mBirthdayDp.getYear()+"-"+(mBirthdayDp.getMonth()+1)+"-"+mBirthdayDp.getDayOfMonth();
            user.setBirthday(date);
            UserManager.setUser(user);
            initView();
            editInfo(user);
        });
        builder.setNegativeButton("取消",null);
        builder.create().show();
    }

    private void editInfo(User user) {
        HttpRequest.updateUser(user, new Callback<ResponseBody<Integer>>() {
            @Override
            public void onResponse(Call<ResponseBody<Integer>> call, Response<ResponseBody<Integer>> response) {
                ResponseBody<Integer> body = response.body();
                if (body != null) {
                    ToastUtil.show(getContext(), body.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<Integer>> call, Throwable t) {

            }
        });
    }
}
