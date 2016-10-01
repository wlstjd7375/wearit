package kr.wearit.android.view.check;

import android.util.Log;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import kr.wearit.android.view.BaseActivity;

/**
 * Created by KimJS on 2016-10-01.
 */

//Date Time Picker Dialog 기능을 하게 해줌
public class CheckBaseActivity extends BaseActivity implements
        View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private String TAG = "CheckBaseActivity##";

    private DatePickerDialog  dpd;


    public void showDialog() {
        //현재 날짜를 기준으로 dialog 생성
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                CheckBaseActivity.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        );

        //세팅


        //show
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }



    //Callback 함수
    @Override
    public void onClick(View v) {
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Log.d(TAG, date);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
    }
}
