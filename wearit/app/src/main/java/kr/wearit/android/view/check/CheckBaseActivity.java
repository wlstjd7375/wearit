package kr.wearit.android.view.check;

import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.Calendar;

import kr.wearit.android.R;
import kr.wearit.android.view.BaseActivity;

/**
 * Created by KimJS on 2016-10-01.
 */

//Date Time Picker Dialog 기능을 하게 해줌
public class CheckBaseActivity extends BaseActivity implements
        View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private String TAG = "CheckBaseActivity##";

    private DatePickerDialog  dpd;
    private TimePickerDialog tpd;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private boolean isDateTimeComplete = false;

    public void showDialog() {
        showDatePicker();
    }

    public void setDisableTime() {
        //TODO
    }

    //액티비티 onResume 에서 호출
    public String getDateTimeResult() {
        if(isDateTimeComplete) {
            return year + "-" + month + "-" + day + " " + hour + ":" + minute;
        }
        return "날짜와 시간을 선택해 주세요.";
    }

    private void showDatePicker() {
        //현재 날짜를 기준으로 dialog 생성
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                CheckBaseActivity.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        );

        //세팅
        //color
        dpd.setAccentColor(ContextCompat.getColor(this, R.color.green));
        //
        Calendar[] dates = new Calendar[3];
        for (int i = -1; i <= 1; i++) {
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DAY_OF_MONTH, i+10);
            dates[i+1] = date;
        }

        dpd.setDisabledDays(dates);
        //dpd.setHighlightedDays(dates);


        //show
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void showTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                CheckBaseActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false    //24시간 모드
        );
        tpd.setAccentColor(ContextCompat.getColor(this, R.color.green));
        /*
        Timepoint t1 = new Timepoint(3);
        Timepoint t2 = new Timepoint(4);
        Timepoint t3 = new Timepoint(6);
        Timepoint[] times = {t1, t2, t3};
        tpd.setSelectableTimes(times);
        tpd.setTimeInterval(1, 15);*/
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }



    //Callback 함수
    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear + 1;
        this.day = dayOfMonth;
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Log.d(TAG, date);

        showTimePicker();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        this.hour = hourOfDay;
        this.minute = minute;
        isDateTimeComplete = true;
        String time = "You picked the following time: "+hourOfDay+"h"+minute;
        Log.d(TAG, time);
    }
}
