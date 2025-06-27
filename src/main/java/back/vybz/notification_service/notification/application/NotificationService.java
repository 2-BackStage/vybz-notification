package back.vybz.notification_service.notification.application;

import back.vybz.notification_service.common.util.CursorPageUtil;
import back.vybz.notification_service.notification.dto.request.RequestCreateNotificationDto;
import back.vybz.notification_service.notification.dto.response.ResponseNotificationDto;

public interface NotificationService {

    /**
     * 알림 생성
     * @param requestCreateNotificationDto
     */
    void sendNotification(RequestCreateNotificationDto requestCreateNotificationDto);

    /**
     * 수신자 UUID로 알림 조회 (커서 페이징)
     * @param receiverUuid
     */
    CursorPageUtil<ResponseNotificationDto, String> getNotificationsByReceiverUuidWithCursor(String receiverUuid, String lastId, int pageSize);

    /**
     * 알림을 읽음 처리
     * @param notificationId
     */
    void markAsRead(String notificationId);

    /**
     * 알림 삭제
     * @param notificationId
     */
    void deleteNotification(String notificationId);

}
