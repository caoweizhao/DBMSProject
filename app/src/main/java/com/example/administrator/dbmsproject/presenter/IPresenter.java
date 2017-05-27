package com.example.administrator.dbmsproject.presenter;

import com.example.administrator.dbmsproject.view.IView;

/**
 * Created by Administrator on 2017-5-22.
 */

public interface IPresenter {

    void onSearchButtonClicked(String source, String destination, String dateText);

    void onSearchSubmit(String input);

    void onAttach(IView view);

    void onDetach();

    void onCheckTicketButtonClicked(String id);
}
