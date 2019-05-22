package com.example.gatepass.History;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.gatepass.Adapter.HRHistoryAdapter;
import com.example.gatepass.Model.historyHrModel;
import com.example.gatepass.R;
import com.example.gatepass.user.HRActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HrHistoryActivity extends AppCompatActivity {


    //Tag for the logs optional
    private static final String TAG = "HrHistoryActivity";

    String UserId;
    private List<historyHrModel> historyHrModel = new ArrayList<>();

    private RecyclerView recyclerView;
    private HRHistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hr_history);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new HRHistoryAdapter(this, historyHrModel);

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
                    String isTrue = snapshot.child("status").getValue().toString();

                    //String date = snapshot.child("date").getValue().toString();
                    String id = snapshot.child("id").getValue().toString();
                    String name = snapshot.child("name").getValue().toString();
                    String reason = snapshot.child("reason").getValue().toString();
                    String status = snapshot.child("status").getValue().toString();
                    String timestamp = snapshot.child("timestamp").getValue().toString();
                    String empImage = snapshot.child("profile").getValue().toString();
                    String leaveId = snapshot.child("leaveId").getValue().toString();
                    Log.v(TAG, "id:" + id + " name:" + name + " reason:" + reason + " status:" + status + " timestamp:" + timestamp + " empImage" + empImage + " leaveId:" + leaveId);
                    historyHrModel leave = new historyHrModel(id, name, reason, status, timestamp, empImage, leaveId);
                    historyHrModel.add(leave);
                    mAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HrHistoryActivity.this, HRActivity.class);
        startActivity(intent);
        finish();
    }
}
