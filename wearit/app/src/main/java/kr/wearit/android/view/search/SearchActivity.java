package kr.wearit.android.view.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.RecommandApi;
import kr.wearit.android.controller.SearchApi;
import kr.wearit.android.database.DBManager;
import kr.wearit.android.model.Recommand;
import kr.wearit.android.model.Search;
import kr.wearit.android.model.SearchWord;
import kr.wearit.android.view.BaseActivity;

public class SearchActivity extends BaseActivity {

    private ArrayList<Recommand> recommandList;
    private ArrayList<SearchWord> recentList;
    private LinearLayout llRecomand;
    private LinearLayout llRecent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        llRecomand = (LinearLayout) findViewById(R.id.ll_recommand);
        llRecent = (LinearLayout) findViewById(R.id.ll_recent);

    }

    @Override
    public void onResume(){
        super.onResume();
        RecommandApi.getList(new Api.OnWaitListener<ArrayList<Recommand>>(getActivity()) {
            @Override
            public void onSuccess(ArrayList<Recommand> data) {
                recommandList = data;
                System.out.println("SearchMain Size: " + recommandList.size());
                initializeRecommand();
            }
        });
        recentList = DBManager.getManager(getActivity().getApplicationContext()).getALLSearchWord();
        initializeRecent();

        ((ImageButton) findViewById(R.id.bt_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ((ImageButton) findViewById(R.id.bt_search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearch(((EditText) findViewById(R.id.et_search)).getText().toString());
            }
        });
    }

    public void initializeRecent() {
        for(int i=0;i <recentList.size();i++) {
            View view = getLayoutInflater().inflate(R.layout.content_search_text,null);
            final SearchWord recent = recentList.get(i);
            ((TextView) view.findViewById(R.id.text)).setText(recent.getSearchWord());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Recommand : " + recent.getSearchWord());
                    onSearch(recent.getSearchWord());
                }
            });
            llRecent.addView(view);
        }
    }

    public void initializeRecommand() {
        for(int i=0;i <recommandList.size();i++) {
            View view = getLayoutInflater().inflate(R.layout.content_search_text,null);
            final Recommand recommand = recommandList.get(i);
            ((TextView) view.findViewById(R.id.text)).setText(recommand.getRecommand());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Recommand : " + recommand.getRecommand());
                    onSearch(recommand.getRecommand());
                }
            });
            llRecomand.addView(view);
        }
    }

    public void onSearch(String query) {
        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
        intent.putExtra("word",query);
        startActivity(intent);
    }
}
