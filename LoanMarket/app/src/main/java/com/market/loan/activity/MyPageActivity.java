package com.market.loan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.market.loan.R;
import com.market.loan.bean.Result;
import com.market.loan.bean.UserResult;
import com.market.loan.constant.Status;
import com.market.loan.core.ConfigCache;
import com.market.loan.model.MyProfileViewModel;

public class MyPageActivity extends AppCompatActivity {

    private MyProfileViewModel myProfileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        myProfileViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);

        myProfileViewModel.getUserResult().observe(this, new Observer<Result<UserResult>>() {
            @Override
            public void onChanged(Result<UserResult> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    ConfigCache.userResult = result.getData();
                    AppCompatTextView myPageMobile = findViewById(R.id.myPageMobile);
                    myPageMobile.setText(ConfigCache.userResult.getMobile());
                }
            }
        });

        myProfileViewModel.request();
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
            }
        };
        myProfile.setOnClickListener(onClickListener);
        myFeedBack.setOnClickListener(onClickListener);
        myCustomer.setOnClickListener(onClickListener);
        myAboutUs.setOnClickListener(onClickListener);
        myLogOut.setOnClickListener(onClickListener);
    }
}