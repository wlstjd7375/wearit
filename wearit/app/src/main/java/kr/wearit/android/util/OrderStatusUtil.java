package kr.wearit.android.util;

/**
 * Created by KimJS on 2016-10-09.
 */
public class OrderStatusUtil {
    public static String getStatus (String status) {
        String result = "";
        if(status.equals("wait")){
            result = "입금대기중";
        }
        else if(status.equals("pay")){
            result = "결제완료";
        }
        else if(status.equals("ready")){
            result = "상품준비중";
        }
        else if(status.equals("division")){
            result = "일부발송완료";
        }
        else if(status.equals("send")){
            result = "발송완료";
        }
        else if(status.equals("receive")){
            result = "배송완료";
        }
        else if(status.equals("exchange")){
            result = "교환신청";
        }
        else if(status.equals("excomplete")){
            result = "교환완료";
        }
        else if(status.equals("refund")){
            result = "환불신청";
        }
        else if(status.equals("recomplete")){
            result = "환불완료";
        }
        else if(status.equals("cancel")){
            result = "주문취소";
        }
        else if(status.equals("error")){
            result = "에러";
        }
        return result;
    }
}
