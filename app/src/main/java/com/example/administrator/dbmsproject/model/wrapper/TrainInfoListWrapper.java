package com.example.administrator.dbmsproject.model.wrapper;

import com.example.administrator.dbmsproject.model.trainbean.TrainSchedule;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-5-22.
 */

public class TrainInfoListWrapper implements Serializable {
    private List<TrainSchedule> mTrainSchedules;

    public TrainInfoListWrapper(List<TrainSchedule> infos) {
        mTrainSchedules = infos;
    }


    public List<TrainSchedule> getTrainSchedules() {
        return mTrainSchedules;
    }
}
