package com.example.jump;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import Views.Jump_View;

public class MainActivity extends AppCompatActivity {
    Jump_View jump_view;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jump_view=new Jump_View(MainActivity.this);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MainActivity.this,Home_activity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}