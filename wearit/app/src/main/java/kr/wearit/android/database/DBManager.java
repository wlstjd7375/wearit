package kr.wearit.android.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import kr.wearit.android.model.SearchWord;

public class DBManager {

    private static DBManager instance;

    private DBHelper dbh;
    private static String TAG = "DBManager";

    private static SimpleDateFormat defaultDateFormat;
    private static SimpleDateFormat printDateFormat;

    // android 생성자
    private DBManager(Context context) {

        dbh = new DBHelper(context);
        try {
            dbh.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DBManager getManager(Context context) {
        if (instance == null) {
            instance = new DBManager(context);
            defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            printDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        return instance;
    }

    //Search Info
    //새 세션 추가
    public synchronized void insertSearchWord (SearchWord searchInfo) {
        String sql = "insert into " +
                WearitDB._SEARCH_INFO_TABLE_NAME +
                "(" +
                WearitDB._SEARCH_KEYWORD +
                ", " +
                WearitDB._SEARCH_DATE +
                ") " +
                "values " +
                "(" +
                "'" + searchInfo.getSearchWord() + "', " +
                "'" + searchInfo.getDate() + "'" +
                "); ";

        dbh.mDB.execSQL(sql);
        Log.d(TAG, "insertSearchWord 완료");
    }

    public synchronized SearchWord getSearchWord(int key) {
        SearchWord searchWord = new SearchWord();

        String sql = "select * from "
                + WearitDB._SEARCH_INFO_TABLE_NAME
                + " where "
                + WearitDB._SEARCH_KEY
                + " = "
                + key
                + "; ";
        Cursor c = dbh.mDB.rawQuery(sql, null);

        if (c != null && c.getCount() != 0)
            c.moveToFirst();

        if (c.getCount() == 0)
            return searchWord; // error?

        int wordKey = c.getColumnIndex(WearitDB._SEARCH_KEY);
        int word = c.getColumnIndex(WearitDB._SEARCH_KEYWORD);
        int date = c.getColumnIndex(WearitDB._SEARCH_DATE);

        searchWord.setKey(c.getInt(wordKey));
        searchWord.setSearchWord(c.getString(word));
        searchWord.setDate(c.getString(date));

        c.close();
        Log.d(TAG, "getSearchWord 완료");
        return searchWord;
    }

    public synchronized ArrayList<SearchWord> getALLSearchWord(){
        ArrayList<SearchWord> wordList = new ArrayList<SearchWord>();

        String sql = "select * from "
                + WearitDB._SEARCH_INFO_TABLE_NAME
                + " order by " + WearitDB._SEARCH_KEY + " desc"  + " ;";

        Cursor c = dbh.mDB.rawQuery(sql, null);
        if (c != null && c.getCount() != 0)
            c.moveToFirst();

        SearchWord searchWord;

        int wordKey = c.getColumnIndex(WearitDB._SEARCH_KEY);
        int word = c.getColumnIndex(WearitDB._SEARCH_KEYWORD);
        int date = c.getColumnIndex(WearitDB._SEARCH_DATE);


        while (!c.isAfterLast()) {
            searchWord = new SearchWord();

            searchWord.setKey(c.getInt(wordKey));
            String insert = "";
            try {
                insert = URLDecoder.decode(c.getString(word), "utf-8");
                Log.d("dd", "decode : " + insert);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            searchWord.setSearchWord(insert);
            searchWord.setDate(c.getString(date));

            wordList.add(searchWord);
            c.moveToNext();
        }

        Log.i(TAG, "getAllWord 완료");
        c.close();
        return wordList;
    }

    public void deleteAllSearchWord(){
        String sql = "delete from " + WearitDB._SEARCH_INFO_TABLE_NAME
                + " ;";
        dbh.mDB.execSQL(sql);
        Log.d(TAG, "deleteAllSearchWord 완료");
    }

}
