package com.example.administrator.dbmsproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.dbmsproject.R;
import com.example.administrator.dbmsproject.model.trainbean.TrainSchedule;
import com.example.administrator.dbmsproject.util.DateFormatUtil;

import java.util.List;

/**
 * Created by Administrator on 2017-5-22.
 */

public class TrainInfoAdapter extends RecyclerView.Adapter<TrainInfoAdapter.ViewHolder> {

    private onItemClickedListener mOnItemClickedListener;
    private List<TrainSchedule> mTrainSchedules;

    public TrainInfoAdapter(@NonNull List<TrainSchedule> trainSchedules) {
        mTrainSchedules = trainSchedules;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.train_schedule_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrainSchedule trainSchedule = mTrainSchedules.get(position);
        holder.mTrainIdentity.setText(trainSchedule.getScheduleNum());
        holder.mStartTime.setText(DateFormatUtil.hourMinuteToString(trainSchedule.getStartDate()));
        holder.mSource.setText(trainSchedule.getSource());
        holder.mDestination.setText(trainSchedule.getDestination());
        holder.mRemainCount.setText(String.valueOf(trainSchedule.getRestTicket()));
        holder.itemView.setOnClickListener(e -> {
            if (mOnItemClickedListener != null) {
                mOnItemClickedListener.onItemClicked(holder.itemView, position, mTrainSchedules.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrainSchedules.size();
    }

    public void setOnItemClickedListener(onItemClickedListener onItemClickedListener) {
        mOnItemClickedListener = onItemClickedListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTrainIdentity;
        TextView mSource;
        TextView mDestination;
        TextView mStartTime;
        TextView mRemainCount;

        ViewHolder(View itemView) {
            super(itemView);
            mTrainIdentity = (TextView) itemView.findViewById(R.id.identity_item);
            mSource = (TextView) itemView.findViewById(R.id.source_text_view_item);
            mDestination = (TextView) itemView.findViewById(R.id.destination_text_view_item);
            mStartTime = (TextView) itemView.findViewById(R.id.start_time_item);
            mRemainCount = (TextView) itemView.findViewById(R.id.remain_count_text_view_item);
        }
    }

    public interface onItemClickedListener {
        void onItemClicked(View view, int position, TrainSchedule trainSchedule);
    }
}
