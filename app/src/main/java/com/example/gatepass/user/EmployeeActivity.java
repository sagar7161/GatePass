package com.example.gatepass.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gatepass.APIService;
import com.example.gatepass.History.HistoryActivity;
import com.example.gatepass.MainActivity;
import com.example.gatepass.Notifications.Client;
import com.example.gatepass.Notifications.Data;
import com.example.gatepass.Notifications.MyResponse;
import com.example.gatepass.Notifications.Sender;
import com.example.gatepass.Notifications.Token;
import com.example.gatepass.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;


public class EmployeeActivity extends AppCompatActivity {

    //Tag for the logs optional
    private static final String TAG = "EmployeeActivity";

    String UserId,UserName,profile;
    EditText etReason,etDate;
    Button btnSubmit;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    APIService apiService;
    ImageView empImage,logout,history;
    TextView empId,empName;


    DatabaseReference mUserDatabase;


    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        empId=findViewById(R.id.empId);
        empImage = findViewById(R.id.empImage);
        empName = findViewById(R.id.empName);
        logout = findViewById(R.id.logout);
        history = findViewById(R.id.history);
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("PrefFile", 0); // 0 - for private mode
        SharedPreferences.Editor editor1 = pref1.edit();
        editor1.putString("post", "employee");
        editor1.apply();
        checkLogin();
        mUserDatabase = database.getReference("employee").child(UserId);

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        updateToken(FirebaseInstanceId.getInstance().getToken());

        etReason = findViewById(R.id.reasonForLeave);
        btnSubmit = findViewById(R.id.btnSubmit);



        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeActivity.this, HistoryActivity.class);
                intent.putExtra("userid",UserId);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(EmployeeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = etReason.getText().toString();
                //String date = etDate.getText().toString();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                Date c = Calendar.getInstance().getTime();
                //String pattern = "yyyy-MM-dd";
                //String pattern = "yyyy-MM-dd HH:mm:ss";
                String pattern = "dd-MM-yyyy HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                String datePattern = simpleDateFormat.format(new Date());

                String randomId = randomString(5);
                DatabaseReference myRef2 = database.getReference("leave").child(randomId);

                if (reason.isEmpty()) {
                    etReason.setError("Enter reason");
                    etReason.requestFocus();
                }
                else{
                    Map userInfo2 = new HashMap();
                    userInfo2.put("leaveId", randomId);
                    userInfo2.put("reason", reason);
                    userInfo2.put("date", "5-3-2019");
                    userInfo2.put("timestamp", datePattern);
                    //userInfo2.put("timestamp", sdf.format(timestamp));
                    userInfo2.put("status", "Pending");
                    userInfo2.put("id", UserId);
                    userInfo2.put("name", UserName);
                    userInfo2.put("profile", profile);
                    myRef2.updateChildren(userInfo2);
                    Toast.makeText(EmployeeActivity.this, "Your leave application has been submit successfully.", Toast.LENGTH_SHORT).show();
                    etReason.setText("");
                }


                //Opp. uid, own name,msg
                sendNotifiaction("srkay_0","srkay_7","new leave app.");
            }
        });

    }

    private void setBasicInfo() {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String empName1 = dataSnapshot.child("name").getValue().toString();
                String id1 = dataSnapshot.child("id").getValue().toString();
                String profile1 = dataSnapshot.child("profile").getValue().toString();
                Glide.with(getApplicationContext()).load(profile1).into(empImage);
                empName.setText(empName1);
                empId.setText(id1);
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
        DatabaseReference myRef = database.getReference("employee").child(UserId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                UserName = dataSnapshot.child("name").getValue(String.class);
                profile = dataSnapshot.child("profile").getValue(String.class);
                empName.setText(dataSnapshot.child("name").getValue(String.class));
                empId.setText(dataSnapshot.child("id").getValue(String.class));
                String profile1 = dataSnapshot.child("profile").getValue().toString();
                Glide.with(getApplicationContext()).load(profile1).into(empImage);
                Log.v(TAG,"UserId:"+UserId+"  UserName:"+UserName);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        String sharedPost =  pref.getString("post",null);

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("PrefFile", 0); // 0 - for private mode
        SharedPreferences.Editor editor1 = pref1.edit();
        editor1.putString("post", sharedPost);
        editor1.apply();

        if (sharedId == "null"){
            Intent startIntent = new Intent(EmployeeActivity.this, MainActivity.class);
            startActivity(startIntent);
            finish();
        }

    }

    /*random name for image*/
    public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static Random RANDOM = new Random();
    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(UserId).setValue(token1);
    }

    private void sendNotifiaction(String receiver, final String username, final String message) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Token token = snapshot.getValue(Token.class);

                    Data data = new Data(UserId,R.drawable.srkaylogo, username + ": " + message, "New Message",
                            "srkay_0");

                    Log.v("insideNotification", message);
                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {

                                @Override
                                public void onResponse(Call<MyResponse> call, retrofit2.Response<MyResponse> response) {
                                    if (response.code() == 200) {
                                        if (response.body().success != 1) {
                                            Toast.makeText(EmployeeActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
