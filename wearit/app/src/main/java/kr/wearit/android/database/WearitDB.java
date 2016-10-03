package kr.wearit.android.database;

import android.provider.BaseColumns;

public class WearitDB {

    //검색어 정보 테이블 컬럼
    public static final String _SEARCH_INFO_TABLE_NAME = "search_info";
    public static final String _SEARCH_KEY = "search_key";
    public static final String _SEARCH_KEYWORD = "search_keyword";
    public static final String _SEARCH_DATE = "search_date";


    //디비 생성용
    public static final class CreateDB implements BaseColumns {
        //검색어 관련 테이블
        public static final String _CREATE_SEARCH_INFO_TABLE = "create table " +_SEARCH_INFO_TABLE_NAME
                + " (" + _SEARCH_KEY + " int primary key, "
                + _SEARCH_KEYWORD + " text, "
                + _SEARCH_DATE + " datetime"
                + " ); ";
    }
}
