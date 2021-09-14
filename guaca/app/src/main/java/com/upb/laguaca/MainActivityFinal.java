package com.upb.laguaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;

public class MainActivityFinal extends AppCompatActivity {

    private Window ventana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_final);

        getSupportActionBar().hide();
        ventana=getWindow();
        ventana.setStatusBarColor(Color.parseColor("#38020B"));


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivityFinal.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);


    }
}