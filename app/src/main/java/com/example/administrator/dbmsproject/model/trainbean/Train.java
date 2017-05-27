package com.example.administrator.dbmsproject.model.trainbean;// default package

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.Set;

/**
 * Train entity. @author MyEclipse Persistence Tools
 */
public class Train implements java.io.Serializable {

    /**
     * 列车号
     */
    @SerializedName(value = "id")
    private String indentity;
    /**
     * 车厢数量
     */
    @SerializedName(value = "carriageNumber")
    private int carriageNumber;
    /**
     * 座位信息
     */
    private Set<SeatInfo> seats = new HashSet<>();
    /**
     * 时刻表
     */
    private Set<TrainSchedule> trainSchedules = new HashSet<>();

    public String getIndentity() {
        return indentity;
    }

    public void setIndentity(String indentity) {
        this.indentity = indentity;
    }

    public int getCarriageNumber() {
        return carriageNumber;
    }

    public void setCarriageNumber(int carriageNumber) {
        this.carriageNumber = carriageNumber;
    }

    public Set<SeatInfo> getSeats() {
        return seats;
    }

    public void setSeats(Set<SeatInfo> seats) {
        this.seats = seats;
    }

    public Set<TrainSchedule> getTrainSchedules() {
        return trainSchedules;
    }

    public void setTrainSchedules(Set<TrainSchedule> trainSchedules) {
        this.trainSchedules = trainSchedules;
    }
}