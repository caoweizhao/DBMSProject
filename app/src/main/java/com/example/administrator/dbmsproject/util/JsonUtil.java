package com.example.administrator.dbmsproject.util;


import com.example.administrator.dbmsproject.model.locationbean.CityBean;
import com.example.administrator.dbmsproject.model.locationbean.CountryBean;
import com.example.administrator.dbmsproject.model.locationbean.ProvinceBean;
import com.example.administrator.dbmsproject.model.trainbean.SeatInfo;
import com.example.administrator.dbmsproject.model.trainbean.Ticket;
import com.example.administrator.dbmsproject.model.trainbean.TrainSchedule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Administrator on 2017-3-25.
 */

public class JsonUtil {
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();

    public static List<ProvinceBean> parseProvinceJsonArray(String jsonArray) {
        return gson.fromJson(jsonArray, new TypeToken<List<ProvinceBean>>() {
        }.getType());
    }

    public static List<CityBean> parsCityJsonArray(String jsonArray) {
        return gson.fromJson(jsonArray, new TypeToken<List<CityBean>>() {
        }.getType());
    }

    public static List<CountryBean> parsCountryJsonArray(String jsonArray) {
        return gson.fromJson(jsonArray, new TypeToken<List<CountryBean>>() {
        }.getType());
    }

    public static List<TrainSchedule> parseTrainSchedule(String jsonArray) {
        return gson.fromJson(jsonArray, new TypeToken<List<TrainSchedule>>() {
        }.getType());
    }

    public static List<Ticket> parseTickets(String jsonArray) {
        return gson.fromJson(jsonArray, new TypeToken<List<Ticket>>() {
        }.getType());
    }

    public static Ticket parseTicket(String jsonArray) {
        return gson.fromJson(jsonArray, Ticket.class);
    }

    public static List<SeatInfo> parseSeatInfo(String jsonArray) {
        return gson.fromJson(jsonArray, new TypeToken<List<SeatInfo>>() {
        }.getType());
    }

    public static String get(String json) {
        String s = json.substring(json.indexOf("[") + 1, json.lastIndexOf("_links") - 1);
        System.out.println(s);
        s = s.substring(0, s.lastIndexOf(","));
        return s;
    }

}
