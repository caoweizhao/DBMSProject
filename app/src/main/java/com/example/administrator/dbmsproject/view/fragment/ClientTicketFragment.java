package com.example.administrator.dbmsproject.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.model.trainbean.Ticket;
import com.example.administrator.dbmsproject.model.wrapper.TicketListWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-5-24.
 */

public class ClientTicketFragment extends Fragment {

    public static final String KEY = "TICKET_LIST";
    RecyclerView mRecyclerView;
    TicketAdapter mAdapter;
    List<Ticket> mTickets = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.client_ticket_layout, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.client_ticket_recycler_view);
        view.findViewById(R.id.back_in_client_ticket).setOnClickListener(e -> {
            getActivity().onBackPressed();
        });
        return view;
    }

    public static ClientTicketFragment newInstance(TicketListWrapper ticketListWrapper) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, ticketListWrapper);
        ClientTicketFragment clientTicketFragment = new ClientTicketFragment();
        clientTicketFragment.setArguments(bundle);
        return clientTicketFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getSerializable(KEY) != null) {
                mTickets = ((TicketListWrapper) (bundle.getSerializable(KEY))).getTicketList();
            }
            if (mTickets != null) {
                mAdapter = new TicketAdapter(mTickets);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

    class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

        List<Ticket> mTickets;

        TicketAdapter(List<Ticket> tickets) {
            mTickets = tickets;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Ticket ticket = mTickets.get(position);
            holder.idTextView.setText(ticket.getIdentityCard());
            holder.sourceTextView.setText(ticket.getTrainSchedule().getSource());
            holder.destinationTextView.setText(ticket.getTrainSchedule().getDestination());
            holder.ticketIdTextView.setText(ticket.getTicketId());
        }

        @Override
        public int getItemCount() {
            return mTickets.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView idTextView;
            TextView ticketIdTextView;
            TextView sourceTextView;
            TextView destinationTextView;

            ViewHolder(View itemView) {
                super(itemView);
                idTextView = (TextView) itemView.findViewById(R.id.id_card_ticket_item);
                sourceTextView = (TextView) itemView.findViewById(R.id.source_text_view_ticket_item);
                destinationTextView = (TextView) itemView.findViewById(R.id.destination_text_view_ticket_item);
                ticketIdTextView = (TextView) itemView.findViewById(R.id.ticket_id_ticket_item);
            }
        }
    }
}
