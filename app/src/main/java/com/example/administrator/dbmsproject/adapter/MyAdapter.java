package com.example.administrator.dbmsproject.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.dbmsproject.AddressWrapper;
import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.model.locationbean.IAddress;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-3-28.
 */

public class MyAdapter<T extends IAddress> extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<T> mList = new ArrayList<>();

    public MyAdapter(List<T> list) {
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int p = position;
        IAddress iAddress = mList.get(position);
        holder.mTextView.setText(iAddress.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AddressWrapper(mList.get(p)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
