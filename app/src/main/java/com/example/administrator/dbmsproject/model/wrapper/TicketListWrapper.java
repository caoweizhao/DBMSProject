package com.example.administrator.dbmsproject.model.wrapper;

import com.example.administrator.dbmsproject.model.trainbean.Ticket;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017-5-22.
 */

public class TicketListWrapper implements Serializable {
    private List<Ticket> mTickets;

    public TicketListWrapper(List<Ticket> tickets) {
        mTickets = tickets;
    }


    public List<Ticket> getTicketList() {
        return mTickets;
    }
}
