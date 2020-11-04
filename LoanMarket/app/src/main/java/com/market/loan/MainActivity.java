package com.market.loan;

import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends NoBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
//        Objects.requireNonNull(getSupportActionBar()).hide();

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // read SharedPreferences
        SharedPreferences sp = getSharedPreferences("start_config", MODE_PRIVATE);
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            String tmp = sp.getString("", null);
            if (tmp != null) {

            } else {
                editor.putString("name", "Liu");
                editor.putInt("age", 8);
                editor.apply();
            }
        } else {

        }
    }
}