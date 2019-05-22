package com.example.gatepass.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.gatepass.Adapter.WatchmanAdapter;
import com.example.gatepass.MainActivity;
import com.example.gatepass.Model.ModelLeave;
import com.example.gatepass.Notifications.Token;
import com.example.gatepass.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class WatchmanActivity extends AppCompatActivity {

    //Tag for the logs optional
    private static final String TAG = "WatchmanActivity";

    String UserId;
    ImageView logout;


    private List<ModelLeave> modelLeaves = new ArrayList<>();

    private RecyclerView recyclerView;
    /*private MoviesAdapter mAdapter;*/
    private WatchmanAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchman);
        checkLogin();

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(WatchmanActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new WatchmanAdapter(this,modelLeaves);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ((LinearLayoutManager) mLayoutManager).setReverseLayout(true);

        ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(mAdapter);

        prepareLeaveData();
        updateToken(FirebaseInstanceId.getInstance().getToken());

    }

    private void prepareLeaveData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("leave");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("leave");

        reference.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String isTrue = snapshot.child("status").getValue().toString();
                    if (isTrue=="true"){
                        String date = snapshot.child("date").getValue().toString();
                        String id = snapshot.child("id").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String reason = snapshot.child("reason").getValue().toString();
                        String status = snapshot.child("status").getValue().toString();
                        String timestamp = snapshot.child("timestamp").getValue().toString();
                        String empImage = snapshot.child("profile").getValue().toString();
                        String leaveId = snapshot.child("leaveId").getValue().toString();
                        Log.v(TAG, "date:" + date + " id:" + id + " name:" + name + " reason:" + reason + " status:" + status + " timestamp:" + timestamp + " empImage" + empImage + " leaveId:" + leaveId);
                        ModelLeave leave = new ModelLeave(id, name, reason, status, timestamp, empImage, leaveId);
                        modelLeaves.add(leave);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void checkLogin() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("PrefFile", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String sharedId = pref.getString("id", null);
        UserId = sharedId;

        String sharedPost =  pref.getString("post",null);

        if (sharedId.equals(null)){
            Intent startIntent = new Intent(WatchmanActivity.this, MainActivity.class);
            startActivity(startIntent);
            finish();
        }
    }
    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(UserId).setValue(token1);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
