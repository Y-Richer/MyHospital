package com.richer.myhospital.login.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.richer.myhospital.R;
import com.richer.myhospital.home.Home;
import com.richer.myhospital.login.model.User;
import com.richer.myhospital.login.presenter.LoginPresenter;
import com.richer.myhospital.net.NetWork;
import com.richer.myhospital.register.view.RegisterActivity;
import com.richer.myhospital.util.ToastUtil;


import static com.richer.myhospital.Constant.NET_SHOW;
import static com.richer.myhospital.Constant.NET_TEST;

/**
 * 2021/04/14 Richer
 * LoginActivity
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {

    Button mEnterBtn;
    TextView mRegisterBtn;
    EditText mAccountNameEt;
    EditText mPasswordEt;
    TextView mTitleTv;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        NetWork.init(NET_TEST);
        initView();
        initPresenter();

    }

    private void initView() {
        mEnterBtn = findViewById(R.id.btn_enter_login);
        mRegisterBtn = findViewById(R.id.btn_register_login);
        mAccountNameEt = findViewById(R.id.et_accountname_login);
        mPasswordEt = findViewById(R.id.et_password_login);
        mTitleTv = findViewById(R.id.tv_title_login);
        mTitleTv.setOnClickListener(this);
        mEnterBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    private void initPresenter() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_enter_login:
                doLogin();
                break;
            case R.id.btn_register_login:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_title_login:
                NetWork.init(NET_SHOW);
                ToastUtil.show(this, "net change");
                break;
            default:
                break;
        }
    }

    private void doLogin() {
        String accountName = mAccountNameEt.getText().toString();
        String password = mPasswordEt.getText().toString();
        if (loginPresenter != null) {
            loginPresenter.login(accountName, password);
        }
    }

    @Override
    public void showMessage(String message) {
        ToastUtil.show(this, message);
    }

    @Override
    public void success(User user) {
        ToastUtil.show(this, "登录成功");
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}