package com.example.administrator.dbmsproject.model.wrapper;


import com.example.administrator.dbmsproject.model.locationbean.CountryBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-3-27.
 */

public class CountryListWrapper implements Serializable{
    private List<CountryBean> mCountryBeanList;

    public CountryListWrapper(List<CountryBean> list) {
        mCountryBeanList = list;
    }

    public List<CountryBean> getCountryBeanList() {
        return mCountryBeanList;
    }

    public void setCountryBeanList(List<CountryBean> countryBeanList) {
        mCountryBeanList = countryBeanList;
    }
}
