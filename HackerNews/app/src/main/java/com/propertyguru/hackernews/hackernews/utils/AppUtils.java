package com.propertyguru.hackernews.hackernews.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.propertyguru.hackernews.hackernews.R;
import com.propertyguru.hackernews.hackernews.interfaces.IDialogCallback;
import com.propertyguru.hackernews.hackernews.ui.HackerNewsApplication;

/**
 * Created by Uba
 */

public class AppUtils {
    public static boolean isNetworkConnected() {
return false;
    }

    //Show LONG Toast to User
    public static void showToast(final String text) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(HackerNewsApplication.getsInstance(), text, Toast.LENGTH_LONG).show();
            }
        }, 0);

    }

    /**
     * Show Alert DIALOG
     */
    public static void showAlertMessage(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.getResources().getString(R.string.ok_string), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(true).show();
    }

    public static void showAlertMessage(Context context, String title, String message, String okText, final IDialogCallback callback) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(okText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) {
                            callback.onPositiveClick(null);
                        }
                        dialog.dismiss();
                    }
                }).setCancelable(false).show();
    }

    public static void showAlertMessage(Context context, String title, String message, String okText, String cancelText, final IDialogCallback callback) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(okText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) {
                            callback.onPositiveClick(null);
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    callback.onNegativeClick(null);
                }
                dialog.dismiss();
            }
        }).setCancelable(false).show();
    }
}
