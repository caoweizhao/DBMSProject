package com.example.administrator.dbmsproject.model.locationbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-3-25.
 */

public class CityBean implements Serializable, IAddress {

    /**
     * mId : 205
     * name : 广州
     */
    @SerializedName("id")
    private int mId;
    private String name;
    private int provinceId;

    @Override
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

}
