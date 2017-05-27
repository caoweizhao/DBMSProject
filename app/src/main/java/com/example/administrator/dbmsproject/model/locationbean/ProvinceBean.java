package com.example.administrator.dbmsproject.model.locationbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-3-25.
 */

public class ProvinceBean implements Serializable, IAddress {

    /**
     * id : 1
     * name : 北京
     */

    @SerializedName(value = "id")
    private int id;

    private String name;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
