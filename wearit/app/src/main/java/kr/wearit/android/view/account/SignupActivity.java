package kr.wearit.android.view.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import kr.wearit.android.R;
import kr.wearit.android.controller.Api;
import kr.wearit.android.controller.UserApi;
import kr.wearit.android.model.User;
import kr.wearit.android.util.EncryptPassword;
import kr.wearit.android.view.BaseActivity;

public class SignupActivity extends BaseActivity {

    private String LOG = "LoginActivity##";
    private Context mContext;

    private TextView tvToolbarTitle;
    private ImageView ivToolbarBack;

    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private EditText etName;
    private EditText etBirthday;
    private EditText etPhone;
    private Button btSendSMS;
    private EditText etCertification;
    private Button btCertification;
    private EditText etAddress;
    private Button btFindAddress;
    private EditText etExtraAddress;
    private EditText etHeight;
    private EditText etWeight;
    private Button btSignUp;

    private User user;

    private String confirmCode;
    private boolean isConfirm;

    private static String address;
    private static String postcd;

    private Wait wait;

    private TextView tvYak;
    private TextView tvGaein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
    }

    @Override
    public void onBackPressed(){
        if(((LinearLayout) findViewById(R.id.ll_yak)).getVisibility() == View.VISIBLE){
            ((LinearLayout) findViewById(R.id.ll_yak)).setVisibility(View.GONE);
        }
        else if (((LinearLayout) findViewById(R.id.ll_gaein)).getVisibility() == View.VISIBLE){
            ((LinearLayout) findViewById(R.id.ll_gaein)).setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
        }
    }

    private void init() {
        mContext = this;
        isConfirm = false;
        wait = new Wait();
        tvToolbarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        tvToolbarTitle.setText("SIGN UP");
        ivToolbarBack = (ImageView)findViewById(R.id.ivToolbarBack);
        ivToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText)findViewById(R.id.etPasswordConfirm);
        etName = (EditText)findViewById(R.id.etName);
        etBirthday = (EditText)findViewById(R.id.etBirthday);
        etPhone = (EditText)findViewById(R.id.etPhone);
        btSendSMS = (Button)findViewById(R.id.btSendSMS);
        etCertification = (EditText)findViewById(R.id.etCertification);
        btCertification = (Button)findViewById(R.id.btCertification);
        etAddress = (EditText)findViewById(R.id.etAddress);
        btFindAddress = (Button)findViewById(R.id.btFindAddress);
        etExtraAddress = (EditText)findViewById(R.id.etExtraAddress);
        etHeight = (EditText)findViewById(R.id.etHeight);
        etWeight = (EditText)findViewById(R.id.etWeight);

        tvYak = (TextView) findViewById(R.id.tv_yak);
        tvGaein = (TextView) findViewById(R.id.tv_gaein);

        String text = "약관";
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0 , text.length(), 0);
        tvYak.setText(content);
        tvYak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LinearLayout) findViewById(R.id.ll_yak)).setVisibility(View.VISIBLE);
            }
        });

        String text2 = "개인정보 수집/이용";
        SpannableString content2 = new SpannableString(text2);
        content2.setSpan(new UnderlineSpan(), 0 , text2.length(), 0);
        tvGaein.setText(content2);
        tvGaein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LinearLayout) findViewById(R.id.ll_gaein)).setVisibility(View.VISIBLE);
            }
        });

        ((ImageButton) findViewById(R.id.bt_exit_yak)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ((ImageButton) findViewById(R.id.bt_exit_gaein)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ((TextView) findViewById(R.id.tv_yak_text)).setText(getYak());
        ((TextView) findViewById(R.id.tv_geain_text)).setText(getGaein());

        btSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConfirm) {
                    UserApi.confirmPhone(etPhone.getText().toString(), new Api.OnAuthDefaultListener<String>() {
                        @Override
                        public void onSuccess(String data) {
                            if(data == null){
                                makeToast("이미 가입된 휴대폰 번호 입니다.");
                                return;
                            }
                            makeToast("인증 번호가 발송 되었습니다.");
                            System.out.println("인증 번호 : " + data);
                            confirmCode = data;
                        }
                    });
                }
            }
        });

        btCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = etCertification.getText().toString();
                if(input.equals(confirmCode)){
                    makeToast("인증이 완료 되었습니다.");
                    isConfirm = true;
                    etCertification.setTextColor(Color.parseColor("#d8d8d8"));
                    etPhone.setTextColor(Color.parseColor("#d8d8d8"));
                    etPhone.setFocusable(false);
                    etPhone.setClickable(false);
                    etCertification.setFocusable(false);
                    etCertification.setClickable(false);
                }
                else{
                    makeToast("인증 번호를 다시 입력 해주세요.");
                }
            }
        });

        btSignUp = (Button)findViewById(R.id.btSignUp);
        btSignUp.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFormIsValid()) {
                    makeUserInfo();
                    UserApi.add(user, new Api.OnListener<User>() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(User data) {
                            makeToast("회원가입 성공!");
                            finish();
                        }

                        @Override
                        public void onFail() {

                        }
                    });
                }
            }
        });

        btFindAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindAddressActivity.launch(getActivity());
            }
        });
    }

    private void makeUserInfo() {
        user = new User();

        user.setId(etEmail.getText().toString());
        user.setAccount(1);
        user.setPassword(EncryptPassword.encryptSHA256(etPassword.getText().toString()));
        user.setName(etName.getText().toString());
        user.setBirthday(etBirthday.getText().toString());
        user.setPhone(etPhone.getText().toString());
        user.setPostcode(postcd);
        user.setAddress1(etAddress.getText().toString());
        user.setAddress2(etExtraAddress.getText().toString());
        user.setHeight(etHeight.getText().toString());
        user.setWeight(etWeight.getText().toString());
    }

    private boolean isFormIsValid() {

        String email = etEmail.getText().toString();

        if(!isConfirm) {
            makeToast("휴대폰 인증을 해주세요.");
            return false;
        }
        if(email.equals("")) {
            makeToast("Email을 입력해 주세요.");
            return false;
        }
        if(!email.contains("@") || !email.contains(".")) {
            makeToast("Email 형식이 잘못되었습니다.");
            return false;
        }

        String password = etPassword.getText().toString();
        String password2 = etPasswordConfirm.getText().toString();
        if(password.equals("")) {
            makeToast("비밀번호를 입력해주세요.");
            return false;
        }
        if(!password.equals(password2)) {
            makeToast("비밀번호를 확인해주세요.");
            return false;
        }

        String birthday = etBirthday.getText().toString();
        if(birthday.equals("")) {
            makeToast("생년월일을 입력해주세요.");
            return false;
        }

        String authenNumber = etCertification.getText().toString();
        if(authenNumber.equals("")) {
            makeToast("휴대폰 번호를 인증해주세요.");
            return false;
        }

        //TODO 인증번호 확인

        if(etAddress.getText().toString().equals("")) {
            makeToast("주소를 입력해주세요.");
            return false;
        }
        if(etExtraAddress.getText().toString().equals("")) {
            makeToast("나머지 주소를 입력해주세요.");
            return false;
        }

        if(!((CheckBox) findViewById(R.id.cb_agree)).isChecked()){
            makeToast("이용약관 및 개인정보 수집/이용에 동의해주세요.");
        }


        return true;
    }

    private void makeToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void setAddress(String postcd, String address) {
        this.postcd = postcd;
        this.address = address;

        wait.run();
    }

    private class Wait implements Runnable{

        @Override
        public void run() {
            etAddress.setText(address);

        }
    }

    public String getYak(){
        String data = "STYLEMAP은 편집샵 및 패션 정보를 제공하는 어플리케이션입니다. STYLEMAP의 원활한 서비스 이용을 도모하기 위하여 본 서비스와 함께 약관을 제공합니다. STYLEMAP은 서비스 이용자에게 약관을 검토 할 수 있는 합리적인 기회를 제공하였으며 회원은 본 약관에 동의하였음을 확인하는 바입니다.\n" +
                "\n" +
                "제1장 총칙\n" +
                "제1조(목적) \n" +
                "이 약관은 STYLEMAP이 제공하는 디지털콘텐츠(이하 \"콘텐츠\"라고 한다) 및 제반 서비스의 이용과 관련하여 STYLEMAP과 이용자와의 권리, 의무 및 책임사항 등을 규정함을 목적으로 합니다.\n" +
                "\n" +
                "제2조(정의) 이 약관에서 사용하는 용어의 정의는 다음과 같습니다.\n" +
                "1. \"STYLEMAP\"이라 함은 \"콘텐츠\" 산업과 관련된 경제활동을 영위하는 자로서 콘텐츠 및 제반 서비스를 제공하는 자를 말합니다.\n" +
                "2. \"이용자\"라 함은 \"STYLEMAP\"의 사이트에 접속하여 이 약관에 따라 \"STYLEMAP”이 제공하는 \"콘텐츠\" 및 제반 서비스를 이용하는 회원 및 비회원을 말합니다.\n" +
                "3. \"회원\"이라 함은 \"STYLEMAP\"과 이용계약을 체결하고 \"이용자\" 아이디(ID)를 부여 받은 \"이용자\"로서 \"STYLEMAP\"의 정보를 지속적으로 제공받으며 \"STYLEMAP\"이 제공하는 서비스를 지속적으로 이용할 수 있는 자를 말합니다.\n" +
                "4. \"비회원\"이라 함은 \"회원\"이 아니면서 \"STYLEMAP\"이 제공하는 서비스를 이용하는 자를 말합니다.\n" +
                "5. \"콘텐츠\"라 함은 정보통신망이용촉진 및 정보보호 등에 관한 법률 제2조 제1항 제1호의 규정에 의한 정보통신망에서 사용되는 부호·문자·음성·음향·이미지 또는 영상 등으로 표현된 자료 또는 정보로서, 그 보존 및 이용에 있어서 효용을 높일 수 있도록 전자적 형태로 제작 또는 처리된 것을 말합니다.\n" +
                "6. \"아이디(ID)\"라 함은 \"회원\"의 식별과 서비스이용을 위하여 \"회원\"이 정하고 \"STYLEMAP\"이 승인하는 문자 또는 숫자의 조합을 말합니다.\n" +
                "7. \"비밀번호(PASSWORD)\"라 함은 \"회원\"이 부여받은 \"아이디\"와 일치되는 \"회원\"임을 확인하고 비밀보호를 위해 \"회원\" 자신이 정한 문자 또는 숫자의 조합을 말합니다.\n" +
                "제3조(신원정보 등의 제공) \"STYLEMAP\"은 이 약관의 내용, 상호, 대표자 성명, 영업소 소재지 주소(소비자의 불만을 처리할 수 있는 곳의 주소를 포함), 전화번호, 모사전송번호, 전자우편주소, 사업자등록번호, 통신판매업 신고번호 및 개인정보관리책임자 등을 이용자가 쉽게 알 수 있도록 온라인 서비스초기화면에 게시합니다. 다만, 약관은 이용자가 연결화면을 통하여 볼 수 있도록 할 수 있습니다.\n" +
                "제4조(약관의 게시 등) ① \"STYLEMAP\"은 이 약관을 \"회원\"이 그 전부를 인쇄할 수 있고 거래과정에서 해당 약관의 내용을 확인할 수 있도록 기술적 조치를 취합니다.\n" +
                "② \"STYLEMAP\"은 \"이용자\"가 \"STYLEMAP\"과 이 약관의 내용에 관하여 질의 및 응답할 수 있도록 기술적 장치를 설치합니다.\n" +
                "③ \"STYLEMAP\"은 \"이용자\"가 약관에 동의하기에 앞서 약관에 정하여져 있는 내용 중 청약철회, 환불조건 등과 같은 중요한 내용을 이용자가 쉽게 이해할 수 있도록 별도의 연결화면 또는 팝업화면 등을 제공하여 \"이용자\"의 확인을 구합니다.\n" +
                "제5조(약관의 개정 등) ① \"STYLEMAP”은 온라인 디지털콘텐츠산업 발전법, 전자상거래 등에서의 소비자보호에 관한 법률, 약관의 규제에 관한 법률 등 관련법을 위배하지 않는 범위에서 이 약관을 개정할 수 있습니다.\n" +
                "② \"STYLEMAP\"이 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 서비스초기화면에 그 적용일자 7일 이전부터 적용일 후 상당한 기간 동안 공지하고, 기존회원에게는 개정약관을 전자우편주소로 발송합니다. \n" +
                "③ \"STYLEMAP\"이 약관을 개정할 경우에는 개정약관 공지 후 개정약관의 적용에 대한 \"이용자\"의 동의 여부를 확인합니다. \"이용자\"가 개정약관의 적용에 동의하지 않는 경우 \"STYLEMAP\" 또는 \"이용자\"는 콘텐츠 이용계약을 해지할 수 있습니다. 이때, \"STYLEMAP\"은 계약해지로 인하여 \"이용자\"가 입은 손해를 배상합니다. \n" +
                "제6조(약관의 해석) 이 약관에서 정하지 아니한 사항과 이 약관의 해석에 관하여는 온라인 디지털콘텐츠산업 발전법, 전자상거래 등에서의 소비자보호에 관한 법률, 약관의 규제에 관한 법률, 문화체육관광부장관이 정하는 디지털콘텐츠이용자보호지침, 기타 관계법령 또는 상관례에 따릅니다.\n" +
                "제2장 회원가입\n" +
                "제7조(회원가입) ① 회원가입은 \"이용자\"가 약관의 내용에 대하여 동의를 하고 회원가입신청을 한 후 \"STYLEMAP\"이 이러한 신청에 대하여 승낙함으로써 체결됩니다.\n" +
                "② 회원가입신청서에는 다음 사항을 기재해야 합니다. 1호 내지 3호의 사항은 필수사항이며, 그 외의 사항은 선택사항입니다. \n" +
                "1. \"회원\"의 성명과 주민등록번호 또는 인터넷상 개인식별번호\n" +
                "2. \"아이디\"와 \"비밀번호\"\n" +
                "3. 전자우편주소\n" +
                "4. 이용하려는 \"콘텐츠\"의 종류\n" +
                "5. 기타 \"STYLEMAP”이 필요하다고 인정하는 사항\n" +
                "③ \"STYLEMAP”은 상기 \"이용자\"의 신청에 대하여 회원가입을 승낙함을 원칙으로 합니다. 다만, \"STYLEMAP”은 다음 각 호에 해당하는 신청에 대하여는 승낙을 하지 않을 수 있습니다.\n" +
                "1. 가입신청자가 이 약관에 의하여 이전에 회원자격을 상실한 적이 있는 경우\n" +
                "2. 실명이 아니거나 타인의 명의를 이용한 경우\n" +
                "3. 허위의 정보를 기재하거나, STYLEMAP가 제시하는 내용을 기재하지 않은 경우\n" +
                "4. 이용자의 귀책사유로 인하여 승인이 불가능하거나 기타 규정한 제반 사항을 위반하며 신청하는 경우\n" +
                "④ \"STYLEMAP”은 서비스 관련 설비의 여유가 없거나, 기술상 또는 업무상 문제가 있는 경우에는 승낙을 유보할 수 있습니다.\n" +
                "⑤ 제3항과 제4항에 따라 회원가입신청의 승낙을 하지 아니하거나 유보한 경우, \"STYLEMAP”은 이를 신청자에게 알려야 합니다. \"STYLEMAP\"의 귀책사유 없이 신청자에게 통지할 수 없는 경우에는 예외로 합니다.\n" +
                "⑥ 회원가입계약의 성립 시기는 \"STYLEMAP\"의 승낙이 \"이용자\"에게 도달한 시점으로 합니다.\n" +
                "제8조(미성년자의 회원가입에 관한 특칙) ① 만 14세 미만의 \"이용자\"는 개인정보의 수집 및 이용목적에 대하여 충분히 숙지하고 부모 등 법정대리인의 동의를 얻은 후에 회원가입을 신청하고 본인의 개인정보를 제공하여야 합니다. \n" +
                "② STYLEMAP는 부모 등 법정대리인의 동의에 대한 확인절차를 거치지 않은 14세 미만 이용자에 대하여는 가입을 취소 또는 불허합니다.\n" +
                "③ 만 14세 미만 \"이용자\"의 부모 등 법정대리인은 아동에 대한 개인정보의 열람, 정정, 갱신을 요청하거나 회원가입에 대한 동의를 철회할 수 있으며, 이러한 경우에 \"STYLEMAP”은 지체 없이 필요한 조치를 취해야 합니다. \n" +
                "제9조(회원정보의 변경) ① \"회원\"은 개인정보관리화면을 통하여 언제든지 자신의 개인정보를 열람하고 수정할 수 있습니다. \n" +
                "② \"회원\"은 회원가입신청 시 기재한 사항이 변경되었을 경우 온라인으로 수정을 하거나 전자우편 기타 방법으로 \"STYLEMAP\"에 대하여 그 변경사항을 알려야 합니다. \n" +
                "③ 제2항의 변경사항을 \"STYLEMAP\"에 알리지 않아 발생한 불이익에 대하여 \"STYLEMAP”은 책임지지 않습니다. \n" +
                "제10조(\"회원\"의 \"아이디\" 및 \"비밀번호\"의 관리에 대한 의무) ① \"회원\"의 \"아이디\"와 \"비밀번호\"에 관한 관리책임은 \"회원\"에게 있으며, 이를 제3자가 이용하도록 하여서는 안 됩니다.\n" +
                "② \"회원\"은 \"아이디\" 및 \"비밀번호\"가 도용되거나 제3자에 의해 사용되고 있음을 인지한 경우에는 이를 즉시 \"STYLEMAP\"에 통지하고 \"STYLEMAP\"의 안내에 따라야 합니다.\n" +
                "③ 제2항의 경우에 해당 \"회원\"이 \"STYLEMAP\"에 그 사실을 통지하지 않거나, 통지한 경우에도 \"STYLEMAP\"의 안내에 따르지 않아 발생한 불이익에 대하여 \"STYLEMAP”은 책임지지 않습니다.\n" +
                "제11조(\"회원\"에 대한 통지) ① \"STYLEMAP”이 \"회원\"에 대한 통지를 하는 경우 \"회원\"이 지정한 전자우편주소로 할 수 있습니다.\n" +
                "② \"STYLEMAP”은 \"회원\" 전체에 대한 통지의 경우 7일 이상 \"STYLEMAP\"의 게시판에 게시함으로써 제1항의 통지에 갈음할 수 있습니다. 다만, \"회원\" 본인의 거래와 관련하여 중대한 영향을 미치는 사항에 대하여는 제1항의 통지를 합니다.\n" +
                "제12조(회원탈퇴 및 자격 상실 등) ① \"회원\"은 \"STYLEMAP\"에 언제든지 탈퇴를 요청할 수 있으며 \"STYLEMAP”은 즉시 회원탈퇴를 처리합니다.\n" +
                "② \"회원\"이 다음 각호의 사유에 해당하는 경우, \"STYLEMAP”은 회원자격을 제한 및 정지시킬 수 있습니다.\n" +
                "1. 가입신청 시에 허위내용을 등록한 경우\n" +
                "2. \"STYLEMAP\"의 서비스이용대금, 기타 \"STYLEMAP\"의 서비스이용에 관련하여 회원이 부담하는 채무를 기일에 이행하지 않는 경우\n" +
                "3. 다른 사람의 \"STYLEMAP\"의 서비스이용을 방해하거나 그 정보를 도용하는 등 전자상거래 질서를 위협하는 경우\n" +
                "4. \"STYLEMAP\"를 이용하여 법령 또는 이 약관이 금지하거나 공서양속에 반하는 행위를 하는 경우\n" +
                "③ \"STYLEMAP”이 회원자격을 제한·정지시킨 후, 동일한 행위가 2회 이상 반복되거나 30일 이내에 그 사유가 시정되지 아니하는 경우 \"STYLEMAP”은 회원자격을 상실시킬 수 있습니다. \n" +
                "④ \"STYLEMAP”이 회원자격을 상실시키는 경우에는 회원등록을 말소합니다. 이 경우 \"회원\"에게 이를 통지하고, 회원등록 말소 전에 최소한 30일 이상의 기간을 정하여 소명할 기회를 부여합니다.\n" +
                "제3장 콘텐츠이용계약\n" +
                "제13조(\"콘텐츠\"의 내용 등의 게시) ① \"STYLEMAP”은 다음 사항을 해당 \"콘텐츠\"의 이용초기화면이나 그 포장에 \"이용자\"가 알기 쉽게 표시합니다.\n" +
                "1. \"콘텐츠\"의 명칭 또는 제호\n" +
                "2. \"콘텐츠\"의 제작 및 표시 연월일\n" +
                "3. \"콘텐츠\" 제작자의 성명(법인인 경우에는 법인의 명칭), 주소, 전화번호\n" +
                "4. \"콘텐츠\"의 내용, 이용방법, 이용료 기타 이용조건 \n" +
                "② \"STYLEMAP”은 \"콘텐츠\"별 이용가능기기 및 이용에 필요한 최소한의 기술사양에 관한 정보를 계약체결과정에서 \"이용자\"에게 제공합니다.\n" +
                "제14조(이용계약의 성립 등) ① \"이용자\"는 \"STYLEMAP”이 제공하는 다음 또는 이와 유사한 절차에 의하여 이용신청을 합니다. \"STYLEMAP”은 계약 체결 전에 각 호의 사항에 관하여 \"이용자\"가 정확하게 이해하고 실수 또는 착오 없이 거래할 수 있도록 정보를 제공합니다.\n" +
                "1. \"콘텐츠\" 목록의 열람 및 선택\n" +
                "2. 성명, 주소, 전화번호(또는 이동전화번호), 전자우편주소 등의 입력\n" +
                "3. 약관내용, 청약철회가 불가능한 \"콘텐츠\"에 대해 \"STYLEMAP”이 취한 조치에 관련한 내용에 대한 확인 \n" +
                "4. 이 약관에 동의하고 위 제3호의 사항을 확인하거나 거부하는 표시(예, 마우스 클릭) \n" +
                "5. \"콘텐츠\"의 이용신청에 관한 확인 또는 \"STYLEMAP\"의 확인에 대한 동의 \n" +
                "6. 결제방법의 선택 \n" +
                "② \"STYLEMAP”은 \"이용자\"의 이용신청이 다음 각 호에 해당하는 경우에는 승낙하지 않거나 승낙을 유보할 수 있습니다.\n" +
                "1. 실명이 아니거나 타인의 명의를 이용한 경우\n" +
                "2. 허위의 정보를 기재하거나, \"STYLEMAP”이 제시하는 내용을 기재하지 않은 경우\n" +
                "3. 미성년자가 청소년보호법에 의해서 이용이 금지되는 \"콘텐츠\"를 이용하고자 하는 경우\n" +
                "4. 서비스 관련 설비의 여유가 없거나, 기술상 또는 업무상 문제가 있는 경우\n" +
                "③ \"STYLEMAP\"의 승낙이 제16조 제1항의 수신확인통지형태로 \"이용자\"에게 도달한 시점에 계약이 성립한 것으로 봅니다. \n" +
                "④ \"STYLEMAP\"의 승낙의 의사표시에는 \"이용자\"의 이용신청에 대한 확인 및 서비스제공 가능여부, 이용신청의 정정·취소 등에 관한 정보 등을 포함합니다.\n" +
                "제15조(미성년자 이용계약에 관한 특칙) \"STYLEMAP”은 만 20세 미만의 미성년이용자가 유료서비스를 이용하고자 하는 경우에 부모 등 법정 대리인의 동의를 얻거나, 계약체결 후 추인을 얻지 않으면 미성년자 본인 또는 법정대리인이 그 계약을 취소할 수 있다는 내용을 계약체결 전에 고지하는 조치를 취합니다.\n" +
                "제16조(수신확인통지·이용신청 변경 및 취소) ① \"STYLEMAP”은 \"이용자\"의 이용신청이 있는 경우 \"이용자\"에게 수신확인통지를 합니다. \n" +
                "② 수신확인통지를 받은 \"이용자\"는 의사표시의 불일치 등이 있는 경우에는 수신확인통지를 받은 후 즉시 이용신청 변경 및 취소를 요청할 수 있고, \"STYLEMAP”은 서비스제공 전에 \"이용자\"의 요청이 있는 경우에는 지체 없이 그 요청에 따라 처리하여야 합니다. 다만, 이미 대금을 지불한 경우에는 청약철회 등에 관한 제27조의 규정에 따릅니다. \n" +
                "제17조(\"STYLEMAP\"의 의무) ① \"STYLEMAP”은 법령과 이 약관이 정하는 권리의 행사와 의무의 이행을 신의에 좇아 성실하게 하여야 합니다.\n" +
                "② \"STYLEMAP”은 \"이용자\"가 안전하게 \"콘텐츠\"를 이용할 수 있도록 개인정보(신용정보 포함)보호를 위해 보안시스템을 갖추어야 하며 개인정보보호정책을 공시하고 준수합니다.\n" +
                "③ \"STYLEMAP”은 \"이용자\"가 콘텐츠이용 및 그 대금내역을 수시로 확인할 수 있도록 조치합니다.\n" +
                "④ \"STYLEMAP”은 콘텐츠이용과 관련하여 \"이용자\"로부터 제기된 의견이나 불만이 정당하다고 인정할 경우에는 이를 지체없이 처리합니다. 이용자가 제기한 의견이나 불만사항에 대해서는 게시판을 활용하거나 전자우편 등을 통하여 그 처리과정 및 결과를 전달합니다.\n" +
                "⑤ \"STYLEMAP”은 이 약관에서 정한 의무 위반으로 인하여 \"이용자\"가 입은 손해를 배상합니다.\n" +
                "제18조(\"이용자\"의 의무) ① \"이용자\"는 다음 행위를 하여서는 안 됩니다. \n" +
                "1. 신청 또는 변경 시 허위내용의 기재\n" +
                "2. 타인의 정보도용 \n" +
                "3. \"STYLEMAP\"에 게시된 정보의 변경 \n" +
                "4. \"STYLEMAP”이 금지한 정보(컴퓨터 프로그램 등)의 송신 또는 게시 \n" +
                "5. \"STYLEMAP”과 기타 제3자의 저작권 등 지적재산권에 대한 침해 \n" +
                "6. \"STYLEMAP\" 및 기타 제3자의 명예를 손상시키거나 업무를 방해하는 행위 \n" +
                "7. 외설 또는 폭력적인 말이나 글, 화상, 음향, 기타 공서양속에 반하는 정보를 \"STYLEMAP\"의 사이트에 공개 또는 게시하는 행위 \n" +
                "8. 기타 불법적이거나 부당한 행위\n" +
                "② \"이용자\"는 관계법령, 이 약관의 규정, 이용안내 및 \"콘텐츠\"와 관련하여 공지한 주의사항, \"STYLEMAP”이 통지하는 사항 등을 준수하여야 하며, 기타 \"STYLEMAP\"의 업무에 방해되는 행위를 하여서는 안 됩니다.\n" +
                "제19조(지급방법) \"콘텐츠\"의 이용에 대한 대금지급방법은 다음 각 호의 방법 중 가능한 방법으로 할 수 있습니다. 다만, \"STYLEMAP”은 \"이용자\"의 지급방법에 대하여 어떠한 명목의 수수료도 추가하여 징수하지 않습니다. \n" +
                "1. 폰뱅킹, 인터넷뱅킹, 메일 뱅킹 등의 각종 계좌이체 \n" +
                "2. 선불카드, 직불카드, 신용카드 등의 각종 카드결제 \n" +
                "3. 온라인무통장입금\n" +
                "4. 전자화폐에 의한 결제 \n" +
                "5. 마일리지 등 \"STYLEMAP”이 지급한 포인트에 의한 결제 \n" +
                "6. \"STYLEMAP”과 계약을 맺었거나 \"STYLEMAP”이 인정한 상품권에 의한 결제 \n" +
                "7. 전화 또는 휴대전화를 이용한 결제\n" +
                "8. 기타 전자적 지급방법에 의한 대금지급 등 \n" +
                "제20조(콘텐츠서비스의 제공 및 중단) ① 콘텐츠서비스는 연중무휴, 1일 24시간 제공함을 원칙으로 합니다.\n" +
                "② \"STYLEMAP”은 컴퓨터 등 정보통신설비의 보수점검, 교체 및 고장, 통신두절 또는 운영상 상당한 이유가 있는 경우 콘텐츠서비스의 제공을 일시적으로 중단할 수 있습니다. 이 경우 \"STYLEMAP”은 제11조[\"회원\"에 대한 통지]에 정한 방법으로 \"이용자\"에게 통지합니다. 다만, \"STYLEMAP”이 사전에 통지할 수 없는 부득이한 사유가 있는 경우 사후에 통지할 수 있습니다.\n" +
                "③ \"STYLEMAP”은 상당한 이유 없이 콘텐츠서비스의 제공이 일시적으로 중단됨으로 인하여 \"이용자\"가 입은 손해에 대하여 배상합니다. 다만, \"STYLEMAP”이 고의 또는 과실이 없음을 입증하는 경우에는 그러하지 아니합니다.\n" +
                "④ \"STYLEMAP”은 콘텐츠서비스의 제공에 필요한 경우 정기점검을 실시할 수 있으며, 정기점검시간은 서비스제공화면에 공지한 바에 따릅니다.\n" +
                "⑤ 사업종목의 전환, 사업의 포기, 업체 간의 통합 등의 이유로 콘텐츠서비스를 제공할 수 없게 되는 경우에는 \"STYLEMAP”은 제11조[\"회원\"에 대한 통지]에 정한 방법으로 \"이용자\"에게 통지하고 당초 \"STYLEMAP\"에서 제시한 조건에 따라 \"이용자\"에게 보상합니다. 다만, \"STYLEMAP”이 보상기준 등을 고지하지 아니하거나, 고지한 보상기준이 적절하지 않은 경우에는 \"이용자\"들의 마일리지 또는 적립금 등을 현물 또는 현금으로 \"이용자\"에게 지급합니다.\n" +
                "제21조(콘텐츠서비스의 변경) ① \"STYLEMAP”은 상당한 이유가 있는 경우에 운영상, 기술상의 필요에 따라 제공하고 있는 콘텐츠서비스를 변경할 수 있습니다. \n" +
                "② \"STYLEMAP”은 콘텐츠서비스의 내용, 이용방법, 이용시간을 변경할 경우에 변경사유, 변경될 콘텐츠서비스의 내용 및 제공일자 등을 그 변경 전 7일 이상 해당 콘텐츠초기화면에 게시합니다.\n" +
                "③ 제2항의 경우에 변경된 내용이 중대하거나 \"이용자\"에게 불리한 경우에는 \"STYLEMAP”이 해당 콘텐츠서비스를 제공받는 \"이용자\"에게 제11조[\"회원\"에 대한 통지]에 정한 방법으로 통지하고 동의를 받습니다. 이때, \"STYLEMAP”은 동의를 거절한 \"이용자\"에 대하여는 변경전 서비스를 제공합니다. 다만, 그러한 서비스 제공이 불가능할 경우 계약을 해지할 수 있습니다.\n" +
                "④ \"STYLEMAP”은 제1항에 의한 서비스의 변경 및 제3항에 의한 계약의 해지로 인하여 \"이용자\"가 입은 손해를 배상합니다.\n" +
                "제22조(정보의 제공 및 광고의 게재) ① \"STYLEMAP”은 \"이용자\"가 콘텐츠이용 중 필요하다고 인정되는 다양한 정보를 공지사항이나 전자우편 등의 방법으로 \"회원\"에게 제공할 수 있습니다. 다만, \"회원\"은 언제든지 전자우편 등을 통하여 수신 거절을 할 수 있습니다.\n" +
                "② 제1항의 정보를 전화 및 모사전송기기에 의하여 전송하려고 하는 경우에는 \"회원\"의 사전 동의를 받아서 전송합니다. \n" +
                "③ \"STYLEMAP”은 \"콘텐츠\"서비스 제공과 관련하여 콘텐츠화면, 홈페이지, 전자우편 등에 광고를 게재할 수 있습니다. 광고가 게재된 전자우편 등을 수신한 \"회원\"은 수신거절을 \"STYLEMAP\"에게 할 수 있습니다. \n" +
                "제23조(게시물의 삭제) ① \"STYLEMAP”은 게시판에 정보통신망이용촉진 및 정보보호 등에 관한 법률을 위반한 청소년유해매체물이 게시되어 있는 경우에는 이를 지체 없이 삭제 합니다. 다만, 19세 이상의 \"이용자\"만 이용할 수 있는 게시판은 예외로 합니다.\n" +
                "② \"STYLEMAP”이 운영하는 게시판 등에 게시된 정보로 인하여 법률상 이익이 침해된 자는 \"STYLEMAP\"에게 당해 정보의 삭제 또는 반박내용의 게재를 요청할 수 있습니다. 이 경우 \"STYLEMAP”은 지체 없이 필요한 조치를 취하고 이를 즉시 신청인에게 통지합니다.\n" +
                "제24조(저작권 등의 귀속) ① \"STYLEMAP”이 작성한 저작물에 대한 저작권 기타 지적재산권은 \"STYLEMAP\"에 귀속합니다. \n" +
                "② \"STYLEMAP”이 제공하는 서비스 중 제휴계약에 의해 제공되는 저작물에 대한 저작권 기타 지적재산권은 해당 제공업체에 귀속합니다.\n" +
                "③ \"이용자\"는 \"STYLEMAP”이 제공하는 서비스를 이용함으로써 얻은 정보 중 \"STYLEMAP\" 또는 제공업체에 지적재산권이 귀속된 정보를 \"STYLEMAP\" 또는 제공업체의 사전승낙 없이 복제, 전송, 출판, 배포, 방송 기타 방법에 의하여 영리목적으로 이용하거나 제3자에게 이용하게 하여서는 안 됩니다. \n" +
                "④ \"STYLEMAP”은 약정에 따라 \"이용자\"의 저작물을 사용하는 경우 당해 \"이용자\"의 허락을 받습니다.. \n" +
                "제25조(개인정보보호) ① \"STYLEMAP”은 제7조 제2항의 신청서기재사항 이외에 \"이용자\"의 콘텐츠이용에 필요한 최소한의 정보를 수집할 수 있습니다. 이를 위해 \"STYLEMAP”이 문의한 사항에 관해 \"이용자\"는 진실한 내용을 성실하게 고지하여야 합니다.\n" +
                "② \"STYLEMAP”이 \"이용자\"의 개인 식별이 가능한 \"개인정보\"를 수집하는 때에는 당해 \"이용자\"의 동의를 받습니다. \n" +
                "③ \"STYLEMAP”은 \"이용자\"가 이용신청 등에서 제공한 정보와 제1항에 의하여 수집한 정보를 당해 \"이용자\"의 동의 없이 목적 외로 이용하거나 제3자에게 제공할 수 없으며, 이를 위반한 경우에 모든 책임은 \"STYLEMAP”이 집니다. 다만, 다음의 경우에는 예외로 합니다. \n" +
                "1. 통계작성, 학술연구 또는 시장조사를 위하여 필요한 경우로서 특정 개인을 식별할 수 없는 형태로 제공하는 경우 \n" +
                "2. \"콘텐츠\" 제공에 따른 요금정산을 위하여 필요한 경우 \n" +
                "3. 도용방지를 위하여 본인확인에 필요한 경우 \n" +
                "4. 약관의 규정 또는 법령에 의하여 필요한 불가피한 사유가 있는 경우 \n" +
                "④ \"STYLEMAP”이 제2항과 제3항에 의해 \"이용자\"의 동의를 받아야 하는 경우에는 \"개인정보\"관리책임자의 신원(소속, 성명 및 전화번호 기타 연락처), 정보의 수집목적 및 이용목적, 제3자에 대한 정보제공관련사항(제공받는 자, 제공목적 및 제공할 정보의 내용)등에 관하여 정보통신망이용촉진 및 정보보호 등에 관한 법률 제22조 제2항이 규정한 사항을 명시하고 고지하여야 합니다.\n" +
                "⑤ \"이용자\"는 언제든지 제3항의 동의를 임의로 철회할 수 있습니다.\n" +
                "⑥ \"이용자\"는 언제든지 \"STYLEMAP”이 가지고 있는 자신의 \"개인정보\"에 대해 열람 및 오류의 정정을 요구할 수 있으며, \"STYLEMAP”은 이에 대해 지체 없이 필요한 조치를 취할 의무를 집니다. \"이용자\"가 오류의 정정을 요구한 경우에는 \"STYLEMAP”은 그 오류를 정정할 때까지 당해 \"개인정보\"를 이용하지 않습니다.\n" +
                "⑦ \"STYLEMAP”은 개인정보보호를 위하여 관리자를 한정하여 그 수를 최소화하며, 신용카드, 은행계좌 등을 포함한 \"이용자\"의 \"개인정보\"의 분실, 도난, 유출, 변조 등으로 인한 \"이용자\"의 손해에 대하여 책임을 집니다.\n" +
                "⑧ \"STYLEMAP\" 또는 그로부터 \"개인정보\"를 제공받은 자는 \"이용자\"가 동의한 범위 내에서 \"개인정보\"를 사용할 수 있으며, 목적이 달성된 경우에는 당해 \"개인정보\"를 지체 없이 파기합니다. \n" +
                "⑨ \"STYLEMAP”은 정보통신망이용촉진 및 정보보호에 관한 법률 등 관계 법령이 정하는 바에 따라 \"이용자\"의 \"개인정보\"를 보호하기 위해 노력합니다. \"개인정보\"의 보호 및 사용에 대해서는 관련법령 및 \"STYLEMAP\"의 개인정보보호정책이 적용됩니다. \n" +
                "제4장 콘텐츠이용계약의 청약철회, 계약해제·해지 및 이용제한\n" +
                "제26조(\"이용자\"의 청약철회와 계약해제·해지) ① \"STYLEMAP”과 \"콘텐츠\"의 이용에 관한 계약을 체결한 \"이용자\"는 수신확인의 통지를 받은 날로부터 7일 이내에는 청약의 철회를 할 수 있습니다. 다만, \"STYLEMAP”이 다음 각 호중 하나의 조치를 취한 경우에는 \"이용자\"의 청약철회권이 제한될 수 있습니다.\n" +
                "1. 청약의 철회가 불가능한 \"콘텐츠\"에 대한 사실을 표시사항에 포함한 경우\n" +
                "2. 시용상품을 제공한 경우\n" +
                "3. 한시적 또는 일부이용 등의 방법을 제공한 경우\n" +
                "② \"이용자\"는 다음 각 호의 사유가 있을 때에는 당해 \"콘텐츠\"를 공급받은 날로부터 3월 이내 또는 그 사실을 안 날 또는 알 수 있었던 날부터 30일 이내에 콘텐츠이용계약을 해제·해지할 수 있습니다.\n" +
                "1. 이용계약에서 약정한 \"콘텐츠\"가 제공되지 않는 경우\n" +
                "2. 제공되는 \"콘텐츠\"가 표시·광고 등과 상이하거나 현저한 차이가 있는 경우\n" +
                "3. 기타 \"콘텐츠\"의 결함으로 정상적인 이용이 현저히 불가능한 경우\n" +
                "③ 제1항의 청약철회와 제2항의 계약해제·해지는 \"이용자\"가 전화, 전자우편 또는 모사전송으로 \"STYLEMAP\"에 그 의사를 표시한 때에 효력이 발생합니다.\n" +
                "④ \"STYLEMAP”은 제3항에 따라 \"이용자\"가 표시한 청약철회 또는 계약해제·해지의 의사표시를 수신한 후 지체 없이 이러한 사실을 \"이용자\"에게 회신합니다.\n" +
                "⑤ \"이용자\"는 제2항의 사유로 계약해제·해지의 의사표시를 하기 전에 상당한 기간을 정하여 완전한 \"콘텐츠\" 혹은 서비스이용의 하자에 대한 치유를 요구할 수 있습니다.\n" +
                "제27조(\"이용자\"의 청약철회와 계약해제·해지의 효과) ① \"STYLEMAP”은 \"이용자\"가 청약철회의 의사표시를 한 날로부터 또는 \"이용자\"에게 계약해제·해지의 의사표시에 대하여 회신한 날로부터 3영업일 이내에 대금의 결제와 동일한 방법으로 이를 환급하여야 하며, 동일한 방법으로 환불이 불가능할 때에는 이를 사전에 고지하여야 합니다. 이 경우 \"STYLEMAP”이 \"이용자\"에게 환급을 지연한 때에는 그 지연기간에 대하여 공정거래위원회가 정하여 고시하는 지연이자율을 곱하여 산정한 지연이자를 지급합니다.\n" +
                "② \"STYLEMAP”이 제1항에 따라 환급할 경우에 \"이용자\"가 서비스이용으로부터 얻은 이익에 해당하는 금액을 공제하고 환급할 수 있습니다.\n" +
                "③ \"STYLEMAP”은 위 대금을 환급함에 있어서 \"이용자\"가 신용카드 또는 전자화폐 등의 결제수단으로 재화 등의 대금을 지급한 때에는 지체 없이 당해 결제수단을 제공한 사업자로 하여금 재화 등의 대금의 청구를 정지 또는 취소하도록 요청합니다. 다만, 제2항의 금액공제가 필요한 경우에는 그러하지 아니할 수 있습니다.\n" +
                "④ \"STYLEMAP\", \"콘텐츠 등의 대금을 지급 받은 자\" 또는 \"이용자와 콘텐츠이용계약을 체결한 자\"가 동일인이 아닌 경우에 각자는 청약철회 또는 계약해제·해지로 인한 대금환급과 관련한 의무의 이행에 있어서 연대하여 책임을 집니다.\n" +
                "⑤ \"STYLEMAP”은 \"이용자\"에게 청약철회를 이유로 위약금 또는 손해배상을 청구하지 않습니다. 그러나 \"이용자\"의 계약해제·해지는 손해배상의 청구에 영향을 미치지 않습니다. \n" +
                "제28조(STYLEMAP의 계약해제·해지 및 이용제한) ① \"STYLEMAP”은 \"이용자\"가 제12조 제2항에서 정한 행위를 하였을 경우 사전통지 없이 계약을 해제·해지하거나 또는 기간을 정하여 서비스이용을 제한할 수 있습니다. \n" +
                "② 제1항의 해제·해지는 \"STYLEMAP”이 자신이 정한 통지방법에 따라 \"이용자\"에게 그 의사를 표시한 때에 효력이 발생합니다.\n" +
                "③ \"STYLEMAP\"의 해제·해지 및 이용제한에 대하여 \"이용자\"는 \"STYLEMAP”이 정한 절차에 따라 이의신청을 할 수 있습니다. 이 때 이의가 정당하다고 \"STYLEMAP”이 인정하는 경우, \"STYLEMAP”은 즉시 서비스의 이용을 재개합니다.\n" +
                "제29조(STYLEMAP의 계약해제·해지의 효과) \"이용자\"의 귀책사유에 따른 이용계약의 해제·해지의 효과는 제27조를 준용합니다. 다만, \"STYLEMAP”은 \"이용자\"에 대하여 계약해제·해지의 의사표시를 한 날로부터 7영업일 이내에 대금의 결제와 동일한 방법으로 이를 환급합니다. \n" +
                "제5장 과오금, 피해보상 등\n" +
                "제30조(과오금) ① \"STYLEMAP”은 과오금이 발생한 경우 이용대금의 결제와 동일한 방법으로 과오금 전액을 환불하여야 합니다. 다만, 동일한 방법으로 환불이 불가능할 때는 이를 사전에 고지합니다.\n" +
                "② \"STYLEMAP\"의 책임 있는 사유로 과오금이 발생한 경우 \"STYLEMAP”은 계약비용, 수수료 등에 관계없이 과오금 전액을 환불합니다. 다만, \"이용자\"의 책임 있는 사유로 과오금이 발생한 경우, \"STYLEMAP”이 과오금을 환불하는 데 소요되는 비용은 합리적인 범위 내에서 \"이용자\"가 부담하여야 합니다.\n" +
                "③ STYLEMAP는 \"이용자\"가 주장하는 과오금에 대해 환불을 거부할 경우에 정당하게 이용대금이 부과되었음을 입증할 책임을 집니다.\n" +
                "④ \"STYLEMAP”은 과오금의 환불절차를 디지털콘텐츠이용자보호지침에 따라 처리합니다.\n" +
                "제31조(콘텐츠하자 등에 의한 이용자피해보상) \"STYLEMAP”은 콘텐츠하자 등에 의한 이용자피해보상의 기준·범위·방법 및 절차에 관한 사항을 디지털콘텐츠이용자보호지침에 따라 처리합니다.\n" +
                "제32조(면책조항) ① \"STYLEMAP”은 천재지변 또는 이에 준하는 불가항력으로 인하여 \"콘텐츠\"를 제공할 수 없는 경우에는 \"콘텐츠\" 제공에 관한 책임이 면제됩니다. \n" +
                "② \"STYLEMAP”은 \"이용자\"의 귀책사유로 인한 콘텐츠이용의 장애에 대하여는 책임을 지지 않습니다. \n" +
                "③ \"STYLEMAP”은 \"회원\"이 \"콘텐츠\"와 관련하여 게재한 정보, 자료, 사실의 신뢰도, 정확성 등의 내용에 관하여는 책임을 지지 않습니다. \n" +
                "④ \"STYLEMAP”은 \"이용자\" 상호간 또는 \"이용자\"와 제3자 간에 \"콘텐츠\"를 매개로 하여 발생한 분쟁 등에 대하여 책임을 지지 않습니다.\n" +
                "제33조(분쟁의 해결) \"STYLEMAP”은 분쟁이 발생하였을 경우에 \"이용자\"가 제기하는 정당한 의견이나 불만을 반영하여 적절하고 신속한 조치를 취합니다. 다만, 신속한 처리가 곤란한 경우에 \"STYLEMAP”은 \"이용자\"에게 그 사유와 처리일정을 통보합니다.\n" +
                "\n" +
                "\n" +
                "부칙\n" +
                "이 약관은 2015년 9월 10일부터 적용됩니다.\n" +
                "본 약관에 문의사항이 있는 경우에는 info@wearit.kr로 문의주세요.";

        return data.replaceAll("STYLEMAP","WEARIT");
    }

    public String getGaein(){
        String data = "STYLEMAP은 (이하 \"회사\"는) 고객님의 개인정보를 중요시하며, \"정보통신망 이용촉진 및 정보보호\"에 관한 법률을 준수하고 있습니다.\n" +
                "회사는 개인정보취급방침을 통하여 고객님께서 제공하시는 개인정보가 어떠한 용도와 방식으로 이용되고 있으며, 개인정보보호를 위해 어떠한 조치가 취해지고 있는지 알려드립니다.\n" +
                "■ 수집하는 개인정보 항목 및 수집방법\n" +
                "\n" +
                "가. 수집하는 개인정보의 항목\n" +
                "\n" +
                "o 회사는 회원가입, 상담, 서비스 신청 등을 위해 아래와 같은 개인정보를 수집하고 있습니다.\n" +
                "- 회원가입시 : 이메일 주소, 비밀번호, 전화번호\n" +
                "- 서비스 신청시 : 이름, 주소, 결제 정보 \n" +
                "o 서비스 이용 과정이나 사업 처리 과정에서 서비스이용기록, 접속로그, 쿠키, 접속 IP, 결제 기록, 불량이용 기록이 생성되어 수집될 수 있습니다. \n" +
                "\n" +
                "나. 수집방법\n" +
                "\n" +
                "- STYLEMAP 회원가입 시 수집\n" +
                "- 물품 구매 시 수집\n" +
                "\n" +
                "■ 개인정보의 수집 및 이용목적\n" +
                "\n" +
                "o 서비스 제공에 관한 계약 이행 및 서비스 제공에 따른 요금정산\n" +
                "콘텐츠 제공, 구매 및 요금 결제, 물품배송 또는 청구지 등 발송, 금융거래 본인 인증 및 금융 서비스\n" +
                "o 회원 관리\n" +
                "회원제 서비스 이용에 따른 본인확인, 개인 식별, 불량회원의 부정 이용 방지와 비인가 사용 방지,가입 의사 확인,연령확인 ,불만처리 등 민원처리, 고지사항 전달\n" +
                "o 마케팅 및 광고에 활용\n" +
                "이벤트 등 광고성 정보 전달,접속 빈도 파악 또는 회원의 서비스 이용에 대한 통계\n" +
                "\n" +
                "o 서비스 개선에 따른 피드백에 활용\n" +
                "서비스 개선을 위해 회원과의 커뮤니케이션 및 설문을 위해 활용 됨\n" +
                "\n" +
                "\n" +
                "■ 개인정보의 보유 및 이용기간\n" +
                "\n" +
                "원칙적으로, 개인정보 수집 및 이용목적이 달성된 후에는 해당 정보를 지체 없이 파기합니다. 단, 다음의 정보에 대해서는 아래의 이유로 명시한 기간 동안 보존합니다.\n" +
                "가. 회사 내부방침에 의한 정보보유 사유\n" +
                "\n" +
                "회원이 탈퇴한 경우에도 불량회원의 부정한 이용의 재발을 방지,분쟁해결 및 수사기관의 요청에 따른 협조를 위하여,이용계약 해지일로부터 3년간 회원의 정보를 보유할 수 있습니다. \n" +
                "\n" +
                "나. 관련 법령에 의한 정보 보유 사유 \n" +
                "\n" +
                "전자상거래등에서의소비자보호에관한법률 등 관계법령의 규정에 의하여 보존할 필요가 있는 경우 회사는 아래와 같이 관계법령에서 정한 일정한 기간 동안 회원정보를 보관합니다.\n" +
                "\n" +
                "o 계약 또는 청약철회 등에 관한 기록\n" +
                "-보존이유 : 전자상거래등에서의소비자보호에관한법률\n" +
                "-보존기간 : 5년\n" +
                "o 대금 결제 및 재화 등의 공급에 관한 기록\n" +
                "-보존이유: 전자상거래등에서의소비자보호에관한법률\n" +
                "-보존기간 : 5년 \n" +
                "o 소비자 불만 또는 분쟁처리에 관한 기록\n" +
                "-보존이유 : 전자상거래등에서의소비자보호에관한법률\n" +
                "-보존기간 : 3년 \n" +
                "o 로그 기록 \n" +
                "-보존이유: 통신비밀보호법\n" +
                "-보존기간 : 3개월\n" +
                "■ 개인정보의 파기절차 및 방법\n" +
                "\n" +
                "회사는 원칙적으로 개인정보 수집 및 이용목적이 달성된 후에는 해당 정보를 지체없이 파기합니다. 파기절차 및 방법은 다음과 같습니다.\n" +
                "\n" +
                "o 파기절차\n" +
                "회원님이 회원가입 등을 위해 입력하신 정보는 목적이 달성된 후 별도의 DB로 옮겨져(종이의 경우 별도의 서류함) 내부 방침 및 기타 관련 법령에 의한 정보보호 사유에 따라(보유 및 이용기간 참조) 일정 기간 저장된 후 파기 되어집니다.\n" +
                "별도 DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 보유되어지는 이외의 다른 목적으로 이용되지 않습니다.\n" +
                "o 파기방법\n" +
                "전자적 파일형태로 저장된 개인정보는 기록을 재생할 수 없는 기술적 방법을 사용하여 삭제합니다.\n" +
                "■ 개인정보 제공\n" +
                "\n" +
                "회사는 이용자의 개인정보를 원칙적으로 외부에 제공하지 않습니다. 다만, 아래의 경우에는 예외로 합니다.\n" +
                "o 이용자들이 사전에 동의한 경우\n" +
                "o 법령의 규정에 의거하거나 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우\n" +
                "■ 수집한 개인정보의 위탁\n" +
                "\n" +
                "회사는 서비스 이행을 위해 아래와 같이 외부 전문업체에 위탁하여 운영하고 있습니다.\n" +
                "\n" +
                "o 위탁 대상자 : 주문한 상품을 판매하는 편집샵 및 택배사\n" +
                "o 위탁 목적 : 구매자의 거래의 원활한 진행, 고객상담 및 불만처리, 상품과 경품 배송을 위한 배송지 확인 등\n" +
                "o 위탁 항목 : 구매자 이름, 전화번호, ID, 휴대폰번호, 상품 구매정보, 상품 수취인 정보 등\n" +
                "o 위탁 대상자 : KG이니시스\n" +
                "o 위탁업무 내용 : 결제 및 에스크로 서비스\n" +
                "■ 이용자 및 법정대리인의 권리와 그 행사방법\n" +
                "\n" +
                "o 이용자는 언제든지 등록되어 있는 자신의 개인정보를 조회하거나 수정할 수 있으며 가입해지를 요청할 수도 있습니다.\n" +
                "o 이용자들의 개인정보 조회,수정을 위해서는 \"개인정보변경\"(또는 \"회원정보수정\" 등)을 가입해지(동의철회)를 위해서는 \"회원탈퇴\"를 클릭하여 본인 확인 절차를 거치신 후 직접 열람,정정 또는 탈퇴가 가능합니다.\n" +
                "o 혹은 개인정보관리책임자에게 서면,전화 또는 이메일로 연락하시면 지체없이 조치하겠습니다.\n" +
                "o 귀하가 개인정보의 오류에 대한 정정을 요청하신 경우에는 정정을 완료하기 전까지 당해 개인정보를 이용 또는 제공하지 않습니다. 또한 잘못된 개인정보를 제3자에게 이미 제공한 경우에는 정정 처리결과를 제3자에게 지체없이 통지하여 정정이 이루어지도록 하겠습니다.\n" +
                "o 회사는 이용자의 요청에 의해 해지 또는 삭제된 개인정보는 \"회사가 수집하는 개인정보의 보유 및 이용기간\"에 명시된 바에 따라 처리하고 그 외의 용도로 열람 또는 이용할 수 없도록 처리하고 있습니다.\n" +
                "■ 개인정보 자동수집 장치의 설치, 운영 및 그 거부에 관한 사항\n" +
                "\n" +
                "회사는 귀하의 정보를 수시로 저장하고 찾아내는 \"쿠키(cookie)\" 등을 운용합니다. 쿠키란 웹사이트를 운영하는데 이용되는 서버가 귀하의 브라우저에 보내는 아주 작은 텍스트 파일로서 귀하의 컴퓨터 하드디스크에 저장됩니다.\n" +
                "회사은(는) 다음과 같은 목적을 위해 쿠키를 사용합니다.\n" +
                "\n" +
                "o 쿠키 등 사용 목적\n" +
                "1. 회원과 비회원의 접속 빈도나 방문 시간 등을 분석,이용자의 취향과 관심분야를 파악 및 자취 추적,각종 이벤트 참여 정도 및 방문 회수 파악 등을 통한 타겟 마케팅 및 개인 맞춤 서비스 제공\n" +
                "\n" +
                "\n" +
                "■ 개인정보에 관한 민원서비스\n" +
                "\n" +
                "회사는 고객의 개인정보를 보호하고 개인정보와 관련한 불만을 처리하기 위하여 아래와 같이 관련 부서 및 개인정보관리책임자를 지정하고 있습니다.\n" +
                "\n" +
                "o 개인정보관리담당부서 \n" +
                "부서명: CS고객센터\n" +
                "소속 : STYLEMAP\n" +
                "전화번호 : 02-6052-3031\n" +
                "이메일 : info@wearit.kr\n" +
                "\n" +
                "o 개인정보관리책임자 \n" +
                "성명 : 지세광\n" +
                "소속 : STYLEMAP\n" +
                "직책 : CEO\n" +
                "전화번호 : 02-6052-3031\n" +
                "이메일 : sekwang@wearit.kr";

        return data.replaceAll("STYLEMAP","WEARIT");
    }
}
