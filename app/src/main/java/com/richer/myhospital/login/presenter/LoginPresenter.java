package com.richer.myhospital.login.presenter;

import com.richer.myhospital.login.model.User;
import com.richer.myhospital.login.view.ILoginView;
import com.richer.myhospital.net.HttpRequest;
import com.richer.myhospital.net.ResponseBody;
import com.richer.myhospital.util.BmobUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 2021/04/14 Richer
 * LoginPresenter
 */
public class LoginPresenter{

    ILoginView view;

    public LoginPresenter(ILoginView view) {
        this.view = view;
    }

    public void login(String accountName, String password) {
        if (accountName.isEmpty() || password.isEmpty()) {
            view.showMessage("账号密码不能为空!");
        }else {
            HttpRequest.login(accountName, new Callback<ResponseBody<User>>() {
                @Override
                public void onResponse(Call<ResponseBody<User>> call, Response<ResponseBody<User>> response) {
                    ResponseBody<User> body = response.body();
                    if (response.body() != null) {
                        if (body.getStatus() == 200) {
                            String pwd = body.getData().getPassword();
                            if (password.equals(pwd)){
                                view.success(body.getData());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody<User>> call, Throwable t) {

                }
            });
        }
    }

    /**
     * 用户登录网络请求
     * @param accountName 用户名
     * @param password 密码
     */
//    public void login(String accountName, String password) {
//        if (accountName.isEmpty() || password.isEmpty()) {
//            view.showMessage("账号密码不能为空!");
//        }else {
//            //查询数据库accountName对应的密码
//            Map<String, String> condition = new HashMap<>();
//            condition.put("accountName", accountName);
//            BmobUtil.equalQuery(condition, new FindListener<User>() {
//                @Override
//                public void done(List<User> list, BmobException e) {
//                    if (e == null) {
//                        if (list != null && list.size() > 0) {
//                            String p = list.get(0).getPassword();
//                            //密码比对
//                            if (p.equals(password)) {
//                                view.success(list.get(0).getObjectId());
//                            }else {
//                                view.showMessage("密码错误!");
//                            }
//                        }else {
//                            view.showMessage("账号不存在!");
//                        }
//                    }else {
//                        view.showMessage(e.getMessage());
//                    }
//                }
//            });
//        }
//    }
}
