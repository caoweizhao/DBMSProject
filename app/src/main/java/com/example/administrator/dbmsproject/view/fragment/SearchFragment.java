package com.example.administrator.dbmsproject.view.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.util.DateFormatUtil;
import com.example.administrator.dbmsproject.view.MainActivity;
import com.example.administrator.dbmsproject.view.SelectAddressActivity;

import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017-5-24.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {
    public static final int SELECT_SOURCE_REQUEST_CODE = 0Xaaa;
    public static final int SELECT_DESTINATION_REQUEST_CODE = 0Xaab;
    public TextView mSourceTextView;
    public TextView mDestinationTextView;
    public TextView mDateTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_train_layout, container, false);
        mSourceTextView = (TextView) view.findViewById(R.id.source_text_view);
        mDestinationTextView = (TextView) view.findViewById(R.id.destination_text_view);
        mDateTextView = (TextView) view.findViewById(R.id.date_text_view);
        Date date = new Date();
        mDateTextView.setText(DateFormatUtil.normalDateToString(date));
        view.findViewById(R.id.source_layout).setOnClickListener(this);
        view.findViewById(R.id.destination_layout).setOnClickListener(this);
        view.findViewById(R.id.date_layout).setOnClickListener(this);
        view.findViewById(R.id.search_btn).setOnClickListener(this);
        return view;
    }

    public void search() {
        ((MainActivity) getActivity()).getPresenter().onSearchButtonClicked(mSourceTextView.getText().toString(), mDestinationTextView.getText().toString(), mDateTextView.getText().toString());
    }

    public void onDateClicked(final View view) {
        Calendar c = Calendar.getInstance();
        final TextView textView = (TextView) view.findViewById(R.id.date_text_view);
        new DatePickerDialog(getContext(), (v, year, monthOfYear, dayOfMonth) -> textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth), c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)).show();
    }

    public void onDestinationClicked() {
        Intent intent = new Intent(getActivity(), SelectAddressActivity.class);
        startActivityForResult(intent, SELECT_DESTINATION_REQUEST_CODE);
    }

    public void onSourceClicked() {
        Intent intent = new Intent(getActivity(), SelectAddressActivity.class);
        startActivityForResult(intent, SELECT_SOURCE_REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.source_layout:
                onSourceClicked();
                break;
            case R.id.destination_layout:
                onDestinationClicked();
                break;
            case R.id.date_layout:
                onDateClicked(v);
                break;
            case R.id.search_btn:
                search();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String cityName = data.getStringExtra(SelectAddressActivity.COUNTRY_NAME_KEY);
            if (cityName == null) {
                return;
            }
            if (requestCode == SELECT_DESTINATION_REQUEST_CODE) {
                mDestinationTextView.setText(cityName);
            } else if (requestCode == SELECT_SOURCE_REQUEST_CODE) {
                mSourceTextView.setText(cityName);
            }
        }
    }
}
