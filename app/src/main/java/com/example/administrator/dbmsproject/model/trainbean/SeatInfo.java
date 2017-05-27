package com.example.administrator.dbmsproject.model.trainbean;

import com.example.administrator.dbmsproject.model.AbstractSeatItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-5-22.
 */

public class SeatInfo extends AbstractSeatItem implements Serializable {

    public String getText() {
        return getRoomNum() + "-" + getSeatNum();
    }

    @SerializedName(value = "id")
    private int id;
    /**
     * 车厢号
     */
    @SerializedName(value = "carriageNo")
    private int roomNum;
    /**
     * 座位号
     */
    @SerializedName(value = "seatNo")
    private int seatNum;

    /**
     * 座位状态（是否已售 0否 1是）
     */
    private int isSale;

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public int isSale() {
        return isSale;
    }

    public void setSale(int sale) {
        isSale = sale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
