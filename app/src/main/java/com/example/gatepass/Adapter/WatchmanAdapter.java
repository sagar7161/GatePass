package com.example.gatepass.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gatepass.Model.ModelLeave;
import com.example.gatepass.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WatchmanAdapter extends RecyclerView.Adapter<WatchmanAdapter.MyViewHolder> {
    private static final String TAG = "WatchmanAdapter";


    private Context mContext;
    private List<ModelLeave> mUsers;
    private boolean ischat;


    private List<ModelLeave> modelLeaves;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView leaveId;
        public TextView empName,empId,time,reason;
        public CircleImageView empImage;

        public MyViewHolder(View view) {
            super(view);
            leaveId = view.findViewById(R.id.leaveId);
            empName = view.findViewById(R.id.empName);
            empId = view.findViewById(R.id.empId);
            time = view.findViewById(R.id.time);
           // reason = view.findViewById(R.id.reason);
            empImage = view.findViewById(R.id.empImage);
        }
    }


    public WatchmanAdapter(Context mContext, List<ModelLeave> modelLeaves){
        this.mUsers = mUsers;
        this.mContext = mContext;

        this.modelLeaves = modelLeaves;
    }
    @Override
    public WatchmanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.watchmanrow, parent, false);

        return new WatchmanAdapter.MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull WatchmanAdapter.MyViewHolder myViewHolder, int i) {
        final ModelLeave modelLeave = modelLeaves.get(i);

        Log.v(TAG,"date:"+modelLeave.getDate()+" id:"+modelLeave.getId()+" name:"+modelLeave.getName()+" reason:"+modelLeave.getReason()+" status:"+modelLeave.getStatus()+" timestamp:"+modelLeave.getTimestamp()+" empImage"+modelLeave.getEmpImage());

        myViewHolder.leaveId.setText("Gate Pass Id: "+modelLeave.getLeaveId());
        myViewHolder.empName.setText(modelLeave.getName());
        myViewHolder.empId.setText("Employee Id: "+modelLeave.getId());
        myViewHolder.time.setText(modelLeave.getTimestamp());
       // myViewHolder.reason.setText(modelLeave.getReason());

        Glide.with(mContext).load(modelLeave.getEmpImage()).into(myViewHolder.empImage);


        final int position =i;




    }



    @Override
    public int getItemCount() {
        return modelLeaves.size();
    }
}