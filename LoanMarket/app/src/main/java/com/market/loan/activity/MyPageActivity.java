package com.market.loan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.market.loan.R;

public class MyPageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        final AppCompatButton myProfile = findViewById(R.id.myProfile);
        final AppCompatButton myFeedBack = findViewById(R.id.myFeedBack);
        final AppCompatButton myCustomer = findViewById(R.id.myCustomer);
        final AppCompatButton myAboutUs = findViewById(R.id.myAboutUs);
        final AppCompatButton myLogOut = findViewById(R.id.myLogOut);

        View.OnClickListener onClickListener = new View.OnClickListener() {

            Class<?> activityClass;
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.myProfile) {
                    activityClass = MyProfileActivity.class;
                } else if (id == R.id.myFeedBack) {

                } else if (id == R.id.myCustomer) {

                } else if (id == R.id.myAboutUs) {

                } else if (id == R.id.myLogOut) {

                }
                Intent intent = new Intent(getApplicationContext(), activityClass);
                startActivity(intent);
                finish();
            }
        };
        myProfile.setOnClickListener(onClickListener);
        myFeedBack.setOnClickListener(onClickListener);
        myCustomer.setOnClickListener(onClickListener);
        myAboutUs.setOnClickListener(onClickListener);
        myLogOut.setOnClickListener(onClickListener);
    }
}