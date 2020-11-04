package com.market.loan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.market.loan.activity.FeedbackActivity;
import com.market.loan.activity.LoginActivity;
import com.market.loan.activity.PayActivity;
import com.market.loan.activity.PayEndActivity;

public class StartActivity extends NoBarActivity {

    SharedPreferences config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_start);
        super.onCreate(savedInstanceState);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // is login
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        thread.start();
    }
}