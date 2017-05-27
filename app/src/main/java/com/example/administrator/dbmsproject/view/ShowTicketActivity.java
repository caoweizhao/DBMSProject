package com.example.administrator.dbmsproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.model.trainbean.Ticket;
import com.example.administrator.dbmsproject.util.DateFormatUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowTicketActivity extends AppCompatActivity {

    @BindView(R.id.ticket_id_ticket_layout)
    TextView mTicketIdTicketLayout;
    @BindView(R.id.id_card_ticket_layout)
    TextView mIdCardTicketLayout;
    @BindView(R.id.ticket_man_text_view_ticket_layout)
    TextView mTicketManTextViewTicketLayout;
    @BindView(R.id.train_id_text_view_ticket_layout)
    TextView mTrainIdTextViewTicketLayout;
    @BindView(R.id.source_text_view_ticket_layout)
    TextView mSourceTextViewTicketLayout;
    @BindView(R.id.destination_text_view_ticket_layout)
    TextView mDestinationTextViewTicketLayout;
    @BindView(R.id.start_time_text_view_ticket_layout)
    TextView mStartTimeTextViewTicketLayout;
    @BindView(R.id.reach_time_text_view_ticket_layout)
    TextView mReachTimeTextViewTicketLayout;
    @BindView(R.id.carriage_num_text_view_ticket_layout)
    TextView mCarriageNumTextViewTicketLayout;
    @BindView(R.id.seat_num_text_view_ticket_layout)
    TextView mSeatNumTextViewTicketLayout;
    @BindView(R.id.price_text_view_ticket_layout)
    TextView mPriceTextViewTicketLayout;
    @BindView(R.id.order_time_text_view_ticket_layout)
    TextView mOrderTimeTextViewTicketLayout;
    @BindView(R.id.refunds_time_text_view_ticket_layout)
    TextView mRefundsTimeTextViewTicketLayout;
    @BindView(R.id.status_text_view_ticket_layout)
    TextView mStatusTextView;

    Ticket mTicket;
    String sourceText;
    String destinationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ticket);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            mTicket = (Ticket) intent.getSerializableExtra(SaleActivity.TICKET_DATA);
            sourceText = intent.getStringExtra(SaleActivity.TICKET_SOURCE);
            sourceText = intent.getStringExtra(SaleActivity.TICKET_DESTINATION);
            if (mTicket == null) {
                finish();
            }
        }

        mTicketIdTicketLayout.setText(mTicket.getTicketId());
        mCarriageNumTextViewTicketLayout.setText(mTicket.getCarriageNo());
        mDestinationTextViewTicketLayout.setText(destinationText);
        mSourceTextViewTicketLayout.setText(sourceText);
        mIdCardTicketLayout.setText(mTicket.getIdentityCard());
        mOrderTimeTextViewTicketLayout.setText(DateFormatUtil.detailDateToString(mTicket.getOrderTime()));
        mStartTimeTextViewTicketLayout.setText(DateFormatUtil.detailDateToString(mTicket.getStartTime()));
        mReachTimeTextViewTicketLayout.setText(DateFormatUtil.detailDateToString(mTicket.getReachTime()));
        mCarriageNumTextViewTicketLayout.setText(mTicket.getCarriageNo());
        mSeatNumTextViewTicketLayout.setText(mTicket.getSeatNo());
        int status = mTicket.getStatus();
        mStatusTextView.setText(String.valueOf(status));
        if (status == 1) {
            mRefundsTimeTextViewTicketLayout.setText(DateFormatUtil.detailDateToString(mTicket.getRefundsTime()));
        } else {
            findViewById(R.id.refunds_layout).setVisibility(View.GONE);
        }
    }
}
