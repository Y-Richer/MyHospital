package com.richer.myhospital.home.model;

import android.os.Parcelable;

import com.richer.myhospital.net.ResponseBody;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 2021/04/18 Richer
 */
public class Hospital implements Serializable {
    /**
     * {
     * "province": "福建",
     * "city": "福州",
     * "type": "综合医院 ",
     * "name": "福州市台江区医院",
     * "rank": "", //医院等级
     * "address": "福州市中选路33号 ",
     * "phone": "0591-83836969 ",
     * "key_point": "",
     * "expert": "",
     * "content": "" //医院介绍
     * }
     */
    int id;
    String name;
    String province;
    String city;
    String type;
    String rank;
    String address;
    String phone;
    String content;

    public Hospital() {
    }

    public Hospital(String name, String province, String city, String type, String rank, String address, String phone, String content) {
        this.name = name;
        this.province = province;
        this.city = city;
        this.type = type;
        this.rank = rank;
        this.address = address;
        this.phone = phone;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
