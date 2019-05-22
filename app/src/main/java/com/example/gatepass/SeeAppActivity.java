package com.example.gatepass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gatepass.user.HRActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SeeAppActivity extends AppCompatActivity {
    //Tag for the logs optional
    private static final String TAG = "SeeAppActivity";

    TextView date, id, name, reason, status, timestamp, leaveId;

    ImageView btnAllow, btnReject, empImage;
    ImageView profileImage;
    String leaveIdFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_app);

        empImage = findViewById(R.id.empImage);
        name = findViewById(R.id.empName);
        id = findViewById(R.id.empId);
        leaveId = findViewById(R.id.leaveId);
        reason = findViewById(R.id.reason);
        btnAllow = findViewById(R.id.btnAllow);
        btnReject = findViewById(R.id.btnReject);

        leaveIdFromIntent = getIntent().getStringExtra("leaveId");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("leave").child(leaveIdFromIntent);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dateString = snapshot.child("date").getValue().toString();
                String idString = snapshot.child("id").getValue().toString();
                String nameString = snapshot.child("name").getValue().toString();
                String reasonString = snapshot.child("reason").getValue().toString();
                String statusString = snapshot.child("status").getValue().toString();
                String timestampString = snapshot.child("timestamp").getValue().toString();
                String empImageString = snapshot.child("profile").getValue().toString();
                String leaveIdString = snapshot.child("leaveId").getValue().toString();

                Glide.with(getApplicationContext()).load(empImageString).into(empImage);
                name.setText(nameString);
                id.setText(idString);
                leaveId.setText(leaveIdString);
                reason.setText(reasonString);


                Log.v(TAG, "date:" + date + " id:" + id + " name:" + name + " reason:" + reason + " status:" + status + " timestamp:" + timestamp + " empImage" + empImage + " leaveId:" + leaveId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("leave").child(leaveIdFromIntent);
                reference.child("status").setValue(true);
                Intent intent = new Intent(SeeAppActivity.this, HRActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("leave").child(leaveIdFromIntent);
                reference.child("status").setValue(false);
                Intent intent = new Intent(SeeAppActivity.this, HRActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SeeAppActivity.this, HRActivity.class);
        startActivity(intent);
    }


}
