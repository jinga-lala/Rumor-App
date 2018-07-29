package com.hfad.layoutprac;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hfad.layoutprac.Database.DatabaseHandler;

public class Splash extends AppCompatActivity {
    SharedPreferences data;
    String userDataString = "";
    String username = "";
    private static final int PERMISSION_REQUEST_CODE = 1;
    String useremail = "";
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (check_permission()) {// checking permission for phone.whenever we have to access db in the phone taki iska koi b file phone may write kr ske
            db = new DatabaseHandler(this);
        }
        data = getSharedPreferences("rumour", MODE_PRIVATE);
        userDataString = data.getString("userphone", "");
        Log.d("Splash", "the value of phone data is " + userDataString);
        username = data.getString("user", "");
        Log.d("Splash", "the value of name data is " + username);
        useremail = data.getString("email", "");
        Log.d("Splash", "the value of email data is " + useremail);
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (username.equals("")) {
                        Intent intent = new Intent(Splash.this, signup.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Splash.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        };
        timerThread.start();

    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
    boolean check_permission() {

        int result = ContextCompat.checkSelfPermission(Splash.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {
            requestPermission();
            return false;
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(Splash.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Write External Storage permission allows Reckon to save data l" +
                    "ocally. Please allow in App Settings for better offline experience.")
                    .setCancelable(false)

                    .setNegativeButton("Enable Access",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(Splash.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

                                }
                            });

            // Creating dialog box
            AlertDialog alert = builder.create();
            alert.show();

        } else {

            ActivityCompat.requestPermissions(Splash.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

}