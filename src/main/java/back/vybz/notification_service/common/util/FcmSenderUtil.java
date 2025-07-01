package back.vybz.notification_service.common.util;

import back.vybz.notification_service.fcm.domain.FcmToken;
import back.vybz.notification_service.fcm.infrastructure.FcmTokenRepository;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmSenderUtil {

    private final FcmTokenRepository fcmTokenRepository;

    public void send(String fcmToken, String title, String body, Map<String, String> data) {
        try {
            Map<String, String> modifiableData = new HashMap<>(data);
            modifiableData.put("title", title);
            modifiableData.put("body", body);

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .putAllData(modifiableData)
                    .setAndroidConfig(AndroidConfig.builder()
                            .setPriority(AndroidConfig.Priority.HIGH)
                            .build())
                    .setApnsConfig(ApnsConfig.builder()
                            .setAps(Aps.builder().setContentAvailable(true).build())
                            .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("📨 FCM 메시지 전송 성공: {}", response);
            log.info("보낼 메시지: title={}, body={}, data={}", title, body, modifiableData);

        } catch (FirebaseMessagingException e) {
            if ("UNREGISTERED".equals(e.getMessagingErrorCode().name())) {
                log.warn("📛 유효하지 않은 FCM 토큰입니다. 삭제 처리: {}", fcmToken);
                fcmTokenRepository.findByToken(fcmToken)
                        .ifPresent(token -> fcmTokenRepository.delete((FcmToken) token));
            } else {
                log.error("🚨 FCM 전송 실패 (FirebaseMessagingException): {}", e.getMessage(), e);
            }
            throw new RuntimeException("FCM 메시지 전송 실패", e);

        } catch (Exception e) {
            log.error("🚨 FCM 전송 실패 (기타 예외): {}", e.getMessage(), e);
            throw new RuntimeException("FCM 메시지 전송 실패", e);
        }
    }

}
