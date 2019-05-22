package com.example.gatepass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gatepass.user.EmployeeActivity;
import com.example.gatepass.user.HRActivity;
import com.example.gatepass.user.WatchmanActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText edId, edPassword;
    Button btnLogin;

    private FirebaseDatabase mDatabase;
    private DatabaseReference databaseReference;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    //Tag for the logs optional
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = mDatabase.getReference().child("employee");//.child(enr_number);

        edId = findViewById(R.id.etId);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = edId.getText().toString();
                String password = edPassword.getText().toString();
                if (id.isEmpty()) {
                    edId.setError("Enter your id.");
                    edId.requestFocus();
                }
                if (password.isEmpty()) {
                    edPassword.setError("Enter password.");
                    edPassword.requestFocus();
                }
                CheckUserIntent(id, password);

            }
        });
    }


    private void checkLogin() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("PrefFile", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String sharedId = pref.getString("id", null);
        String sharedPost = pref.getString("post", null);
        Log.d(TAG, "sharedPost: " + sharedPost + " shareId:" + sharedId);

        if (sharedId.equals(null)) {
            Intent startIntent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(startIntent);
            finish();
        }

        if (sharedPost.equals("HR")) {
            Intent intent = new Intent(MainActivity.this, HRActivity.class);
            startActivity(intent);
        } else if (sharedPost.equals("employee")) {
            Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
            startActivity(intent);
        } else if (sharedPost.equals("watchman")) {
            Intent intent = new Intent(MainActivity.this, WatchmanActivity.class);
            startActivity(intent);
        }
    }

    private void CheckUserIntent(final String id, final String password) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(id).exists()) {
                    if (!id.isEmpty()) {
                        String passwordFromFirebase = dataSnapshot.child(id).child("password").getValue().toString();
                        if (passwordFromFirebase.equals(password)) {
                            String post = dataSnapshot.child(id).child("post").getValue().toString();

                            if (post.equals("HR")) {
                                Intent intent = new Intent(MainActivity.this, HRActivity.class);
                                intent.putExtra("id", id);
                                pref = getApplicationContext().getSharedPreferences("PrefFile", 0);
                                editor = pref.edit();
                                editor.putString("id", id);
                                editor.putString("post", post);
                                editor.apply();

                                startActivity(intent);
                            } else if (post.equals("employee")) {
                                Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
                                intent.putExtra("id", id);
                                pref = getApplicationContext().getSharedPreferences("PrefFile", 0);
                                editor = pref.edit();
                                editor.putString("id", id);
                                editor.putString("post", post);
                                editor.commit();

                                startActivity(intent);
                            } else if (post.equals("watchman")) {
                                Intent intent = new Intent(MainActivity.this, WatchmanActivity.class);
                                intent.putExtra("id", id);
                                pref = getApplicationContext().getSharedPreferences("PrefFile", 0);
                                editor = pref.edit();
                                editor.putString("id", id);
                                editor.putString("post", post);

                                editor.commit();

                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "There is some error!!", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                            /*progressDialog.dismiss();*/
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                        /*progressDialog.dismiss();*/
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                    /*progressDialog.dismiss();*/
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String error = databaseError.getMessage();
                Log.e("Error of database", error);
                Toast.makeText(MainActivity.this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                /*progressDialog.dismiss();*/
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //checkLogin();
    }
}
