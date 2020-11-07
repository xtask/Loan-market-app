package com.market.loan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.market.loan.R;
import com.market.loan.bean.Duration;
import com.market.loan.bean.Limit;
import com.market.loan.bean.ProductResult;
import com.market.loan.bean.Result;
import com.market.loan.bean.UserResult;
import com.market.loan.constant.Status;
import com.market.loan.core.ConfigCache;
import com.market.loan.model.MyProfileViewModel;
import com.market.loan.model.PayViewModel;
import com.market.loan.tools.AmountFormat;

import java.util.List;

public class PayActivity extends AppCompatActivity {

    List<Limit> limits;
    List<Duration> durations;
    private PayViewModel payViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        payViewModel = new ViewModelProvider(this).get(PayViewModel.class);

        final AppCompatSeekBar seekBarAmount = findViewById(R.id.seekBarAmount);
        final AppCompatSeekBar seekBarMonths = findViewById(R.id.seekBarMonths);
        final AppCompatTextView payAmount = findViewById(R.id.payAmount);
        final AppCompatTextView payMonths = findViewById(R.id.payMonths);
        payViewModel.getProductResult().observe(this, new Observer<Result<ProductResult>>() {
            @Override
            public void onChanged(Result<ProductResult> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    limits = result.getData().getLimits();
                    seekBarAmount.setMax(limits.size() - 1);
                }
            }
        });
        payViewModel.request();
        seekBarAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Limit limit = limits.get(progress);
                payAmount.setText(AmountFormat.format(limit.getAmount()));
                durations = limit.getDurations();
                seekBarMonths.setMax(durations.size() - 1);
                seekBarMonths.setProgress(0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        seekBarMonths.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Duration duration = durations.get(progress);
                payMonths.setText(duration.getDuration());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}