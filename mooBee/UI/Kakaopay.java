package UI;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Kakaopay {

    private static final String API_URL = "https://kapi.kakao.com/v1/payment/ready"; // 카카오페이 결제 준비 API URL
    private static final String ACCESS_TOKEN = "DEV14DA0C9AF3BE7D8F9DBEEB702D63A18E4B62F"; // 카카오 API 엑세스 토큰

    public static void main(String[] args) {
        try {
            // API 요청을 위한 URL 객체 생성
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // HTTP 메소드와 헤더 설정
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            connection.setDoOutput(true);
            
            // 요청 파라미터 설정 
            String requestBody = "cid=TC0ONETIME&partner_order_id=order_id_1234&partner_user_id=user_id_1234&item_name=Item_Name&quantity=1&total_amount=1000&tax_free_amount=0&approval_url=https://yourdomain.com/success&fail_url=https://yourdomain.com/fail&cancel_url=https://yourdomain.com/cancel";
            
            // 요청 데이터 전송
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes("UTF-8"));
                os.flush();
            }
            
            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            
            // 응답 데이터 읽기
            try (InputStream is = connection.getInputStream()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
