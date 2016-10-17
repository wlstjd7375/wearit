package kr.wearit.android.view.check;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URISyntaxException;

import kr.wearit.android.Config;
import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.OrderApi;
import kr.wearit.android.model.CardPay;
import kr.wearit.android.view.BaseActivity;

import static android.R.attr.data;

public class CardPaymentActivity extends BaseActivity {
    public static final String ARG = "card";
    public static final String ARG_ORDER = "order";
    public static final String ARG_TYPE = "ordertype";

    private static final String APP_SCHEME = "wearit";

    private static Activity backActivity;

    public static void launch(Activity activity, CardPay cardPay, int order, String orderType) {
        backActivity = activity;

        Intent intent = new Intent(activity, CardPaymentActivity.class);
        intent.putExtra(ARG, cardPay);
        intent.putExtra(ARG_ORDER, order);
        intent.putExtra(ARG_TYPE, orderType);

        activity.startActivity(intent);
    }

    private boolean isISP_call;

    private CardPay cardPay;
    private int order;
    private String orderType;

    private WebView wvCard;

    private Handler handler;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);

        handler = new Handler(this.getMainLooper());
        cardPay = getIntent().getParcelableExtra(ARG);
        order = getIntent().getIntExtra(ARG_ORDER, 0);
        orderType = getIntent().getStringExtra(ARG_TYPE);

        String url = Config.getBase() + "/payment/card";
        String data = "order=" + cardPay.getOrdernum() + "&paytype=" + cardPay.getPaytype() + "&price=" + cardPay.getPrice() + "&productname=" + cardPay    .getProductname() + "&userid=" + cardPay.getUser();

        System.out.println(url);
        System.out.println(data);

        wvCard = (WebView) findViewById(R.id.wv_card);
        wvCard.getSettings().setJavaScriptEnabled(true);
        wvCard.getSettings().setDefaultTextEncodingName("euc-kr");
        wvCard.getSettings().setDomStorageEnabled(true);
        wvCard.postUrl(url, data.getBytes());
        wvCard.addJavascriptInterface(new AndroidBridge(), "android");
        wvCard.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
                    Intent intent = null;

                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME); //IntentURI처리
                        Uri uri = Uri.parse(intent.getDataString());

                        startActivity(new Intent(Intent.ACTION_VIEW, uri)); //해당되는 Activity 실행
                        return true;
                    } catch (URISyntaxException ex) {
                        return false;
                    } catch (ActivityNotFoundException e) {
                        if (intent == null) return false;

                        String packageName = intent.getPackage();
                        if (packageName != null) { //packageName이 있는 경우에는 Google Play에서 검색을 기본
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                            return true;
                        }

                        return false;
                    }
                }

                return false;
            }
        });

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event){
//        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
//            if(wvCard.canGoBack()){
//                wvCard.goBack();
//            }
//            else {
//                onBackPressed();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    }

    @Override
    public void onBackPressed(){
        if(wvCard.canGoBack()){
            wvCard.goBack();
        }
        else {
            Toast.makeText(getActivity(), "결제가 취소되었습니다.", Toast.LENGTH_SHORT).show();
            OrderApi.remove(order, new Api.OnAuthListener<Void>() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(Void data) {
                    finish();
                }

                @Override
                public void onFail() {
                }
            });
            super.onBackPressed();
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        String url = intent.toString();

        if ( url.startsWith(APP_SCHEME) ) {
            // "iamportapp://https://pgcompany.com/foo/bar"와 같은 형태로 들어옴
            String redirectURL = url.substring(APP_SCHEME.length() + "://".length());
            wvCard.loadUrl(redirectURL);
        }
    }
    public void success(){
        OrderCompleteActivity.launch(getActivity(), order);
        backActivity.finish();
        finish();
    }

    public void failed() {
        Toast.makeText(getActivity(), "결제가 실패하였습니다.", Toast.LENGTH_SHORT).show();
        OrderApi.remove(order, new Api.OnAuthListener<Void>() {
            @Override
            public void onStart() {
            }
            @Override
            public void onSuccess(Void data) {
                finish();
            }
            @Override
            public void onFail() {
            }
        });
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void callAndroid (final String arg, final String ordercode) { // must be final
            handler.post(new Runnable() {
                public void run() {
                    System.out.println("Return : " + arg);
                    if(arg.equals("true")){
                        success();
                    }
                    else{
                        failed();
                    }
                }
            });
        }
    }
}
