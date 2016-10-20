package kr.wearit.android.view.account;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nhn.android.maps.opt.V;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;

import kr.wearit.android.Config;
import kr.wearit.android.Const;
import kr.wearit.android.R;
import kr.wearit.android.view.BaseActivity;

public class FindAddressActivity extends BaseActivity {
    private static final String TAG = FindAddressActivity.class.getSimpleName();
    private static final boolean LOG = Config.LOG;
    private static final String IP_ADDR = "http://biz.epost.go.kr/KpostPortal/openapi";

    private static SignupActivity signupActivity;
    private static int flag;

    public static void launch(final Activity activity) {
        if(activity instanceof SignupActivity){
            flag = 0;
            signupActivity = (SignupActivity) activity;
        }

        activity.startActivity(new Intent(activity, FindAddressActivity.class));
    }

    private EditText etSearch;
    private ImageButton btSearch;

    private ListView lvResult;

    private LinearLayout llTip;
    private LinearLayout llResult;

    private Adapter mAdapter;
    private HashSet<Address> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_address);

        initialize();
    }

    public void initialize(){
        ((TextView) findViewById(R.id.tvToolbarTitle)).setText("주소 찾기");
        ((ImageView) findViewById(R.id.ivToolbarBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        etSearch = (EditText) findViewById(R.id.et_search);
        btSearch = (ImageButton) findViewById(R.id.bt_search);
        lvResult = (ListView) findViewById(R.id.lv_result);

        llTip = (LinearLayout) findViewById(R.id.ll_tip);
        llResult = (LinearLayout) findViewById(R.id.ll_result);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = etSearch.getText().toString();
                llTip.setVisibility(View.GONE);
                llResult.setVisibility(View.VISIBLE);

                new AddressGetTask(word).execute();

            }
        });

        mAdapter = new Adapter();

        lvResult.setAdapter(mAdapter);
    }

    public void setData(){
        Log.d(TAG,"SetData Call . ListSize : "  + resultList.size());
        mAdapter.clear();
        mAdapter.addAll(resultList);
        mAdapter.notifyDataSetChanged();
    }

    public void getAddressNum(String query) {
        try {

            URL url = new URL(IP_ADDR + "?regkey=" + Const.POST_KEY + "&target=" + "postNew" + "&query=" + URLEncoder.encode(query, "EUC-KR") + "&countPerPage=50&currentPage=1");
//            URL url2 = new URL("http://biz.epost.go.kr/KpostPortal/openapi?regkey=test&target=post&query=구의1동");
            System.out.println(URLEncoder.encode(query, "EUC-KR").toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept-language", "ko");
            //xml데이터를 읽어서 inpuitstream에 저장
            InputStream in = conn.getInputStream();

            //XmlPullParser를 사용하기 위해서
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            //네임스페이스 사용여부
            factory.setNamespaceAware(true);
            //xml문서를 이벤트를 이용해서 데이터를 추출해주는 객체
            XmlPullParser xpp = factory.newPullParser();

            //XmlPullParser xml데이터를 저장
            xpp.setInput(in, "UTF-8");

            //이벤트 저장할 변수선언
            int eventType = xpp.getEventType();
            resultList = new HashSet<Address>();
            String tagName = "";
            String address = "";
            String jibun = "";
            String postcd = "";
            boolean isItemTag = false; // <item> .영역에 인지 여부 체크

            //xml의 데이터의 끝까지 돌면서 원하는 데이터를  얻어옴
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) { //시작 태그를 만났을때.
                    //태그명을 저장
                    tagName = xpp.getName();
                    System.out.println("TagName : " + tagName);
                    if (tagName.equals("item")) isItemTag = true;
                } else if (eventType == XmlPullParser.TEXT) {
                    //address와 postcd 변수를 이용하여 자신에게 알맞는 형태로 사용하기
                    System.out.println("TagName : " + tagName);

                    if (isItemTag && tagName.equals("postcd")){
                        String tmp = xpp.getText();
                        if(tmp.length() != 0) {
                            postcd += tmp;
                        }
                    }
                    if (isItemTag && tagName.equals("address")) {
                        String tmp = xpp.getText();
                        if(tmp.length() != 0) {
                            address += xpp.getText();
                        }
                    }
                    if (isItemTag && tagName.equals("addrjibun")) {
                        String tmp = xpp.getText();
                        if(tmp.length() != 0) {
                            jibun += xpp.getText();
                        }
                    }

                } else if (eventType == XmlPullParser.END_TAG) { //닫는 태그를 만났을때
                    //태그명을 저장
                    tagName = xpp.getName();
//                    if (tagName.equals("postcd")){
//                        if(address.length()!=0 && postcd.length() > 4 && jibun.length() != 0) {
//                            resultList.add(new Address(postcd, address, jibun));
//                            address = "";
//                            postcd = "";
//                            jibun = "";
//                        }
//                    }
                    if (tagName.equals("item")) {
                        if(address.length()!=0 && postcd.length() > 4 && jibun.length() != 0) {
                            Log.d(TAG,"ADD ITEM");
                            jibun = jibun.trim();
                            address = address.trim();
                            postcd = postcd.trim();
                            System.out.println("Jibun : " + jibun + " Address : " + address + " PostCode : " + postcd);
                            resultList.add(new Address(postcd, address, jibun));
//                            address = "";
//                            postcd = "";
//                            jibun = "";
                        }

                        jibun = ""; //초기화
                        address = ""; //초기화
                        postcd = ""; //초기화
                        isItemTag = false; //초기화
                    }
                }
                eventType = xpp.next(); //다음 이벤트 타입
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AddressGetTask extends AsyncTask<Void, Void, Void> {
        private String keyWord;

        public AddressGetTask(String keyWord){
            this.keyWord = keyWord;
        }
        @Override
        protected Void doInBackground(Void... params) {
            getAddressNum(keyWord);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            setData();
        }
    }

    private class Adapter extends ArrayAdapter<Address> {

        public Adapter() {
            super(getActivity(), 0);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;

            view = LayoutInflater.from(getActivity()).inflate(R.layout.listrow_address, parent, false);

            TextView code = (TextView) view.findViewById(R.id.tv_code);
            TextView doro = (TextView) view.findViewById(R.id.tv_doro);
            TextView jibun = (TextView) view.findViewById(R.id.tv_jibun);

            code.setText(getItem(position).getPostcd());
            doro.setText(getItem(position).getDoro());
            jibun.setText(getItem(position).getJibun());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signupActivity.setAddress(getItem(position).postcd, getItem(position).doro);
                    finish();
                }
            });

            return view;
        }
    }

    private class Address {
        private String postcd;
        private String doro;
        private String jibun;

        public Address(String postcd, String doro, String jibun) {
            this.postcd = postcd;
            this.doro = doro;
            this.jibun = jibun;
        }

        public String getPostcd() {
            return this.postcd;
        }


        public String getDoro() {
            return doro;
        }

        public String getJibun() {
            return jibun;
        }
    }
}
