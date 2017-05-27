package com.example.administrator.dbmsproject.model.trainbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017-5-22.
 */

public class TrainSchedule implements Serializable {

    /**
     * 车次号（G411）
     */
    @SerializedName(value = "id")
    private String scheduleNum;
    /**
     * 列车信息
     */
    private Train train;
    /**
     * 起始站
     */
    @SerializedName(value = "origin")
    private String source;
    /**
     * 终点站
     */
    @SerializedName(value = "destination")
    private String destination;
    /**
     * 发车时间 2017-05-22 17:35
     */
    @SerializedName(value = "startTime")
    private Date startDate;
    /**
     * 到达时间 2017-05-22 17:35
     */
    @SerializedName(value = "reachTime")
    private Date reachDate;

    /**
     * 车票余量
     */
    @SerializedName(value = "restTicket")
    private int restTicket;

    /**
     * 车票情况
     */
    private Set<Ticket> tickets = new HashSet<>();

    public String getScheduleNum() {
        return scheduleNum;
    }

    public void setScheduleNum(String scheduleNum) {
        this.scheduleNum = scheduleNum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getReachDate() {
        return reachDate;
    }

    public void setReachDate(Date reachDate) {
        this.reachDate = reachDate;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public int getRestTicket() {
        return restTicket;
    }

    public void setRestTicket(int restTicket) {
        this.restTicket = restTicket;
    }
}
