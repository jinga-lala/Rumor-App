package com.hfad.layoutprac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class fb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb);
    }
    public void fbfn(View view){

    }
    public void googlefn(View view){
    Intent i=new Intent(fb.this,for_google.class);
            startActivity(i);
    }
    public void phonefn(View view){
    Intent i =new Intent(fb.this,for_mobile.class);
    startActivity(i);
    }
}
