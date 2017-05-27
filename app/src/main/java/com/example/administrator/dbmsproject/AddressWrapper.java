package com.example.administrator.dbmsproject;


import com.example.administrator.dbmsproject.model.locationbean.IAddress;

/**
 * Created by Administrator on 2017-3-28.
 */

public class AddressWrapper {
    private IAddress iAddress;

    public AddressWrapper(IAddress iAddress) {
        this.iAddress = iAddress;
    }

    public IAddress getIAddress() {
        return iAddress;
    }
}
