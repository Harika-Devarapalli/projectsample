package com.example.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.view.WindowManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class LoadingDialog {


    private Activity activity;
    private AlertDialog dialog;


    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void load(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setView(activity.getLayoutInflater().inflate(R.layout.custom_loading_dialog,null))
                .setCancelable(false);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 140);
        dialog.getWindow().setBackgroundDrawable(inset);
        dialog.show();
    }


    public void load2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setView(activity.getLayoutInflater().inflate(R.layout.custom_loading_dialogg,null))
                .setCancelable(false);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 140);
        dialog.getWindow().setBackgroundDrawable(inset);
        dialog.show();
    }

    public void dismisss(){
        dialog.dismiss();
    }
}
