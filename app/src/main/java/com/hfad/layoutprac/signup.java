package com.hfad.layoutprac;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    public void openOptions(View view){
        Intent intent= new Intent(signup.this,fb.class);
        startActivity(intent);
    }
}
