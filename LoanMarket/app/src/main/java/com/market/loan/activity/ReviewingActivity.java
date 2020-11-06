package com.market.loan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.market.loan.R;
import com.market.loan.bean.ConfigResult;

public class ReviewingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewing);

        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        String result = profile.getString("configResult", null);
        AppCompatTextView reviewingText = findViewById(R.id.reviewingText);
        if (result != null) {
            ConfigResult configResult = JSON.parseObject(result, ConfigResult.class);
            reviewingText.setText(configResult.getTipsProcessing());
        }

    }
}