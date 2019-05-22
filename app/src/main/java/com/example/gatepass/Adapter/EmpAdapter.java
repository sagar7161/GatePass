package com.example.gatepass.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gatepass.Model.EmpModel;
import com.example.gatepass.R;

import java.util.List;

public class EmpAdapter extends RecyclerView.Adapter<EmpAdapter.MyViewHolder> {
    private static final String TAG = "EmpAdapter";


    private Context mContext;
    private List<EmpModel> mUsers;
    private boolean ischat;


    private List<EmpModel> empmodel;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView status,time,reason;


        public MyViewHolder(View view) {
            super(view);

            time = view.findViewById(R.id.time);
            reason = view.findViewById(R.id.reason);
            status = view.findViewById(R.id.status);

        }
    }

    public EmpAdapter(Context mContext, List<EmpModel> empmodel){
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.empmodel = empmodel;
    }
    @Override
    public EmpAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emp_layout, parent, false);

        return new EmpAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpAdapter.MyViewHolder myViewHolder, int i) {
        final EmpModel EmpModel = empmodel.get(i);


        if (EmpModel.getStatus().equals("Approved"))
            myViewHolder.status.setTextColor(Color.parseColor("#378B37"));
        else if(EmpModel.getStatus().equals("Rejected"))
            myViewHolder.status.setTextColor(Color.parseColor("#C72828"));
        else
            myViewHolder.status.setTextColor(Color.parseColor("#000000"));


        myViewHolder.status.setText(EmpModel.getStatus());
        myViewHolder.time.setText("Date:"+ EmpModel.getTimestamp());
        myViewHolder.reason.setText(EmpModel.getReason());

    }

    @Override
    public int getItemCount() {
        return empmodel.size();
    }
}