package com.richer.myhospital.login.model;

import java.io.Serializable;

/**
 * 2021/04/14 Richer
 * User 对应数据库User表https://www.bmob.cn/app/browser/306222/1665041
 */
public class User implements Serializable {

    /**
     * accountName 用户名（唯一）
     * password 密码
     */
    private int id;
    private String name;
    private String password;
    private String sex;
    private String phone;
    private String birthday;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(int id, String name, String password, String sex, String phone, String birthday) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
