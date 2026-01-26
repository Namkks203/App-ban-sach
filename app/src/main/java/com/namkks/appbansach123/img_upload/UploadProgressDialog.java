package com.namkks.appbansach123.img_upload;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UploadProgressDialog {

    public interface CancelListener {
        void onCancel();
    }

    private final Dialog dialog;
    private final ProgressBar progressBar;
    private final TextView textView;
    private final Button btnCancel;

    public UploadProgressDialog(Context context, CancelListener listener) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(60, 40, 60, 40);
        layout.setGravity(Gravity.CENTER);

        progressBar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        progressBar.setMax(100);

        textView = new TextView(context);
        textView.setText("Đang upload...");
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, 20, 0, 20);

        btnCancel = new Button(context);
        btnCancel.setText("Cancel");

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) {
                listener.onCancel();
            }
        });

        layout.addView(progressBar,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        layout.addView(textView);
        layout.addView(btnCancel);

        dialog.setContentView(layout);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void update(int percent) {
        progressBar.setProgress(percent);
        textView.setText("Đang tải: " + percent + "%");
    }

    public void error(String msg) {
        textView.setText("Lỗi: " + msg);
    }
}
