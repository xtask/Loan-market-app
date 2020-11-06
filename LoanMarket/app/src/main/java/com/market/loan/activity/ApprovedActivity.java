package com.market.loan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.market.loan.R;
import com.market.loan.adapter.MainMarqueeRecyclerViewAdapter;
import com.market.loan.bean.ConfigResult;
import com.market.loan.bean.MarqueeResult;
import com.market.loan.bean.Result;
import com.market.loan.constant.Status;
import com.market.loan.model.ApprovedViewModel;

import java.util.List;

public class ApprovedActivity extends AppCompatActivity {

    private ApprovedViewModel approvedViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved);
        approvedViewModel = new ViewModelProvider(this).get(ApprovedViewModel.class);

        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        String result = profile.getString("configResult", null);
        AppCompatTextView approvedText = findViewById(R.id.approvedText);
        if (result != null) {
            ConfigResult configResult = JSON.parseObject(result, ConfigResult.class);
            approvedText.setText(configResult.getTipsCongratulations());
        }

        approvedViewModel.getMarqueeResult().observe(this, new Observer<Result<List<MarqueeResult>>>() {
            @Override
            public void onChanged(Result<List<MarqueeResult>> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    loadMarquee(result.getData());
                }
            }

            private void loadMarquee(List<MarqueeResult> marqueeResults) {
                RecyclerView marqueeListView = findViewById(R.id.marqueeList);
                marqueeListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                MainMarqueeRecyclerViewAdapter adapter = new MainMarqueeRecyclerViewAdapter(ApprovedActivity.this, marqueeResults, R.layout.activity_approved_list);
                marqueeListView.setAdapter(adapter);
            }
        });
        approvedViewModel.getMarquee();
    }
}