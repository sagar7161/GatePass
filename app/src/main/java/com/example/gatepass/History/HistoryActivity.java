package com.example.gatepass.History;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.gatepass.Adapter.EmpAdapter;
import com.example.gatepass.Model.EmpModel;
import com.example.gatepass.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    //Tag for the logs optional
    private static final String TAG = "HistoryActivity";
    String UserId;

    private List<EmpModel> empModels = new ArrayList<>();

    private RecyclerView recyclerView;
    private EmpAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

         UserId = getIntent().getStringExtra("userid");
        Log.v(TAG,"From Intent userId:"+UserId);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new EmpAdapter(this,empModels);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ((LinearLayoutManager) mLayoutManager).setReverseLayout(true);

        ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        prepareLeaveData();


    }
    private void prepareLeaveData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("leave");

        reference.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String isUserID = snapshot.child("id").getValue().toString();
                    Log.v(TAG,"isUserId:"+isUserID);

                    if (isUserID.equals(UserId)){
                        //String date = snapshot.child("date").getValue().toString();
                        String id = snapshot.child("id").getValue().toString();
                        String reason = snapshot.child("reason").getValue().toString();
                        String status = snapshot.child("status").getValue().toString();
                        String timestamp = snapshot.child("timestamp").getValue().toString();
                        String leaveId = snapshot.child("leaveId").getValue().toString();

                        if (status=="true")
                            status="Approved";
                        else if (status=="false")
                            status="Rejected";
                        else
                            status = "Pending";

                        Log.v(TAG,"LeaveId:"+leaveId);
                        EmpModel model = new EmpModel(reason,status,timestamp);
                        empModels.add(model);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
