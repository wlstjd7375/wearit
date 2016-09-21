package kr.wearit.android.model;

/**
 * Created by bagjehyeon on 2015. 12. 30..
 */
public class SearchWord {
    private int key;
    private String searchWord;
    private String date;

    public void setKey(int key){
        this.key = key;
    }

    public int getKey(){
        return this.key;
    }

    public void setSearchWord(String searchWord){
        this.searchWord = searchWord;
    }

    public String getSearchWord(){
        return this.searchWord;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }
}
