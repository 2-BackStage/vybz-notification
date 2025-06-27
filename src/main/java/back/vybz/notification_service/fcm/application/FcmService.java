package back.vybz.notification_service.fcm.application;

import back.vybz.notification_service.fcm.dto.response.ResponseFcmTokenDto;
import back.vybz.notification_service.kafka.event.NotificationEvent;
import back.vybz.notification_service.notification.domain.NotificationType;
import back.vybz.notification_service.fcm.dto.request.RequestFcmTokenDto;

public interface FcmService {

    /**
     * FCM 알림 전송
     * @param receiverUuid
     * @param type
     * @param content
     * @param targetId
     */
    void sendFcm(String receiverUuid, NotificationType type, String content, String targetId);

    /**
     * FCM 토큰 생성 또는 업데이트
     * @param requestFcmTokenDto
     */
    void createOrUpdateFcmToken(RequestFcmTokenDto requestFcmTokenDto);

    /**
     * 수신자 UUID로 FCM 토큰 조회
     * @param receiverUuid
     */
    ResponseFcmTokenDto getTokenByReceiverUuid(String receiverUuid);

}
