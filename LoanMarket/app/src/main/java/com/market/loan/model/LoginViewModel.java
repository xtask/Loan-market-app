package com.market.loan.model;

import android.text.Editable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.market.loan.bean.LoginResult;
import com.market.loan.bean.Result;
import com.market.loan.constant.Apis;
import com.market.loan.http.HttpRequest;
import com.market.loan.http.adapter.CallbackAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginViewModel extends ViewModel {

    public LoginViewModel() {

    }

    private MutableLiveData<Result<LoginResult>> loginResult;

    public LiveData<Result<LoginResult>> getLoginResult() {
        if (loginResult == null) {
            loginResult = new MutableLiveData<>();
        }
        return loginResult;
    }

    public void login(String mobile, String code) {
        JSONObject json = new JSONObject();
        try {
            json.put("mobile", mobile);
            json.put("code", code);
        } catch (JSONException ignored) {
        }
        Call call = new HttpRequest().request(Apis.LOGIN_URI, RequestBody.create(HttpRequest.JSON, String.valueOf(json)));
        call.enqueue(new CallbackAdapter() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                Result<LoginResult> result = new Result<>("网络请求失败");
                LoginViewModel.this.loginResult.postValue(result);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                String resultBody = response.body().string();
                Result<LoginResult> result = JSON.parseObject(resultBody, new TypeReference<Result<LoginResult>>(){});
                LoginViewModel.this.loginResult.postValue(result);
            }
        });
    }

}
