package com.example.administrator.dbmsproject.model.trainbean;// default package

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Ticket entity. @author MyEclipse Persistence Tools
 */
public class Ticket implements java.io.Serializable {

    /**
     * 订单号
     */
    @SerializedName(value = "id")
    private String ticketId;
    /**
     * 列车时刻表
     */
    private TrainSchedule trainSchedule;
    /**
     * 车厢号
     */
    @SerializedName(value = "carriageNo")
    private String carriageNo;
    /**
     * 座位号
     */
    @SerializedName(value = "seatNo")
    private String seatNo;
    /**
     * 付款人姓名
     */
    @SerializedName(value = "ticketman")
    private String ticketMan;
    /**
     * 身份证号码
     */
    @SerializedName(value = "identityCard")
    private String identityCard;
    /**
     * 下单时间
     */
    @SerializedName(value = "orderTime")
    private Date orderTime;
    /**
     * 付款时间
     */
    @SerializedName(value = "refundsTime")
    private Date refundsTime;
    /**
     * 价格
     */
    @SerializedName(value = "price")
    private Double price;
    /**
     * 1卖出  0退票
     */
    @SerializedName(value = "status")
    private Short status;

    /**
     * 出发时间
     */
    @SerializedName(value = "startTime")
    private Date startTime;
    /**
     * 到达时间
     */
    @SerializedName(value = "reachTime")
    private Date reachTime;

    public TrainSchedule getTrainSchedule() {
        return trainSchedule;
    }

    public void setTrainSchedule(TrainSchedule trainSchedule) {
        this.trainSchedule = trainSchedule;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCarriageNo() {
        return carriageNo;
    }

    public void setCarriageNo(String carriageNo) {
        this.carriageNo = carriageNo;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getTicketMan() {
        return ticketMan;
    }

    public void setTicketMan(String ticketMan) {
        this.ticketMan = ticketMan;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getRefundsTime() {
        return refundsTime;
    }

    public void setRefundsTime(Date refundsTime) {
        this.refundsTime = refundsTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getReachTime() {
        return reachTime;
    }

    public void setReachTime(Date reachTime) {
        this.reachTime = reachTime;
    }
}