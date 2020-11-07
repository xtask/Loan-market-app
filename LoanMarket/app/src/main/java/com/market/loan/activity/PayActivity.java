package com.market.loan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
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
    private MyProfileViewModel myProfileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        payViewModel = new ViewModelProvider(this).get(PayViewModel.class);
        myProfileViewModel = new ViewModelProvider(this).get(MyProfileViewModel.class);

        final AppCompatSeekBar seekBarAmount = findViewById(R.id.seekBarAmount);
        final AppCompatSeekBar seekBarMonths = findViewById(R.id.seekBarMonths);
        final AppCompatTextView payAmount = findViewById(R.id.payAmount);
        final AppCompatTextView payMonths = findViewById(R.id.payMonths);
        final AppCompatTextView amountOld = findViewById(R.id.amountOld);
        final AppCompatTextView amountNew = findViewById(R.id.amountNew);

        final AppCompatTextView loanTerm = findViewById(R.id.loanTerm);
        final AppCompatTextView loanInterest = findViewById(R.id.loanInterest);
        final AppCompatTextView monthlyPayment = findViewById(R.id.monthlyPayment);
        final AppCompatTextView monthlyPrincipal = findViewById(R.id.monthlyPrincipal);
        final AppCompatTextView monthlyInerest = findViewById(R.id.monthlyInerest);
        final AppCompatTextView bankCardNumber = findViewById(R.id.bankCardNumber);

        final AppCompatImageButton goneViewBtn = findViewById(R.id.goneViewBtn);
        final AppCompatImageButton goneViewBtnGone = findViewById(R.id.goneViewBtnGone);
        final LinearLayoutCompat detailsMain = findViewById(R.id.detailsMain);
        final LinearLayoutCompat detailsList = findViewById(R.id.detailsList);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.goneViewBtn){
                    detailsMain.setVisibility(View.VISIBLE);
                    detailsList.setVisibility(View.GONE);
                }else if(id == R.id.goneViewBtnGone){
                    detailsMain.setVisibility(View.GONE);
                    detailsList.setVisibility(View.VISIBLE);
                }

            }
        };
        goneViewBtn.setOnClickListener(clickListener);
        goneViewBtnGone.setOnClickListener(clickListener);

        payViewModel.getProductResult().observe(this, new Observer<Result<ProductResult>>() {
            @Override
            public void onChanged(Result<ProductResult> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    ProductResult data = result.getData();
                    limits = data.getLimits();
                    seekBarAmount.setMax(limits.size() - 1);
                }
            }
        });

        myProfileViewModel.getUserResult().observe(this, new Observer<Result<UserResult>>() {
            @Override
            public void onChanged(Result<UserResult> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    bankCardNumber.setText(result.getData().getBankAccountNo());
                }
            }
        });
        payViewModel.request();
        myProfileViewModel.request();
        seekBarAmount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Limit limit = limits.get(progress);
                payAmount.setText(AmountFormat.format(limit.getAmount()));
                durations = limit.getDurations();
                seekBarMonths.setMax(durations.size() - 1);
                if (seekBarMonths.getProgress() == 0) {
                    payMonths.setText(durations.get(0).getDuration());
                    amountOld.setText(durations.get(0).getMemberOriFee());
                    amountNew.setText(durations.get(0).getMemberFee());
                    loanTerm.setText(durations.get(0).getDuration());
                    loanInterest.setText(durations.get(0).getInterest());
                    monthlyPayment.setText(durations.get(0).getMonthlyPayment());
                    monthlyPrincipal.setText(durations.get(0).getMonthlyPrincipal());
                    monthlyInerest.setText(durations.get(0).getMonthlyInerest());
                } else {
                    seekBarMonths.setProgress(0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekBarMonths.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Duration duration = durations.get(progress);
                payMonths.setText(duration.getDuration());
                amountOld.setText(duration.getMemberOriFee());
                amountNew.setText(duration.getMemberFee());

                loanTerm.setText(duration.getDuration());
                loanInterest.setText(duration.getInterest());
                monthlyPayment.setText(duration.getMonthlyPayment());
                monthlyPrincipal.setText(duration.getMonthlyPrincipal());
                monthlyInerest.setText(duration.getMonthlyInerest());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}