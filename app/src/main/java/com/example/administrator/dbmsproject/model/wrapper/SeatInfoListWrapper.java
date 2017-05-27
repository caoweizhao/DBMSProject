package com.example.administrator.dbmsproject.model.wrapper;

import com.example.administrator.dbmsproject.model.trainbean.SeatInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-5-22.
 */

public class SeatInfoListWrapper implements Serializable {
    private List<SeatInfo> mSeatInfos;

    public SeatInfoListWrapper(List<SeatInfo> seatInfos) {
        mSeatInfos = seatInfos;
    }


    public List<SeatInfo> getSeatInfoList() {
        return mSeatInfos;
    }
}
