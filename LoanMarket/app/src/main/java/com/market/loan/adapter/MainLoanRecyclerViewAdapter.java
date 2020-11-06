package com.market.loan.adapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.market.loan.R;
import com.market.loan.bean.Duration;
import com.market.loan.bean.Limit;
import com.market.loan.tools.AmountFormat;

import java.util.List;

public class MainLoanRecyclerViewAdapter extends RecyclerView.Adapter<MainLoanRecyclerViewAdapter.ViewHolder> {

    List<Limit> values;
    Context content;

    public MainLoanRecyclerViewAdapter(Context context, List<Limit> limits) {
        this.values = limits;
        this.content = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(content).inflate(R.layout.activity_main_list_1, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Limit limit = values.get(position);
        holder.amountText.setText(AmountFormat.format(limit.getAmount()));
        List<Duration> durations = limit.getDurations();
        holder.daysText.setText(durations.get(0).getDuration().split(" ")[0] + "-" + durations.get(durations.size() - 1).getDuration());

        View.OnClickListener onClickListener = new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                // todo
                Log.d(ContentValues.TAG, "销毁了:" + limit.toString());
            }
        };
        holder.layout.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView amountText;
        AppCompatTextView daysText;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amountText = itemView.findViewById(R.id.loanAmount);
            daysText = itemView.findViewById(R.id.loanDays);
            daysText = itemView.findViewById(R.id.loanDays);
            layout = itemView.findViewById(R.id.loanListItem);
        }
    }
}

