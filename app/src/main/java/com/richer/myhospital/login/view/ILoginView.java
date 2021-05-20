package com.richer.myhospital.login.view;

import com.richer.myhospital.login.model.User;

/**
 * 2021/04/14 Richer
 * ILoginView
 */

public interface ILoginView {

    //弹出提示信息
    void showMessage(String message);
    //登录成功操作
    void success(User user);

}
