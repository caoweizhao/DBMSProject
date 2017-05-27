package com.example.administrator.dbmsproject.model;

/**
 * Created by Administrator on 2017-5-26.
 */

public class SeatHeader extends AbstractSeatItem {

    private int carriage;

    public SeatHeader(int carriage) {
        this.carriage = carriage;
    }

    @Override
    public String getText() {
        return "车厢" + carriage;
    }
}
