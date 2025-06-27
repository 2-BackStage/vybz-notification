package back.vybz.notification_service.notification.infrastructure;

import back.vybz.notification_service.notification.domain.Notification;

import java.util.List;

public interface NotificationRepositoryCustom {

    /**
     * 수신자 UUID로 알림 조회 (커서 기반 페이징)
     * @param receiverUuid
     * @param lastId
     * @param pageSize
     * @return
     */
    List<Notification> findByReceiverUuidWithCursor(String receiverUuid, String lastId, int pageSize);

}
