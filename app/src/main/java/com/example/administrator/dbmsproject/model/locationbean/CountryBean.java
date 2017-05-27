package com.example.administrator.dbmsproject.model.locationbean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-3-26.
 */

public class CountryBean implements Serializable,IAddress{

    /**
     * mId : 937
     * name : 苏州
     * weather_id : CN101190401
     */
    private int mId;
    private String name;
    private int cityId;

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

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
