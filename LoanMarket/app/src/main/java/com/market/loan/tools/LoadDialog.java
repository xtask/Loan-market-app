package com.market.loan.tools;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadDialog extends ProgressDialog {

    public LoadDialog(Context context) {
        super(context);
    }

    public void show(String message) {
        this.setMessage(message);
        this.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.show();
    }

    public void hide() {
        this.dismiss();
    }
}
