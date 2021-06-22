package com.example.southplatform.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;


/**
 * Dialog 工具类
 */
public class DialogUtil {


    static ProgressDialog progressDlg = null;

    public static void showDialogWait(Context ctx, String strMessage) {
        if (null == progressDlg) {
            if (ctx == null) return;
            progressDlg = new ProgressDialog(ctx);
            //设置进度条样式
            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //提示的消息
            progressDlg.setMessage(strMessage);
            progressDlg.setIndeterminate(false);
            progressDlg.setCancelable(true);
            progressDlg.setCanceledOnTouchOutside(false);
            progressDlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            progressDlg.show();
        }
    }

    public static void showDialogWait1(Context ctx, String strMessage) {
        if (ctx == null) return;
        progressDlg = new ProgressDialog(ctx);
        //提示的消息
        progressDlg.setMessage(strMessage);
        progressDlg.setCanceledOnTouchOutside(false);
        progressDlg.show();
//            //设置进度条样式
//            progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);

//            progressDlg.setIndeterminate(false);
//            progressDlg.setCancelable(true);

//            progressDlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//            progressDlg.show();
    }

    private static Dialog dialog;

    /**
     * 结束进度条
     */
    public static void stopProgressDlg() {
        if (null != progressDlg && progressDlg.isShowing()) {
            progressDlg.dismiss();
            progressDlg = null;
        }
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }


}
