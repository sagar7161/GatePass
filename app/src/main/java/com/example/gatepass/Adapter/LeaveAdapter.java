package com.example.gatepass.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gatepass.Model.ModelLeave;
import com.example.gatepass.R;
import com.example.gatepass.SeeAppActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.MyViewHolder> {
    private static final String TAG = "LeaveAdapter";


    private Context mContext;
    private List<ModelLeave> mUsers;
    private boolean ischat;


    private List<ModelLeave> modelLeaves;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView leaveId;
        public TextView empName,empId,time,reason,showMore;
        public CircleImageView empImage;

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




    public LeaveAdapter(Context mContext, List<ModelLeave> modelLeaves){
        this.mUsers = mUsers;
        this.mContext = mContext;

        this.modelLeaves = modelLeaves;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new LeaveAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final ModelLeave modelLeave = modelLeaves.get(i);

        Log.v(TAG,"date:"+modelLeave.getDate()+" id:"+modelLeave.getId()+" name:"+modelLeave.getName()+" reason:"+modelLeave.getReason()+" status:"+modelLeave.getStatus()+" timestamp:"+modelLeave.getTimestamp()+" empImage"+modelLeave.getEmpImage());

        myViewHolder.empName.setText(modelLeave.getName());
        myViewHolder.empId.setText(modelLeave.getId());
        myViewHolder.time.setText(modelLeave.getTimestamp()+ "\nGate Pass Id:"+modelLeave.getLeaveId());
        myViewHolder.reason.setText(modelLeave.getReason());

        Glide.with(mContext).load(modelLeave.getEmpImage()).into(myViewHolder.empImage);

        final int position =i;


        myViewHolder.showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, ""+ modelLeave.getLeaveId(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, SeeAppActivity.class);
                intent.putExtra("leaveId", modelLeave.getLeaveId());
                //intent.putExtra("")
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelLeaves.size();
    }
}