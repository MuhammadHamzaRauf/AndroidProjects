package com.uwr.app.servicemoduledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.uwr.app.module.Services.MyService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyService.startService(this, "Test Url", "Test Path");
    }
}
