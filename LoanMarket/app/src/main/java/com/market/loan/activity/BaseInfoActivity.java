package com.market.loan.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.market.loan.R;
import com.market.loan.adapter.DialogItemAdapter;
import com.market.loan.bean.ConfigData;
import com.market.loan.bean.ConfigResult;

import java.time.Year;
import java.util.Calendar;
import java.util.List;

public class BaseInfoActivity extends AppCompatActivity {

    List<ConfigData> dicts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_info);
        SharedPreferences profile = getSharedPreferences("basic_profile", MODE_PRIVATE);
        String result = profile.getString("configResult", null);
        if (result != null) {
            ConfigResult configResult = JSON.parseObject(result, ConfigResult.class);
            dicts = configResult.getDicts();
        }
        final AppCompatEditText birthday = findViewById(R.id.baseBirthday);
        final AppCompatEditText gender = findViewById(R.id.baseGender);
        final DialogInterface.OnClickListener pickClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // todo
                Log.d(ContentValues.TAG, "销毁了:");
            }
        };


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.baseBirthday) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            birthday.setText(year + "-" + month + "-" + dayOfMonth);
                        }
                    };
                    DatePickerDialog dialog = new DatePickerDialog(
                            BaseInfoActivity.this,
                            dateSetListener,
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );
                    dialog.show();
                } else if (id == R.id.baseGender) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BaseInfoActivity.this, R.style.TransparentDialog).setTitle(gender.getText())
                            .setSingleChoiceItems(new DialogItemAdapter(BaseInfoActivity.this, dicts, R.layout.list_view_item), -1, pickClickListener);
                    builder.create().show();
                }
            }
        };
        birthday.setOnClickListener(onClickListener);
        gender.setOnClickListener(onClickListener);
    }
}