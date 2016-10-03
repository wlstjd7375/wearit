package kr.wearit.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class DBHelper {

    private static final String DATABASE_NAME = "wearit.db";

    private static final int DATABASE_VERSION = 1;


    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    public DBHelper(Context context){
        this.mCtx = context;
    }

    public DBHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDB.close();
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            /*
            //DB TABLE CREATE SAMPLE
            db.execSQL(MynahDB.CreateDB._CREATE_USER_TABLE);
            */

            db.execSQL(WearitDB.CreateDB._CREATE_SEARCH_INFO_TABLE);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


            db.execSQL("DROP TABLE IF EXISTS " + WearitDB._SEARCH_INFO_TABLE_NAME);

            onCreate(db);
        }
    }
}
