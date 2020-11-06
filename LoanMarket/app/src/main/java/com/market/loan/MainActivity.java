package com.market.loan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.market.loan.activity.ApprovedActivity;
import com.market.loan.activity.BankInfoActivity;
import com.market.loan.activity.BaseInfoActivity;
import com.market.loan.activity.LoginActivity;
import com.market.loan.activity.PayActivity;
import com.market.loan.activity.PayEndActivity;
import com.market.loan.activity.ReviewingActivity;
import com.market.loan.activity.WorkInfoActivity;
import com.market.loan.adapter.MainLoanRecyclerViewAdapter;
import com.market.loan.adapter.MainMarqueeRecyclerViewAdapter;
import com.market.loan.bean.Limit;
import com.market.loan.bean.MarqueeResult;
import com.market.loan.bean.ProductResult;
import com.market.loan.bean.Result;
import com.market.loan.bean.enums.Certification;
import com.market.loan.bean.enums.Phase;
import com.market.loan.constant.Status;
import com.market.loan.model.MainViewModel;

import java.util.List;

public class MainActivity extends NoBarActivity {

    private MainViewModel mainViewModel;

    String phase;
    String certification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        AppCompatImageButton moreLoanButton = findViewById(R.id.moreLoan);
        moreLoanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectorActivity();
            }
        });

        mainViewModel.getProductResult().observe(this, new Observer<Result<ProductResult>>() {
            @Override
            public void onChanged(Result<ProductResult> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    ProductResult productResult = result.getData();
                    phase = productResult.getPhase();
                    certification = productResult.getCertification();
                    loadLoan(result.getData().getLimits());
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            private void loadLoan(List<Limit> limits) {
                RecyclerView loanListView = findViewById(R.id.loanList);
                loanListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
                MainLoanRecyclerViewAdapter adapter = new MainLoanRecyclerViewAdapter(MainActivity.this, limits);
                loanListView.setAdapter(adapter);
            }

        });

        mainViewModel.getMarqueeResult().observe(this, new Observer<Result<List<MarqueeResult>>>() {
            @Override
            public void onChanged(Result<List<MarqueeResult>> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    loadMarquee(result.getData());
                }
            }

            private void loadMarquee(List<MarqueeResult> marqueeResults) {
                RecyclerView marqueeListView = findViewById(R.id.marqueeList);
                marqueeListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                MainMarqueeRecyclerViewAdapter adapter = new MainMarqueeRecyclerViewAdapter(MainActivity.this, marqueeResults);
                marqueeListView.setAdapter(adapter);
            }
        });
        mainViewModel.getProduct();
        mainViewModel.getMarquee();
    }



    private void selectorActivity() {
        Class<?> activityClass = null;
        if (Phase.UNAUTHORIZED.toString().equals(phase)) {
            if (Certification.BASE_INFO.toString().equals(certification)) {
                activityClass = BaseInfoActivity.class;
            } else if (Certification.WORK_INFO.toString().equals(certification)) {
                activityClass = WorkInfoActivity.class;
            } else if (Certification.BANK_INFO.toString().equals(certification)) {
                activityClass = BankInfoActivity.class;
            }
        } else if (Phase.IN_REVIEW.toString().equals(phase)) {
            activityClass = ReviewingActivity.class;
        } else if (Phase.PASS_REVIEW.toString().equals(phase)) {
            SharedPreferences profile = getSharedPreferences("pay_profile", MODE_PRIVATE);
            final String FINISH_KEY = "approved_finish";
            boolean approvedFinish = profile.getBoolean(FINISH_KEY, false);
            if (approvedFinish) {
                activityClass = PayActivity.class;
            } else {
                activityClass = ApprovedActivity.class;
                SharedPreferences.Editor edit = profile.edit();
                edit.putBoolean(FINISH_KEY, true);
                edit.apply();
            }
        } else if (Phase.PAYMENT.toString().equals(phase)) {
            activityClass = PayEndActivity.class;
        }
        Intent intent = new Intent(getApplicationContext(), activityClass);
        startActivity(intent);
        if (Phase.PAYMENT.toString().equals(phase)) {
            finish();
        }
    }
}