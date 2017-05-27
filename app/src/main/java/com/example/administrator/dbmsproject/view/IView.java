package com.example.administrator.dbmsproject.view;

import com.example.administrator.dbmsproject.model.wrapper.TicketListWrapper;

/**
 * Created by Administrator on 2017-5-22.
 */

public interface IView {

    void showErrorMessage(String msg);
    void showLoading();
    void dismissLoading();
    void setTicketFragment(TicketListWrapper ticketListWrapper);
}
