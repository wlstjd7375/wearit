package kr.wearit.android.view.check;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
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

    public final static String ISP = "ispmobile";
    public final static String BANKPAY = "kftc-bankpay";
    public final static String HYUNDAI_APPCARD = "hdcardappcardansimclick"; //intent:hdcardappcardansimclick://appcard?acctid=201605092050048514902797477441#Intent;package=com.hyundaicard.appcard;end;
    public final static String KB_APPCARD = "kb-acp"; //intent://pay?srCode=5681318&kb-acp://#Intent;scheme=kb-acp;package=com.kbcard.cxh.appcard;end;


    public final static String PACKAGE_ISP = "kvp.jjy.MispAndroid320";
    public final static String PACKAGE_BANKPAY = "com.kftc.bankpay.android";

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
                System.out.println("Current Url : " + url);
                if(url.contains("https://service.iamport.kr/payments/fail?success=false")){
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
                    return true;
                }
                if(url.contains("https://service.iamport.kr/payments/success?success=true")){
                    Intent intent = new Intent(getActivity(),OrderCompleteActivity.class);
                    intent.putExtra("order",cardPay.getOrdernum());
                    startActivity(intent);
                    backActivity.finish();
                    finish();
                    return true;
                }
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

            @Override
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view,url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                System.out.println("Webview error : " + error.toString());
                super.onReceivedError(view, request, error);
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
            }

            /**
             * @param scheme
             * @return 해당 scheme에 대해 처리를 직접 하는지 여부
             *
             * 결제를 위한 3rd-party 앱이 아직 설치되어있지 않아 ActivityNotFoundException이 발생하는 경우 처리합니다.
             * 여기서 handler되지않은 scheme에 대해서는 intent로부터 Package정보 추출이 가능하다면 다음에서 packageName으로 market이동합니다.
             *
             */
            protected boolean handleNotFoundPaymentScheme(String scheme) {
                //PG사에서 호출하는 url에 package정보가 없어 ActivityNotFoundException이 난 후 market 실행이 안되는 경우
                if ( ISP.equalsIgnoreCase(scheme) ) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PACKAGE_ISP)));
                    return true;
                } else if ( BANKPAY.equalsIgnoreCase(scheme) ) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PACKAGE_BANKPAY)));
                    return true;
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
        Intent intent = new Intent(getActivity(),OrderCompleteActivity.class);
        intent.putExtra("order",cardPay.getOrdernum());
        startActivity(intent);
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
