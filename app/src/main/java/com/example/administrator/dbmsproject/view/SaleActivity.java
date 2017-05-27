package com.example.administrator.dbmsproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.model.Contract;
import com.example.administrator.dbmsproject.model.NetworkHelper;
import com.example.administrator.dbmsproject.model.RequestError;
import com.example.administrator.dbmsproject.model.TicketSoldoutError;
import com.example.administrator.dbmsproject.model.trainbean.Ticket;
import com.example.administrator.dbmsproject.model.trainbean.TrainSchedule;
import com.example.administrator.dbmsproject.util.DateFormatUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SaleActivity extends AppCompatActivity {

    public static final String TICKET_DATA = "TICKET_DATA";
    public static final String TICKET_SOURCE = "SOURCE";
    public static final String TICKET_DESTINATION = "DESTINATION";

    TrainSchedule mTrainSchedule;
    TextInputLayout mTextInputLayout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_sale);
        Intent intent = getIntent();
        if (intent != null) {
            mTrainSchedule = (TrainSchedule) intent.getSerializableExtra(TrainScheduleActivity.TRAIN_SCHEDULE);
        }
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_in_sale_layout);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(e -> onBackPressed());
        }
        mTextInputLayout = (TextInputLayout) findViewById(R.id.id_card_text_input_layout_in_sale);
        mTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 18) {
                    mTextInputLayout.setError("身份证号码不能超过18位！");
                } else {
                    mTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mTextInputLayout.getEditText().setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X', 'x'};
                return chars;
            }

            @Override
            public int getInputType() {
                return 3;
            }
        });
        ((TextView) findViewById(R.id.identity_sale_layout)).setText(mTrainSchedule.getScheduleNum());
        Date reachDate = mTrainSchedule.getReachDate();
        String reachDateText = DateFormatUtil.detailDateToString(reachDate);
        Date startDate = mTrainSchedule.getStartDate();
        String startDateText = DateFormatUtil.detailDateToString(startDate);
        ((TextView) findViewById(R.id.arrive_time_sale_layout)).setText(reachDateText);
        ((TextView) findViewById(R.id.start_time_sale_layout)).setText(startDateText);
        ((TextView) findViewById(R.id.identity_sale_layout)).setText(mTrainSchedule.getScheduleNum());
        ((TextView) findViewById(R.id.source_sale_layout)).setText(mTrainSchedule.getSource());
        ((TextView) findViewById(R.id.destination_sale_layout)).setText(mTrainSchedule.getDestination());
        ((TextView) findViewById(R.id.rest_sale_layout)).setText(String.valueOf(mTrainSchedule.getRestTicket()));
    }

    public void onSaleTicketClicked(View view) {
        // TODO: 2017-5-26  sale
        String scheduleNum = ((TextView) findViewById(R.id.identity_sale_layout)).getText().toString();
        String price = ((TextInputLayout) findViewById(R.id.price_input_layout_in_sale)).getEditText().getText().toString();
        String ticketMan = ((TextInputLayout) findViewById(R.id.ticket_man_input_layout_in_sale)).getEditText().getText().toString();
        String identityCard = ((TextInputLayout) findViewById(R.id.id_card_text_input_layout_in_sale)).getEditText().getText().toString();

        if (TextUtils.isEmpty(scheduleNum) || TextUtils.isEmpty(price) ||
                TextUtils.isEmpty(ticketMan) || TextUtils.isEmpty(identityCard)) {
            showErrorMessage("请检查订单信息完整性！");
            return;
        }
        SweetAlertDialog dialog;
        dialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        dialog.setTitleText("确认购买？")
                .setCancelable(true);
        dialog.setConfirmText("是");
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.dismissWithAnimation();
            saleTicket(scheduleNum, ticketMan, identityCard, price);
        });
        dialog.setCancelText("否");
        dialog.setCancelClickListener(SweetAlertDialog::cancel);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(d -> {
        });
        dialog.show();
    }

    private SweetAlertDialog errorDialog;

    public void showErrorMessage(String msg) {
        errorDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        errorDialog.setTitleText(msg)
                .setCancelable(true);
        errorDialog.setCanceledOnTouchOutside(true);
        errorDialog.show();
    }

    private void saleTicket(String scheduleNum, String ticketMan, String identityCard, String price) {
        // TODO: 2017-5-26
        showLoading();
        new NetworkHelper().saleTicket(identityCard, ticketMan, price, scheduleNum, Contract.SALE_TICKET);
    }

    SweetAlertDialog progressDialog;

    private void showLoading() {
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.setTitleText("正在购买，请稍后...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissLoading() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismissWithAnimation();
            }
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaleDone(Ticket ticket) {
        dismissLoading();
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        sweetAlertDialog.setTitleText("购买成功！");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setConfirmText("OK");
        sweetAlertDialog.setConfirmClickListener(e -> {
            Intent intent = new Intent(SaleActivity.this, ShowTicketActivity.class);
            intent.putExtra(TICKET_DATA, ticket);
            intent.putExtra(TICKET_SOURCE, mTrainSchedule.getSource());
            intent.putExtra(TICKET_DESTINATION, mTrainSchedule.getDestination());
            startActivity(intent);
        });
        sweetAlertDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestError(RequestError error) {
        dismissLoading();
        showErrorMessage("请求失败！");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTicketSoldError(TicketSoldoutError error) {
        dismissLoading();
        showErrorMessage("票已卖完啦！");
    }
}
