package kr.wearit.android.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;

import kr.wearit.android.App;
import kr.wearit.android.Config;

/**
 * Created by KimJS on 2016-09-07.
 */
public class Util {

    private static final String TAG = Util.class.getSimpleName();
    private static final boolean LOG = Config.LOG;

    private static final int EXTRA_DEFAULT_INTEGER = -1;


    public static Integer getIntegerExtra(Intent intent, String name) {
        int value = intent.getIntExtra(name, EXTRA_DEFAULT_INTEGER);

        return value == EXTRA_DEFAULT_INTEGER ? null : value;
    }

    public static int convertDP2PX(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static String formatWon(int price) {
        return String.format(new DecimalFormat("#,##0").format(price));
    }

    public static String encodeQueryString(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage(), e);

            throw new AssertionError();
        }
    }

    public static void openBrowser(Activity activity, String url) {
        if (TextUtils.isEmpty(url))
            return;

        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(activity, "주소가 이상합니다 (" + url + ")", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isNew(Date date) {
        return (new Date().getTime() - date.getTime()) <= 48 * 60 * 60 * 1000;
    }

    //

    public static int writeInteger(Integer value) {
        return value != null ? value : Integer.MIN_VALUE;
    }

    public static Integer readInteger(int value) {
        return value != Integer.MIN_VALUE ? value : null;
    }

    public static CharSequence getPhoneNumber() {
        String phone = ((TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();

        if (phone == null)
            return null;

        if (phone.startsWith("+821"))
            return "0" + phone.substring(3);

        // PhoneNumberUtils.

        return phone;
    }

    //

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isValid(Activity activity) {
        if (activity == null)
            return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            if (activity.isDestroyed())
                return false;

        if (activity.isFinishing())
            return false;

        return true;
    }

    public static boolean isInvalid(Activity activity) {
        return !isValid(activity);
    }
}
