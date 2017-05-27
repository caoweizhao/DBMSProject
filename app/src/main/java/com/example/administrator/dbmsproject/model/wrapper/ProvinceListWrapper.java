package com.example.administrator.dbmsproject.model.wrapper;


import com.example.administrator.dbmsproject.model.locationbean.ProvinceBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-3-27.
 */

public class ProvinceListWrapper implements Serializable {
    private List<ProvinceBean> mProvinceBeanList;

    public ProvinceListWrapper(List<ProvinceBean> list) {
        mProvinceBeanList = list;
    }

    public List<ProvinceBean> getProvinceBeanList() {
        return mProvinceBeanList;
    }

    public void setProvinceBeanList(List<ProvinceBean> provinceBeanList) {
        mProvinceBeanList = provinceBeanList;
    }
}
