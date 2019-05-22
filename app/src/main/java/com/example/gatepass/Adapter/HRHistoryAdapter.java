package com.example.gatepass.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gatepass.Model.historyHrModel;
import com.example.gatepass.R;

import java.util.List;

public class HRHistoryAdapter extends RecyclerView.Adapter<HRHistoryAdapter.MyViewHolder> {
    private static final String TAG = "HRHistoryAdapter";


    private Context mContext;
    private List<historyHrModel> mUsers;
    private boolean ischat;


    private List<historyHrModel> historyHrModel;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView leaveId;
        public TextView empName, empId, time, reason, showMore;
        public ImageView empImage;

        public MyViewHolder(View view) {
            super(view);
            empName = view.findViewById(R.id.empName);
            empId = view.findViewById(R.id.empId);
            time = view.findViewById(R.id.time);
            reason = view.findViewById(R.id.reason);
            empImage = view.findViewById(R.id.empImage);
            showMore = view.findViewById(R.id.showMore);

        }
    }


    public HRHistoryAdapter(Context mContext, List<historyHrModel> mhistoryHrModel) {
        this.mUsers = mUsers;
        this.mContext = mContext;

        this.historyHrModel = mhistoryHrModel;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hr_row_history, parent, false);

        return new HRHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final historyHrModel historyHrModelX = historyHrModel.get(i);

        Log.v(TAG, "date:" + historyHrModelX.getDate() + " id:" + historyHrModelX.getId() + " name:" + historyHrModelX.getName() + " reason:" + historyHrModelX.getReason() + " status:" + historyHrModelX.getStatus() + " timestamp:" + historyHrModelX.getTimestamp() + " empImage" + historyHrModelX.getEmpImage());

        myViewHolder.empName.setText(historyHrModelX.getName());
        myViewHolder.empId.setText(historyHrModelX.getId());
        myViewHolder.time.setText(historyHrModelX.getTimestamp());
        myViewHolder.reason.setText(historyHrModelX.getReason());

        if (historyHrModelX.getStatus().equals("true")) {
            myViewHolder.showMore.setTextColor(Color.parseColor("#378B37"));
            myViewHolder.showMore.setText("Approved");
        } else if (historyHrModelX.getStatus().equals("false")) {
            myViewHolder.showMore.setTextColor(Color.parseColor("#C72828"));
            myViewHolder.showMore.setText("Rejected");
        } else {
            myViewHolder.showMore.setTextColor(Color.parseColor("#000000"));
            myViewHolder.showMore.setText("Pending");
        }

        Glide.with(mContext).load(historyHrModelX.getEmpImage()).into(myViewHolder.empImage);

        final int position = i;


    }

    @Override
    public int getItemCount() {
        return historyHrModel.size();
    }
}