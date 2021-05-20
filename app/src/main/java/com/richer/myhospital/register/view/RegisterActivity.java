package com.richer.myhospital.register.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.richer.myhospital.R;
import com.richer.myhospital.login.view.LoginActivity;
import com.richer.myhospital.register.presenter.RegisterPresenter;
import com.richer.myhospital.util.ToastUtil;

/**
 * 2021/04/14 Richer
 * RegisterActivity
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, IRegisterView {

    Button mDoneBtn;
    EditText mAccountNameEt;
    EditText mPasswordEt;
    EditText mRepeatPasswordEt;
    TextView mLoginBtn;

    RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initPresenter();
    }

    private void initView() {
        mDoneBtn = findViewById(R.id.btn_done_register);
        mAccountNameEt = findViewById(R.id.et_accountname_register);
        mPasswordEt = findViewById(R.id.et_password_register);
        mRepeatPasswordEt = findViewById(R.id.et_password_repeat_register);
        mLoginBtn = findViewById(R.id.btn_login_register);
        mLoginBtn.setOnClickListener(this);
        mDoneBtn.setOnClickListener(this);
    }

    private void initPresenter() {
        registerPresenter = new RegisterPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done_register:
                doRegister();
                break;
            case R.id.btn_login_register:
                startActivity(new Intent(this, LoginActivity.class));
            default:
                break;
        }
    }

    private void doRegister() {
        String accountName = mAccountNameEt.getText().toString();
        String password = mPasswordEt.getText().toString();
        String repeatPassword = mRepeatPasswordEt.getText().toString();
        if (password.equals(repeatPassword)) {
            if (registerPresenter != null) {
                registerPresenter.register(accountName, password);
            }
        }else {
            ToastUtil.show(this, "两次输入密码不一致");
        }
    }

    @Override
    public void success() {
        ToastUtil.show(this, "注册成功!");
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void showMessage(String message) {
        ToastUtil.show(this, message);
    }
}