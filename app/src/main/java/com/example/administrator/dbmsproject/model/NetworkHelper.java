package com.example.administrator.dbmsproject.model;

import android.util.Log;

import com.example.administrator.dbmsproject.model.helper.OkHttpHelper;
import com.example.administrator.dbmsproject.model.trainbean.SeatInfo;
import com.example.administrator.dbmsproject.model.trainbean.Ticket;
import com.example.administrator.dbmsproject.model.trainbean.TrainSchedule;
import com.example.administrator.dbmsproject.model.wrapper.SeatInfoListWrapper;
import com.example.administrator.dbmsproject.model.wrapper.TicketListWrapper;
import com.example.administrator.dbmsproject.model.wrapper.TrainInfoListWrapper;
import com.example.administrator.dbmsproject.util.DateFormatUtil;
import com.example.administrator.dbmsproject.util.HrefUtil;
import com.example.administrator.dbmsproject.util.JsonUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017-5-22.
 */

public class NetworkHelper implements IModel {

    /**
     * 根据起点终点跟日期搜索车次
     *
     * @param source
     * @param destination
     * @param date
     */
    @Override
    public void searchTrainInfoBySourceDestinationDate(String source, String destination, Date date, String url) {
        // TODO: 2017-5-22
        String startText = DateFormatUtil.detailDateToString(date);
        Request.Builder builder = new Request.Builder();
        String endText = startText.replace("00:00", "23:59");
        Map<String, String> params = new HashMap<>();
        params.put("origin", source);
        params.put("start", startText);
        params.put("destination", destination);
        params.put("end", endText);
        params.put("sort", "start,desc");
        String newUrl = HrefUtil.putParams(url, params);
        Log.d("NetworkHelper", "url" + newUrl);

        builder.url(newUrl)
                .get();
        Request request = builder.build();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new RequestError());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(response.body().string()).getAsJsonObject();
                    JsonObject jsonObject2 = jsonObject.getAsJsonObject("_embedded");
                    JsonArray jsonArray = jsonObject2.getAsJsonArray("trainSchedules");
                    Log.d("NetworkHelper", "jsonArray" + jsonArray.toString());
                    List<TrainSchedule> trainSchedules = JsonUtil.parseTrainSchedule(jsonArray.toString());
                    EventBus.getDefault().post(new TrainInfoListWrapper(trainSchedules));
                }
            }
        };
        OkHttpHelper.getInstance().execute(request, callback);
    }

    /**
     * 根据起点终点跟日期搜索车次（按时间升序排序）
     *
     * @param source
     * @param destination
     * @param date
     */
    public void searchTrainInfoBySourceDestinationDateASC(String source, String destination, Date date, String url) {
        // TODO: 2017-5-22
        String startText = DateFormatUtil.detailDateToString(date);
        Request.Builder builder = new Request.Builder();
        String endText = startText.replace("00:00", "23:59");
        Map<String, String> params = new HashMap<>();
        params.put("origin", source);
        params.put("start", startText);
        params.put("destination", destination);
        params.put("end", endText);
        params.put("sort", "start,asc");
        String newUrl = HrefUtil.putParams(url, params);
        Log.d("NetworkHelper", "url" + newUrl);

        builder.url(newUrl)
                .get();
        Request request = builder.build();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new RequestError());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonObject = jsonParser.parse(response.body().string()).getAsJsonObject();
                    JsonObject jsonObject2 = jsonObject.getAsJsonObject("_embedded");
                    JsonArray jsonArray = jsonObject2.getAsJsonArray("trainSchedules");
                    Log.d("NetworkHelper", "jsonArray" + jsonArray.toString());
                    List<TrainSchedule> trainSchedules = JsonUtil.parseTrainSchedule(jsonArray.toString());
                    EventBus.getDefault().post(new TrainInfoListWrapper(trainSchedules));
                }
            }
        };
        OkHttpHelper.getInstance().execute(request, callback);
    }

    /**
     * 根据搜索车次号搜索车次
     *
     * @param scheduleNum
     */
    @Override
    public void searchTrainInfoByScheduleNum(String scheduleNum, String url) {
        // TODO: 2017-5-23
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("no", scheduleNum);
        hashMap.put("sort", "start,desc");
        String newUrl = HrefUtil.putParams(url, hashMap);
        Request.Builder builder = new Request.Builder();
        builder.url(newUrl)
                .get();
        Request request = builder.build();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JsonParser jsonParser = new JsonParser();
                    String jsonData = response.body().string();
                    Log.d("NetworkHelper", "json" + jsonData);
                    JsonObject jsonObject = jsonParser.parse(jsonData).getAsJsonObject();
                    JsonObject jsonObject2 = jsonObject.getAsJsonObject("_embedded");
                    JsonArray jsonArray = jsonObject2.getAsJsonArray("trainSchedules");
                    Log.d("NetworkHelper", "jsonArray" + jsonArray.toString());
                    List<TrainSchedule> trainSchedules = JsonUtil.parseTrainSchedule(jsonArray.toString());
                    EventBus.getDefault().post(new TrainInfoListWrapper(trainSchedules));
                }
            }
        };
        OkHttpHelper.getInstance().execute(request, callback);
    }

    /**
     * 根据搜索车次号搜索车次(按时间升序排序)
     *
     * @param scheduleNum
     */
    public void searchTrainInfoByScheduleNumASC(String scheduleNum, String url) {
        // TODO: 2017-5-23
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("no", scheduleNum);
        hashMap.put("sort", "start,asc");
        String newUrl = HrefUtil.putParams(url, hashMap);
        Log.d("NetworkHelper", "newUrl" + newUrl);
        Request.Builder builder = new Request.Builder();
        builder.url(newUrl)
                .get();
        Request request = builder.build();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JsonParser jsonParser = new JsonParser();
                    String jsonData = response.body().string();
                    Log.d("NetworkHelper", "json" + jsonData);
                    JsonObject jsonObject = jsonParser.parse(jsonData).getAsJsonObject();
                    JsonObject jsonObject2 = jsonObject.getAsJsonObject("_embedded");
                    JsonArray jsonArray = jsonObject2.getAsJsonArray("trainSchedules");
                    Log.d("NetworkHelper", "jsonArray" + jsonArray.toString());
                    List<TrainSchedule> trainSchedules = JsonUtil.parseTrainSchedule(jsonArray.toString());
                    EventBus.getDefault().post(new TrainInfoListWrapper(trainSchedules));
                }
            }
        };
        OkHttpHelper.getInstance().execute(request, callback);
    }

    /**
     * 根据车次号获取座位信息
     *
     * @param scheduleNum
     */
    @Override
    public void getSeatInfos(String scheduleNum, String url) {
        // TODO: 2017-5-25 EventBus post List<SeatInfo> seatInfos
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("scheduleNum", scheduleNum);

        String newUrl = HrefUtil.contactSeatUrl(url, scheduleNum);
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().
                url(newUrl).
                post(requestBody)
                .build();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject embedded = jsonParser.parse(response.body().string()).getAsJsonObject().getAsJsonObject("_embedded");
                    JsonArray jsonArray = embedded.getAsJsonArray("seatPools");
                    List<SeatInfo> seatInfos = JsonUtil.parseSeatInfo(jsonArray.toString());
                    EventBus.getDefault().post(new SeatInfoListWrapper(seatInfos));
                }
            }
        };
        OkHttpHelper.getInstance().execute(request, callback);
    }

    /**
     * 根据身份证号搜索车票
     *
     * @param id
     */
    @Override
    public void searchTicketByIDCard(String id, String url) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("idCard", id);
        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    List<Ticket> tickets = JsonUtil.parseTickets(response.body().string());
                    EventBus.getDefault().post(new TicketListWrapper(tickets));
                }
            }
        };
        OkHttpHelper.getInstance().execute(request, callback);
    }

    /**
     * 根据订单号退票
     *
     * @param ticketNum
     */
    @Override
    public void refundsByTicketNum(String ticketNum, String url) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("ticketNum", ticketNum);
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().
                url(url).
                post(requestBody)
                .build();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        };
        OkHttpHelper.getInstance().execute(request, callback);
    }

    /**
     * 修改车票信息
     */
    public void modifyTrainSchedule(String scheduleNum, String url) {
        // TODO: 2017-5-23
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("no", scheduleNum);
        String newUrl = HrefUtil.putParams(url, hashMap);
        Request.Builder builder = new Request.Builder();
        builder.url(newUrl)
                .get();
        Request request = builder.build();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(response.body().string()).getAsJsonObject();
                JsonObject jsonObject2 = jsonObject.getAsJsonObject("_embedded");
                JsonArray jsonArray = jsonObject2.getAsJsonArray("trainSchedules");
                Log.d("NetworkHelper", "jsonArray" + jsonArray.toString());
                List<TrainSchedule> trainSchedules = JsonUtil.parseTrainSchedule(jsonArray.toString());
                EventBus.getDefault().post(new TrainInfoListWrapper(trainSchedules));
            }
        };
        OkHttpHelper.getInstance().execute(request, callback);
    }

    /**
     * 购买车票
     */
    public void saleTicket(String idNum, String ticketMan, String price, String scheduleNum, String url) {
        // TODO: 2017-5-23
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("identityCard", idNum);
        hashMap.put("ticketMan", ticketMan);
        hashMap.put("price", price);

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        formBodyBuilder.add("identityCard", idNum);
        formBodyBuilder.add("ticketMan", ticketMan);
        formBodyBuilder.add("price", price);
        String newUrl = HrefUtil.contactSaleUrl(url, scheduleNum);
        Request.Builder builder = new Request.Builder();
        builder.url(newUrl)
                .post(formBodyBuilder.build());
        Request request = builder.build();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new RequestError());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    JsonParser jsonParser = new JsonParser();
                    String res = response.body().string();
                    Ticket ticket = JsonUtil.parseTicket(res);
                    EventBus.getDefault().post(ticket);
                } else {
                    if (response.code() == 409) {
                        EventBus.getDefault().post(new TicketSoldoutError());
                    }
                }

            }
        };
        OkHttpHelper.getInstance().execute(request, callback);
    }


    /**
     * 测试数据
     */
    public void getFakeData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TrainSchedule> list = new ArrayList<>();
                List<SeatInfo> seatInfos = new ArrayList<>();
                SeatInfo seatInfo = new SeatInfo();

                for (int i = 0; i < 30; i++) {
                    seatInfos.add(seatInfo);
                    TrainSchedule trainSchedule = new TrainSchedule();
                    trainSchedule.setStartDate(new Date());
                    trainSchedule.setSource("广州" + i);
                    trainSchedule.setDestination("佛冈");
                    trainSchedule.setScheduleNum("G401" + i);
                    list.add(trainSchedule);
                }
                try {
                    Thread.sleep(500);
                    EventBus.getDefault().post(new TrainInfoListWrapper(list));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getFakeTicket() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
                List<Ticket> tickets = new ArrayList<>();
                Ticket ticket = new Ticket();
                ticket.setTicketId("1324234235");
                ticket.setCarriageNo("05");
                ticket.setIdentityCard("1234902394923");
                ticket.setOrderTime(new Timestamp(new Date().getTime()));
                TrainSchedule trainSchedule = new TrainSchedule();
                trainSchedule.setSource("广州");
                trainSchedule.setDestination("佛冈");
                ticket.setTrainSchedule(trainSchedule);
                tickets.add(ticket);
                tickets.add(ticket);
                tickets.add(ticket);
                tickets.add(ticket);
                tickets.add(ticket);
                EventBus.getDefault().post(new TicketListWrapper(tickets));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
