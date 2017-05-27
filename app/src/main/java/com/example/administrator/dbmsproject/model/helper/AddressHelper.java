package com.example.administrator.dbmsproject.model.helper;

import android.util.Log;

import com.example.administrator.dbmsproject.AddressLoadError;
import com.example.administrator.dbmsproject.model.Contract;
import com.example.administrator.dbmsproject.model.locationbean.CityBean;
import com.example.administrator.dbmsproject.model.locationbean.CountryBean;
import com.example.administrator.dbmsproject.model.locationbean.ProvinceBean;
import com.example.administrator.dbmsproject.model.wrapper.CityListWrapper;
import com.example.administrator.dbmsproject.model.wrapper.CountryListWrapper;
import com.example.administrator.dbmsproject.model.wrapper.ProvinceListWrapper;
import com.example.administrator.dbmsproject.util.JsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017-3-27.
 * 处理地址类的数据获取
 */

public class AddressHelper {

    private void getProvinceListFromNetWork(String url) {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AddressHelper", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResult = response.body().string();
                    //解析获取province数据
                    final List<ProvinceBean> provinceBeenList = JsonUtil.parseProvinceJsonArray(jsonResult);
                    EventBus.getDefault().post(new ProvinceListWrapper(provinceBeenList));
                } else {
                    EventBus.getDefault().post(new AddressLoadError());
                }

            }
        };
        OkHttpHelper.getInstance().execute(getAddressRequest(url), callback);
    }

    private void getCityListFromNetwork(final int provinceId) {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonResult = response.body().string();
                //解析获取city数据
                final List<CityBean> cityBeanList = JsonUtil.parsCityJsonArray(jsonResult);
                for (CityBean cityBean : cityBeanList
                        ) {
                    cityBean.setProvinceId(provinceId);
                }
                EventBus.getDefault().post(new CityListWrapper(cityBeanList));
            }
        };
        String url = Contract.ADDRESS_PROVINCES + "/" + provinceId;
        OkHttpHelper.getInstance().execute(getAddressRequest(url), callback);
    }

    private void getCountryListFromNetwork(final int provinceId, final int cityId) {
        OkHttpClient client = new OkHttpClient();
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonResult = response.body().string();
                //解析获取country数据
                final List<CountryBean> countryBeanList = JsonUtil.parsCountryJsonArray(jsonResult);
                for (CountryBean countryBean : countryBeanList
                        ) {
                    countryBean.setCityId(cityId);
                }
                EventBus.getDefault().post(new CountryListWrapper(countryBeanList));
            }
        };
        String url = Contract.ADDRESS_PROVINCES + "/" + provinceId + "/" + cityId;
        OkHttpHelper.getInstance().execute(getAddressRequest(url), callback);
    }

    private Request getAddressRequest(String url) {
        return new Request.Builder() //
                .url(url)   //
                .build();
    }

    public void getProvinceList(String url) {
        getProvinceListFromNetWork(url);
    }

    public void getCityList(int provinceId) {
        getCityListFromNetwork(provinceId);
    }

    public void getCountryList(int provinceId, int cityId) {
        getCountryListFromNetwork(provinceId, cityId);
    }
}
