package com.market.loan.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.market.loan.R;
import com.market.loan.adapter.DialogItemAdapter;
import com.market.loan.bean.BaseRequest;
import com.market.loan.bean.ConfigData;
import com.market.loan.bean.ConfigResult;
import com.market.loan.tools.DictsFilter;

import java.util.Calendar;
import java.util.List;

public class BaseInfoActivity extends AppCompatActivity {

    List<ConfigData> dicts;
    List<ConfigData> genderDicts;
    List<ConfigData> educationDicts;
    List<ConfigData> maritalDicts;

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
        final AppCompatEditText education = findViewById(R.id.baseEducation);
        final AppCompatEditText marital = findViewById(R.id.baseMarital);
        final AppCompatEditText baseNext = findViewById(R.id.baseNext);

        final BaseRequest baseRequest = new BaseRequest();
        final DialogInterface.OnClickListener genderClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = genderDicts.get(which);
                gender.setText(data.getName());
                baseRequest.setGender(data.getId());
                dialog.cancel();
            }
        };

        final DialogInterface.OnClickListener educationClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = educationDicts.get(which);
                education.setText(data.getName());
                baseRequest.setEducation(data.getId());
                dialog.cancel();
            }
        };

        final DialogInterface.OnClickListener maritalClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfigData data = maritalDicts.get(which);
                marital.setText(data.getName());
                baseRequest.setMarital(data.getId());
                dialog.cancel();
            }
        };


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.baseBirthday) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String birthdayValue = year + "-" + month + "-" + dayOfMonth;
                            birthday.setText(birthdayValue);
                            baseRequest.setMarital(birthdayValue);
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
                    genderDicts = new DictsFilter(dicts).getDictsByType("Gender");
                    AlertDialog.Builder builder = new AlertDialog.Builder(BaseInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(BaseInfoActivity.this, genderDicts, R.layout.list_view_item), -1, genderClickListener);
                    builder.create().show();
                } else if (id == R.id.baseEducation) {
                    educationDicts = new DictsFilter(dicts).getDictsByType("Education");
                    AlertDialog.Builder builder = new AlertDialog.Builder(BaseInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(BaseInfoActivity.this, educationDicts, R.layout.list_view_item), -1, educationClickListener);
                    builder.create().show();
                } else if (id == R.id.baseMarital) {
                    maritalDicts = new DictsFilter(dicts).getDictsByType("Marital");
                    AlertDialog.Builder builder = new AlertDialog.Builder(BaseInfoActivity.this, R.style.TransparentDialog)
                            .setSingleChoiceItems(new DialogItemAdapter(BaseInfoActivity.this, maritalDicts, R.layout.list_view_item), -1, maritalClickListener);
                    builder.create().show();
                } else if (id == R.id.baseNext) {
                    baseRequest.setName("");
                    baseRequest.setEmail("");
                    String result = baseRequest.checked();
                    if (result != null) {
                        Toast.makeText(BaseInfoActivity.this, result, Toast.LENGTH_SHORT).show();
                    } else {

                    }
                }
            }
        };
        birthday.setOnClickListener(onClickListener);
        gender.setOnClickListener(onClickListener);
        education.setOnClickListener(onClickListener);
        marital.setOnClickListener(onClickListener);
        baseNext.setOnClickListener(onClickListener);
    }
}