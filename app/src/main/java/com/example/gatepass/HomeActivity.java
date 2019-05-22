package com.example.gatepass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.gatepass.user.EmployeeActivity;
import com.example.gatepass.user.HRActivity;
import com.example.gatepass.user.WatchmanActivity;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    //Tag for the logs optional
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        checkLogin();
    }

    private void checkLogin() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("PrefFile", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String sharedId = pref.getString("id", null);
        String sharedPost = pref.getString("post", null);
        Log.v(TAG, "sharedPost:" + sharedPost + " shareId:" + sharedId);
        String hr = "HR", employee = "employee", watchman = "watchman";
        if (Objects.equals(sharedPost, hr)) {
            Log.v(TAG, "Inside HR");
            SharedPreferences pref1 = getApplicationContext().getSharedPreferences("PrefFile", 0); // 0 - for private mode
            SharedPreferences.Editor editor1 = pref1.edit();
            editor1.putString("post", sharedPost);
            editor1.apply();
            Intent intent = new Intent(HomeActivity.this, HRActivity.class);
            startActivity(intent);

        } else if (Objects.equals(sharedPost, employee)) {
            Log.v(TAG, "Inside employee");
            SharedPreferences pref1 = getApplicationContext().getSharedPreferences("PrefFile", 0); // 0 - for private mode
            SharedPreferences.Editor editor1 = pref1.edit();
            editor1.putString("post", sharedPost);
            editor1.apply();
            Intent intent = new Intent(HomeActivity.this, EmployeeActivity.class);
            startActivity(intent);
        } else if (Objects.equals(sharedPost, watchman)) {
            Log.v(TAG, "Inside watchman");
            SharedPreferences pref1 = getApplicationContext().getSharedPreferences("PrefFile", 0); // 0 - for private mode
            SharedPreferences.Editor editor1 = pref1.edit();
            editor1.putString("post", sharedPost);
            editor1.apply();
            Intent intent = new Intent(HomeActivity.this, WatchmanActivity.class);
            startActivity(intent);
        } else {
            Log.v(TAG, "Inside Main");
            Intent startIntent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(startIntent);
            finish();
        }
    }
}
