package kr.wearit.android.ui.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;

import kr.wearit.android.R;

/**
 * Created by KimJS on 2016-09-07.
 */
public class WaitDialog {

    private static final String TAG = WaitDialog.class.getSimpleName();

    //
    private ProgressDialog mDialog;

    //

    public WaitDialog(Activity activity) {
        mDialog = new ProgressDialog(activity, R.style.WaitTheme);
        mDialog.setCancelable(false);
        mDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
    }

    public WaitDialog(Activity activity, DialogInterface.OnCancelListener listener) {
        mDialog = new ProgressDialog(activity, R.style.WaitTheme);
        mDialog.setCancelable(true);
        mDialog.setOnCancelListener(listener);
        mDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
    }

    //

    public void show() {
        //if (Util.isInvalid(mDialog.getOwnerActivity()))
        //	return;

        try {
            mDialog.show();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public void hide() {
        //if (Util.isInvalid(mDialog.getOwnerActivity()))
        //	return;

        try {
            mDialog.dismiss();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
