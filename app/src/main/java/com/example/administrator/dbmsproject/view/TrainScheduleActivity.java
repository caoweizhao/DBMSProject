package com.example.administrator.dbmsproject.view;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.adapter.TrainInfoAdapter;
import com.example.administrator.dbmsproject.model.Contract;
import com.example.administrator.dbmsproject.model.NetworkHelper;
import com.example.administrator.dbmsproject.model.RequestError;
import com.example.administrator.dbmsproject.model.trainbean.TrainSchedule;
import com.example.administrator.dbmsproject.model.wrapper.TrainInfoListWrapper;
import com.example.administrator.dbmsproject.util.DateFormatUtil;
import com.example.administrator.dbmsproject.util.HrefUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TrainScheduleActivity extends AppCompatActivity implements TrainInfoAdapter.onItemClickedListener {

    public static final String LAUNCH_TYPE_KEY = "LAUNCH_TYPE";
    public static final String LAUNCH_PARAMS = "LAUNCH_PARAMS";
    public static final int SEARCH_BY_SCHEDULE_NUM = 0;
    public static final int SEARCH_BY_SOURCE_AND_DESTINATION_AND_DATE = 1;


    public static final String TRAIN_SCHEDULE = "TRAIN_SCHEDULE";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Toolbar mToolBar;

    private int mType;
    private String mSource;
    private String mDestination;
    private String mDateText;
    private String mScheduleNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_schedule);
        initView();
        initToolBar();
        Intent intent = getIntent();
        mType = intent.getIntExtra(LAUNCH_TYPE_KEY, 0);
        String[] params = (String[]) intent.getSerializableExtra(LAUNCH_PARAMS);
        if (mType == SEARCH_BY_SCHEDULE_NUM) {
            mScheduleNum = params[0];
        } else if (mType == SEARCH_BY_SOURCE_AND_DESTINATION_AND_DATE) {
            mSource = params[0];
            mDestination = params[1];
            mDateText = params[2];
        }
        doSearch(upToDown);
    }

    /**
     * 进行搜索
     */
    public void doSearch(boolean upToDown) {
        showLoading();
        if (mType == SEARCH_BY_SCHEDULE_NUM) {
            if (upToDown) {
                //降序
                new NetworkHelper().searchTrainInfoByScheduleNum(mScheduleNum, Contract.SEARCH_TRAIN_BY_SCHEDULE_NUM);
            } else {
                new NetworkHelper().searchTrainInfoByScheduleNumASC(mScheduleNum, Contract.SEARCH_TRAIN_BY_SCHEDULE_NUM);

            }
        } else if (mType == SEARCH_BY_SOURCE_AND_DESTINATION_AND_DATE) {
            if (upToDown) {
                new NetworkHelper().searchTrainInfoBySourceDestinationDate(mSource, mDestination, DateFormatUtil.stringToNormalDate(mDateText),
                        Contract.SEARCH_TRAIN_BY_SOURCE_DESTINATION_DATE);
            } else {
                new NetworkHelper().searchTrainInfoBySourceDestinationDateASC(mSource, mDestination, DateFormatUtil.stringToNormalDate(mDateText),
                        Contract.SEARCH_TRAIN_BY_SOURCE_DESTINATION_DATE);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    TrainInfoAdapter trainInfoAdapter;
    List<TrainSchedule> trainScheduleList;

    private void initRecyclerView(@NonNull List<TrainSchedule> trainScheduleList) {
        trainInfoAdapter = new TrainInfoAdapter(trainScheduleList);
        mRecyclerView.setAdapter(trainInfoAdapter);
        trainInfoAdapter.setOnItemClickedListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mToolBar.setNavigationOnClickListener(e -> onBackPressed());
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar_detail_page);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_train_info);
        mSwipeRefreshLayout.setOnRefreshListener(() -> doSearch(upToDown));
    }

    private boolean upToDown = true;

    public void toggleTimeOrder(View view) {
        // TODO: 2017-5-23 完成按时间排序
        ImageView imageView = (ImageView) view.findViewById(R.id.img_time_order_detail);
        if (upToDown) {
            //时间升序
            upToDown = false;
            imageView.setImageResource(R.drawable.ic_arrow_upward_black_24dp);
        } else {
            //时间降序
            upToDown = true;
            imageView.setImageResource(R.drawable.ic_arrow_downward_black_24dp);
        }
        doSearch(upToDown);
    }

    View bottomDialogView;
    EditText source;
    EditText destination;
    EditText identity;
    TextView startDate;
    TextView reachDate;
    TextView rest;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetBehavior mBottomSheetBehavior;

    @Override
    public void onItemClicked(View view, int position, TrainSchedule trainSchedule) {
        if (bottomDialogView == null) {
            bottomDialogView = LayoutInflater.from(this).inflate(R.layout.bottom_dialog_detail, null, false);
            rest = (TextView) bottomDialogView.findViewById(R.id.rest_bottom_sheet);
            source = (EditText) bottomDialogView.findViewById(R.id.source_bottom_sheet);
            startDate = (TextView) bottomDialogView.findViewById(R.id.start_time_bottom_sheet);
            destination = (EditText) bottomDialogView.findViewById(R.id.destination_bottom_sheet);
            identity = (EditText) bottomDialogView.findViewById(R.id.identity_bottom_sheet);
            startDate.setOnClickListener(this::onStartDatePick);
            reachDate = (TextView) bottomDialogView.findViewById(R.id.arrive_time_bottom_sheet);
            reachDate.setOnClickListener(this::onReachDatePick);
            // TODO: 2017-5-25  
            bottomDialogView.findViewById(R.id.detail_of_seat_bottom_sheet).setOnClickListener(e ->
                    onSeatDetailClicked(trainSchedule)
            );
            bottomDialogView.findViewById(R.id.sale_in_bottom_sheet).setOnClickListener(e ->
                    onSaleClicked(trainSchedule)
            );
            bottomDialogView.findViewById(R.id.modify_in_bottom_sheet).setOnClickListener(e ->
                    onModifyClicked(trainSchedule, position));

        }
        source.setText(trainSchedule.getSource());
        destination.setText(trainSchedule.getDestination());
        identity.setText(trainSchedule.getScheduleNum());
        String startDateText = DateFormatUtil.detailDateToString(trainSchedule.getStartDate());
        String reachDateText = DateFormatUtil.detailDateToString(trainSchedule.getReachDate());
        startDate.setText(startDateText);
        reachDate.setText(reachDateText);
        rest.setText(String.valueOf(trainSchedule.getRestTicket()));
        if (trainSchedule.getRestTicket() == 0) {
            bottomDialogView.findViewById(R.id.sale_in_bottom_sheet).setEnabled(false);
        } else if (trainSchedule.getRestTicket() > 0) {
            bottomDialogView.findViewById(R.id.sale_in_bottom_sheet).setEnabled(true);
        }

        if (bottomSheetDialog == null) {
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(bottomDialogView);
            mBottomSheetBehavior = BottomSheetBehavior.from((View) bottomDialogView.getParent());
            bottomSheetDialog.setOnDismissListener(dialog -> {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            });
        }
        bottomSheetDialog.show();
    }

    /**
     * 到达时间选择
     *
     * @param view
     */
    private void onReachDatePick(View view) {
        StringBuilder sb = new StringBuilder();
        getTime(sb, reachDate);
    }

    /**
     * 开车时间选择
     *
     * @param view
     */
    private void onStartDatePick(View view) {
        StringBuilder sb = new StringBuilder();
        getTime(sb, startDate);
    }

    /**
     * 获取时间
     *
     * @param sb
     * @param view
     */
    private void getTime(StringBuilder sb, TextView view) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (v, year, monthOfYear, dayOfMonth) -> {
            sb.append(year)
                    .append("-").append(monthOfYear + 1).append("-").append(dayOfMonth).append(" ");
            new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                sb.append(hourOfDay)
                        .append(":").append(minute);
                view.setText(sb.toString());
            }, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), true).show();
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)).
                show();


    }

    /**
     * 修改键触发
     *
     * @param trainSchedule
     * @param position
     */
    private void onModifyClicked(TrainSchedule trainSchedule, int position) {

        String sourceText = source.getText().toString();
        String destinationText = destination.getText().toString();
        String identityText = identity.getText().toString();
        String startDateText = startDate.getText().toString();
        String reachDateText = reachDate.getText().toString();
        String restText = rest.getText().toString();
        if (TextUtils.isEmpty(sourceText) || TextUtils.isEmpty(destinationText)
                || TextUtils.isEmpty(identityText) || TextUtils.isEmpty(startDateText)
                || TextUtils.isEmpty(reachDateText) || TextUtils.isEmpty(restText)) {
            showErrorMessage("输入信息有误！请重新输入！");
        } else {
            showLoading();
            Request.Builder builder = new Request.Builder();
            // TODO: 2017-5-26  url

            HashMap<String, String> params = new HashMap<>();
            params.put("train_schedule", trainSchedule.getScheduleNum());
            String newUrl = HrefUtil.putParams(Contract.MODIFY_TRAIN_SCHEDULE, params);
            builder.url(newUrl).get();
            OkHttpClient client = new OkHttpClient();
            client.newCall(builder.build()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    dismissLoading();
                    showErrorMessage("请求失败，请稍后重试！");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        trainSchedule.setScheduleNum(identityText);
                        trainSchedule.setSource(sourceText);
                        trainSchedule.setDestination(destinationText);
                        trainSchedule.setStartDate(DateFormatUtil.stringToDetailDate(startDateText));
                        trainSchedule.setReachDate(DateFormatUtil.stringToDetailDate(reachDateText));
                        trainInfoAdapter.notifyItemChanged(position);
                        dismissLoading();
                        showSuccessDialog("修改成功！");
                    }
                }
            });


        }
    }

    /**
     * 查看座位详情
     *
     * @param trainSchedule
     */
    private void onSeatDetailClicked(TrainSchedule trainSchedule) {
        Intent intent = new Intent(TrainScheduleActivity.this, SeatInfoActivity.class);
        intent.putExtra(TRAIN_SCHEDULE, trainSchedule);
        startActivity(intent);
    }

    /**
     * 购票
     *
     * @param trainSchedule
     */
    private void onSaleClicked(TrainSchedule trainSchedule) {
        Intent intent = new Intent(TrainScheduleActivity.this, SaleActivity.class);
        intent.putExtra(TRAIN_SCHEDULE, trainSchedule);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(identity, "identity"),
                Pair.create(source, "source"), Pair.create(destination, "destination"), Pair.create(startDate, "startDate"),
                Pair.create(reachDate, "reachDate"), Pair.create(rest, "rest")).toBundle());
    }

    private void showLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void dismissLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private SweetAlertDialog errorDialog;

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

    private SweetAlertDialog successDialog;

    public void showSuccessDialog(String msg) {
        successDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        successDialog.setTitleText(msg)
                .setCancelable(true);
        successDialog.setCanceledOnTouchOutside(true);
        successDialog.show();
    }


    /**
     * 列车信息获取成功
     *
     * @param trainInfoListWrapper
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTrainDataReady(TrainInfoListWrapper trainInfoListWrapper) {
        dismissLoading();
        if (trainInfoListWrapper.getTrainSchedules().size() == 0) {
            showErrorMessage("没有找到列车信息！");
        } else {
            initRecyclerView(trainInfoListWrapper.getTrainSchedules());
        }
    }

    /**
     * 请求失败
     *
     * @param error
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestError(RequestError error) {
        dismissLoading();
        showErrorMessage("请求失败！");
    }

    public static void startTrainScheduleActivity(Context context, int type, String... params) {
        Intent intent = new Intent(context, TrainScheduleActivity.class);
        intent.putExtra(LAUNCH_TYPE_KEY, type);
        intent.putExtra(LAUNCH_PARAMS, params);
        context.startActivity(intent);
    }
}
