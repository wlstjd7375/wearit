package kr.wearit.android.util;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class TextUtil {

	public static DecimalFormat createPriceFormat() {
		return new DecimalFormat("#,##0");
	}

	public static CharSequence formatPriceSymbol(int price) {
		return createPriceFormat().format(price) + Currency.getInstance(Locale.KOREA).getSymbol();
	}
	public static CharSequence formatPriceWon(int price) {
		return createPriceFormat().format(price) + "원";
	}

	@SuppressLint("SimpleDateFormat")
	public static SimpleDateFormat createDateFormat() {
		return new SimpleDateFormat("yyyy.MM.dd");
	}

	@SuppressLint("SimpleDateFormat")
	public static SimpleDateFormat createNoticeDateFormat() {
		return new SimpleDateFormat("yy.MM.dd");
	}

	public static CharSequence formatDate(Date date) {
		return createDateFormat().format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static CharSequence formatStyleDate(Date date) {
		return new SimpleDateFormat("yyyy.M.d").format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static SimpleDateFormat createTimeFormat() {
		return new SimpleDateFormat("HH:mm");
	}

	public static CharSequence formatTime(Date date) {
		return createTimeFormat().format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static SimpleDateFormat createMessageTimeFormat() {
		return new SimpleDateFormat("a h:mm");
	}

	public static String formatMessageTime(Date date) {
		return createMessageTimeFormat().format(date);
	}

	public static String nvl(String value1, String value2) {
		return value1 != null ? value1 : value2;
	}

	@SuppressLint("SimpleDateFormat")
	public static CharSequence formatDateForMessage(Date date) {
		return new SimpleDateFormat(DateUtil.isToday(date) ? "a h:mm" : "yyyy.MM.dd").format(date);
	}

    public static String getPaytype(String paytype) {
        String result = "";
        if (paytype.equals("account")){
            result = "무통장입금";
        }
        else if (paytype.equals("card")){
            result = "카드결제";
        }
        else if (paytype.equals("phone")){
            result = "핸드폰결제";
        }
        else if (paytype.equals("later")){
            result = "현장 후 결제";
        }

        return result;
    }

	public static void applyNewLineCharacter(TextView textView)
	{
		Paint paint = textView.getPaint();
		String text = (String) textView.getText();
		int frameWidth = textView.getWidth();
		int startIndex = 0;
		int endIndex = paint.breakText(text , true, frameWidth, null);
		String save = text.substring(startIndex, endIndex);
		// Count line of TextView
		int lines = 1;

		while(true)
		{
			// Set new start index
			startIndex = endIndex;
			// Get substring the remaining of text
			text = text.substring(startIndex);

			if(text.length() == 0) break;

			else lines++;

			// Calculate end of index that fits
			endIndex = paint.breakText(text, true, frameWidth, null);
			// Append substring that fits into the frame
			save += "\n" + text.substring(0, endIndex);
		}
		// Set text to TextView
		textView.setText(save);
	}
}
