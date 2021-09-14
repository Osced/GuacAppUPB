package com.upb.laguaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivityBienvenida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_bienvenida);

        Intent intent=new Intent(MainActivityBienvenida.this,MainActivitySplash.class);
        startActivity(intent);
        finish();
    }
}