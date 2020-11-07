package com.market.loan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.market.loan.MainActivity;
import com.market.loan.R;
import com.market.loan.adapter.MainLoanRecyclerViewAdapter;
import com.market.loan.adapter.PayEndRecyclerViewAdapter;
import com.market.loan.bean.Limit;
import com.market.loan.bean.ProductResult;
import com.market.loan.bean.Result;
import com.market.loan.bean.Vip;
import com.market.loan.constant.Status;
import com.market.loan.core.ConfigCache;
import com.market.loan.model.MainViewModel;

import java.util.List;

public class PayEndActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    PayEndRecyclerViewAdapter adapter;
    List<Vip> vips = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_end);

        AppCompatImageButton moreLoan = findViewById(R.id.getMoreLoan);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getProductResult().observe(this, new Observer<Result<ProductResult>>() {
            @Override
            public void onChanged(Result<ProductResult> result) {
                if (result.getStatus() == Status.SUCCESS_CODE) {
                    vips = result.getData().getViplist();
                    List<Vip> vipsTemp = null;
                    if (vips.size() > 4) {
                        vipsTemp = vips.subList(0, 4);
                    }
                    loadLoan(vipsTemp);
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });

        mainViewModel.getProduct();


        moreLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Vip> values = adapter.getValues();
                int size = values.size();
                adapter.setValues(vips);
                adapter.notifyItemRangeInserted(size, vips.size() - size);
            }
        });
    }

    private void loadLoan(List<Vip> vipList) {
        RecyclerView loanListView = findViewById(R.id.payEndLoanList);
        loanListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter = new PayEndRecyclerViewAdapter(PayEndActivity.this, vipList, R.layout.activity_pay_list);
        loanListView.setAdapter(adapter);
    }
}