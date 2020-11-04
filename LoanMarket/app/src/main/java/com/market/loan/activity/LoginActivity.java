package com.market.loan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.market.loan.MainActivity;
import com.market.loan.NoBarActivity;
import com.market.loan.R;
import com.market.loan.bean.LoginResult;
import com.market.loan.bean.Result;
import com.market.loan.constant.Status;
import com.market.loan.model.LoginViewModel;

import java.util.Objects;


public class LoginActivity extends NoBarActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        View loginNext = findViewById(R.id.login_next);

        final AppCompatEditText mobileEdit = findViewById(R.id.login_mobile);
        final AppCompatEditText codeEdit = findViewById(R.id.login_code);
        loginNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = Objects.requireNonNull(mobileEdit.getText()).toString();
                String code = Objects.requireNonNull(codeEdit.getText()).toString();
                loginViewModel.login(mobile, code);
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<Result<LoginResult>>() {
            @Override
            public void onChanged(Result<LoginResult> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {

                    SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
                    SharedPreferences.Editor edit = profile.edit();
                    edit.putString("token", result.getData().getToken());
                    edit.apply();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}