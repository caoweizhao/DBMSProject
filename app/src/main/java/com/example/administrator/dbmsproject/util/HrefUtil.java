package com.example.administrator.dbmsproject.util;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017-5-25.
 */

public class HrefUtil {

    public static String putParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        if (params.keySet().size() > 0) {
            sb.append("?");
            Iterator<String> iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                sb.append(key)
                        .append("=")
                        .append(params.get(key))
                        .append("&")
                ;
            }
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        return sb.toString();
    }

    public static String contactSeatUrl(String url, String scheduleNum) {
        return url + "/" + scheduleNum + "/seatPools";
    }

    public static String contactSaleUrl(String url, String scheduleNum) {
        return url + "/" + scheduleNum + "/buy";
    }
}
