package com.market.loan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.market.loan.MainActivity;
import com.market.loan.R;
import com.market.loan.adapter.MainMarqueeRecyclerViewAdapter;
import com.market.loan.bean.ConfigResult;
import com.market.loan.bean.MarqueeResult;
import com.market.loan.bean.Result;
import com.market.loan.constant.Status;
import com.market.loan.core.ConfigCache;
import com.market.loan.model.ApprovedViewModel;
import com.market.loan.tools.AmountFormat;
import com.market.loan.tools.LoadDialog;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ApprovedActivity extends AppCompatActivity {

    private ApprovedViewModel approvedViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved);
        approvedViewModel = new ViewModelProvider(this).get(ApprovedViewModel.class);

        final LoadDialog loadDialog = new LoadDialog(ApprovedActivity.this);
        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        String result = profile.getString("configResult", null);
        AppCompatTextView approvedText = findViewById(R.id.approvedText);
        AppCompatTextView approvedAmount = findViewById(R.id.approvedAmount);
        AppCompatImageButton approvedNext = findViewById(R.id.approvedNext);
        String amount = ConfigCache.amount;
//        approvedAmount.setText(AmountFormat.format(amount));
        if (result != null) {
            ConfigResult configResult = JSON.parseObject(result, ConfigResult.class);
            approvedText.setText(configResult.getTipsCongratulations());
        }

        approvedNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                startActivity(intent);
                finish();
            }
        });


        approvedViewModel.getMarqueeResult().observe(this, new Observer<Result<List<MarqueeResult>>>() {
            @Override
            public void onChanged(Result<List<MarqueeResult>> result) {
                loadDialog.hide();
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    loadMarquee(result.getData());
                } else if (result.getCode().equals(Status.ACCESS_DENIED_CODE)){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            private void loadMarquee(final List<MarqueeResult> marqueeResults) {
                final RecyclerView marqueeListView = findViewById(R.id.marqueeList);
                marqueeListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                MainMarqueeRecyclerViewAdapter adapter = new MainMarqueeRecyclerViewAdapter(ApprovedActivity.this, marqueeResults, R.layout.activity_approved_list);
                marqueeListView.setAdapter(adapter);

                final Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    int index = 0;
                    @Override
                    public void run() {
                        if (index >= marqueeResults.size()) {
                            index = 0;
                        }
                        marqueeListView.smoothScrollToPosition(index);
                        index += 2;
                    }
                }, 1000, 3000);
            }
        });
        loadDialog.show("loading...");
        approvedViewModel.getMarquee();
    }
}