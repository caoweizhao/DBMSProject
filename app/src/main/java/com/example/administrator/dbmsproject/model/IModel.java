package com.example.administrator.dbmsproject.model;

import java.util.Date;

/**
 * Created by Administrator on 2017-5-22.
 */

public interface IModel {

    /**
     * 根据起止点+日期获取列车时刻表
     *
     * @param source
     * @param destination
     * @param date
     */
    void searchTrainInfoBySourceDestinationDate(String source, String destination, Date date, String url);

    /**
     * 根据车次号搜索列车时刻表
     *
     * @param identity
     */
    void searchTrainInfoByScheduleNum(String identity, String url);

    /**
     * 根据列车时刻表的车次号获取座位信息（余量/座位）
     *
     * @param scheduleNum
     */
    void getSeatInfos(String scheduleNum, String url);

    /**
     * 根据身份证号搜索订单号
     *
     * @param id
     */
    void searchTicketByIDCard(String id, String url);

    /**
     * 根据订单号进行退票
     *
     * @param ticketNum
     */
    void refundsByTicketNum(String ticketNum, String url);

}
