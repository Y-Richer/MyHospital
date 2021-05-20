package com.richer.myhospital.register.presenter;

import com.richer.myhospital.login.model.User;
import com.richer.myhospital.net.HttpRequest;
import com.richer.myhospital.net.ResponseBody;
import com.richer.myhospital.register.view.IRegisterView;
import com.richer.myhospital.util.BmobUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 2021/04/14 Richer
 * RegisterPresenter
 */
public class RegisterPresenter {

    IRegisterView view;

    public RegisterPresenter(IRegisterView view) {
        this.view = view;
    }

    public void register(String accountName, String password) {
        if (accountName.isEmpty() || password.isEmpty()) {
            view.showMessage("账户名或密码不能为空!");
            return;
        }
        HttpRequest.register(new User(accountName, password), new Callback<ResponseBody<Integer>>() {
            @Override
            public void onResponse(Call<ResponseBody<Integer>> call, Response<ResponseBody<Integer>> response) {
                ResponseBody<Integer> body = response.body();
                if (response.body() != null) {
                    view.showMessage(body.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<Integer>> call, Throwable t) {

            }
        });
    }

    /**
     * 用户注册网络请求
     * @param accountName 用户名
     * @param password 密码
     * @param phoneNumber 手机号码
     * @param verificationCode 验证码
     */
//    public void register(String accountName, String password, String phoneNumber, String verificationCode) {
//        if (accountName.isEmpty() || password.isEmpty()) {
//            view.showMessage("账户名或密码不能为空!");
//            return;
//        }
//        //验证用户名是否唯一
//        Map<String, String> condition = new HashMap<>();
//        condition.put("accountName", accountName);
//        BmobUtil.equalQuery(condition, new FindListener<User>() {
//            @Override
//            public void done(List<User> list, BmobException e) {
//                if (e == null) {
//                    if (list == null || list.size() < 1) {
//                        //用户名不存在
//                        //校验验证码
//                        BmobSMS.verifySmsCode(phoneNumber, verificationCode, new UpdateListener() {
//                            @Override
//                            public void done(BmobException e) {
//                                if (e == null) {
//                                    //验证码验证成功
//                                    User user = new User(accountName, password);
//                                    //插入一行数据
//                                    BmobUtil.insert(user, new SaveListener<String>() {
//                                        @Override
//                                        public void done(String s, BmobException e) {
//                                            if (e == null) {
//                                                view.success();
//                                            }else {
//                                                view.showMessage(e.getMessage());
//                                            }
//                                        }
//                                    });
//                                }else {
//                                    //验证码验证失败
//                                    view.showMessage("验证码验证失败" + e.getMessage());
//                                }
//                            }
//                        });
//                    }else {
//                        view.showMessage("用户名已存在");
//                    }
//                }else {
//                    view.showMessage(e.getMessage());
//                }
//            }
//        });
//    }

}
