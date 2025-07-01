package back.vybz.notification_service.common.util;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class FcmSenderUtil {

    public void send(String fcmToken, String title, String body, Map<String, String> data) {
        try {
            data.put("title", title);
            data.put("body", body);

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .putAllData(data)
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH)
                            .build())
                    .setApnsConfig(ApnsConfig.builder()
                            .setAps(Aps.builder().setContentAvailable(true).build())
                            .build())
                    .build();
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM 메시지 전송 성공: {}", response);
            log.info("보낼 메시지: title={}, body={}, data={}", title, body, data);
        } catch (Exception e) {
            log.error("FCM 메시지 전송 실패: {}", e.getMessage(), e);
            throw new RuntimeException("FCM 메시지 전송 실패", e);
        }
    }

}
