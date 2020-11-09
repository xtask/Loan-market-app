package com.market.loan;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class BaseActivity extends AppCompatActivity {

  private AlertDialog alertDialog;

  public void show() {
      alertDialog = new AlertDialog.Builder(this).create();
      Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable());
      alertDialog.setCancelable(false);
      alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
          @Override
          public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
              if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK)
                  return true;
              return false;
          }
      });
      alertDialog.show();
      alertDialog.setContentView(R.layout.loading);
      alertDialog.setCanceledOnTouchOutside(false);
  }

  public void dismiss() {
      if (null != alertDialog && alertDialog.isShowing()) {
          alertDialog.dismiss();
      }
  }
}