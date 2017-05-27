package com.example.administrator.dbmsproject.presenter;

import android.text.TextUtils;

import com.example.administrator.dbmsproject.model.IModel;
import com.example.administrator.dbmsproject.model.NetworkHelper;
import com.example.administrator.dbmsproject.model.RequestError;
import com.example.administrator.dbmsproject.model.wrapper.TicketListWrapper;
import com.example.administrator.dbmsproject.view.IView;
import com.example.administrator.dbmsproject.view.MainActivity;
import com.example.administrator.dbmsproject.view.TrainScheduleActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017-5-22.
 */

public class MainPresenter implements IPresenter {

    private IView mView;
    private IModel mModel;

    @Override
    public void onSearchButtonClicked(String source, String destination, String dateText) {

        if (TextUtils.isEmpty(source)) {
            mView.showErrorMessage("请选择起点！");
            return;
        }
        if (TextUtils.isEmpty(destination)) {
            mView.showErrorMessage("请选择终点！");
            return;
        }
        if (source.equals(destination)) {
            mView.showErrorMessage("起点终点不能一致！");
            return;
        }
        if (TextUtils.isEmpty(dateText)) {
            mView.showErrorMessage("请选择日期！");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateText);
            TrainScheduleActivity.startTrainScheduleActivity((MainActivity) mView,
                    TrainScheduleActivity.SEARCH_BY_SOURCE_AND_DESTINATION_AND_DATE, source, destination, dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSearchSubmit(String identity) {
        if (TextUtils.isEmpty(identity)) {
            mView.showErrorMessage("请重新输入车次号！");
        } else {
            TrainScheduleActivity.startTrainScheduleActivity((MainActivity) mView, TrainScheduleActivity.SEARCH_BY_SCHEDULE_NUM, identity);
        }

    }

    @Override
    public void onAttach(IView view) {
        mView = view;
        mModel = new NetworkHelper();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
        mView = null;
        mModel = null;
    }

    @Override
    public void onCheckTicketButtonClicked(String id) {
        mView.showLoading();
        // TODO: 2017-5-25 url
        mModel.searchTicketByIDCard(id, "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTicketDataReady(TicketListWrapper ticketListWrapper) {
        mView.dismissLoading();
        if (ticketListWrapper.getTicketList().size() == 0) {
            mView.showErrorMessage("没有找到该用户的购票信息！");
        } else {
            mView.setTicketFragment(ticketListWrapper);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestError(RequestError error) {
        mView.dismissLoading();
        mView.showErrorMessage("请求失败！");
    }
}
