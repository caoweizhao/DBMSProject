package com.example.administrator.dbmsproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.model.AbstractSeatItem;
import com.example.administrator.dbmsproject.model.Contract;
import com.example.administrator.dbmsproject.model.NetworkHelper;
import com.example.administrator.dbmsproject.model.SeatHeader;
import com.example.administrator.dbmsproject.model.trainbean.SeatInfo;
import com.example.administrator.dbmsproject.model.trainbean.TrainSchedule;
import com.example.administrator.dbmsproject.model.wrapper.SeatInfoListWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SeatInfoActivity extends AppCompatActivity {

    TrainSchedule mTrainSchedule;

    Toolbar mToolbar;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_info);
        EventBus.getDefault().register(this);
        initView();
        Intent intent = getIntent();
        if (intent != null) {
            mTrainSchedule = (TrainSchedule) intent.getSerializableExtra(TrainScheduleActivity.TRAIN_SCHEDULE);
            if (mTrainSchedule != null) {
                new NetworkHelper().getSeatInfos(mTrainSchedule.getScheduleNum(), Contract.SEAT_POOLS);
                showLoading();
                //testFakeData();
            } else {
                showErrorMessage("发生未知错误");
            }
        }
    }

    private void testFakeData() {
        List<SeatInfo> seatInfos = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 31; j++) {
                SeatInfo seatInfo = new SeatInfo();
                seatInfo.setRoomNum(i);
                seatInfo.setSeatNum(j);
                seatInfo.setSale(j < 10 ? 1 : 0);
                seatInfos.add(seatInfo);
            }
        }
        EventBus.getDefault().post(new SeatInfoListWrapper(seatInfos));
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_seat_info);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(e -> onBackPressed());
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.seat_info_recycler_view);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    SweetAlertDialog sad;

    private void showLoading() {
        sad = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sad.setTitleText("正在加载，请稍后...");
        sad.setCancelable(false);
        sad.show();
    }

    private void dismissLoading() {
        if (sad != null) {
            if (sad.isShowing()) {
                sad.dismissWithAnimation();
            }
        }
    }


    private SweetAlertDialog errorDialog;

    /**
     * 显示错误，关闭显示后退出
     *
     * @param msg
     */
    public void showErrorMessage(String msg) {
        errorDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        errorDialog.setTitleText(msg)
                .setCancelable(true);
        errorDialog.setCanceledOnTouchOutside(true);
        errorDialog.setOnCancelListener(dialog -> {
            finish();
        });
        errorDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSeatInfosDateReady(SeatInfoListWrapper seatInfoListWrapper) {
        dismissLoading();
        if (seatInfoListWrapper.getSeatInfoList().size() == 0) {
            showErrorMessage("找不到座位信息！");
        } else {
            showSeatInfo(seatInfoListWrapper);
        }
    }

    SeatAdapter mAdapter;

    private void showSeatInfo(SeatInfoListWrapper seatInfoListWrapper) {
        List<SeatInfo> seatInfos = seatInfoListWrapper.getSeatInfoList();
        List<AbstractSeatItem> abstractSeatItemList = generateAbstractSeatItem(seatInfos);
        mAdapter = new SeatAdapter(abstractSeatItemList);
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = mAdapter.getItemViewType(position);
                if (type == 0) {
                    return 1;
                } else {
                    return 5;
                }
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private List<AbstractSeatItem> generateAbstractSeatItem(List<SeatInfo> seatInfos) {
        List<AbstractSeatItem> abstractSeatItems = new ArrayList<>();
        if (seatInfos.size() > 0) {
            Set<Integer> set = new HashSet<>();
            for (int i = 0; i < seatInfos.size(); i++) {
                SeatInfo seatInfo = seatInfos.get(i);
                int carriageNum = seatInfo.getRoomNum();
                if (!set.contains(carriageNum)) {
                    SeatHeader seatHeader = new SeatHeader(carriageNum);
                    abstractSeatItems.add(seatHeader);
                    set.add(carriageNum);
                }
                abstractSeatItems.add(seatInfo);
            }
        }
        return abstractSeatItems;
    }

    class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {

        private List<AbstractSeatItem> mAbstractSeatItems;

        public SeatAdapter(List<AbstractSeatItem> abstractSeatItems) {
            mAbstractSeatItems = abstractSeatItems;
        }

        @Override
        public int getItemViewType(int position) {
            return mAbstractSeatItems.get(position) instanceof SeatInfo ? 0 : 1;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == 0) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_item_layout, parent, false);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carriage_header, parent, false);
            }
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            AbstractSeatItem abstractSeatItem = mAbstractSeatItems.get(position);
            holder.mTextView.setText(abstractSeatItem.getText());
            if (abstractSeatItem instanceof SeatInfo) {
                SeatInfo seatInfo = (SeatInfo) abstractSeatItem;
                if (seatInfo.isSale() == 0) {
                    //未售
                    holder.mTextView.setBackground(getDrawable(R.drawable.seat_not_sale_bg));
                } else {
                    //已售
                    holder.mTextView.setBackground(getDrawable(R.drawable.seat_sale_bg));
                }
            }
        }

        @Override
        public int getItemCount() {
            return mAbstractSeatItems.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.seat_text_view);
            }
        }
    }
}
