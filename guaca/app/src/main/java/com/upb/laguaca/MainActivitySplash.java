package com.upb.laguaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

public class MainActivitySplash extends AppCompatActivity {
private Window ventana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);
        getSupportActionBar().hide();
        ventana=getWindow();
        ventana.setStatusBarColor(Color.parseColor("#38020B"));




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent intent=new Intent(MainActivitySplash.this,MainActivity.class);
               startActivity(intent);
               finish();
            }
        },4800);
    }
}