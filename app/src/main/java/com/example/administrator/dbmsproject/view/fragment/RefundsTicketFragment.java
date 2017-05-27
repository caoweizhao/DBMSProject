package com.example.administrator.dbmsproject.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.view.MainActivity;

/**
 * Created by Administrator on 2017-5-24.
 */

public class RefundsTicketFragment extends Fragment {

    private TextInputLayout mTextInputLayout;
    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.refunds_ticket_layout, container, false);
        mTextInputLayout = (TextInputLayout) view.findViewById(R.id.text_input_layout);
        mButton = (Button) view.findViewById(R.id.search_ticket);
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
        mButton.setOnClickListener(e -> checkId());
        return view;
    }

    public void checkId() {
        if (mTextInputLayout.getEditText().getText().length() < 18) {
            ((MainActivity) getActivity()).showErrorMessage("请输入完整的身份证号码！");
        } else {
            ((MainActivity) getActivity()).checkTicket(mTextInputLayout.getEditText().getText().toString());
        }
    }
}
