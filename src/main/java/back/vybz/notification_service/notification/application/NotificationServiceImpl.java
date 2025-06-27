package back.vybz.notification_service.notification.application;

import back.vybz.notification_service.common.entity.BaseResponseStatus;
import back.vybz.notification_service.common.exception.BaseException;
import back.vybz.notification_service.common.util.CursorPageUtil;
import back.vybz.notification_service.fcm.application.FcmService;
import back.vybz.notification_service.notification.domain.Notification;
import back.vybz.notification_service.notification.dto.request.RequestCreateNotificationDto;
import back.vybz.notification_service.notification.dto.response.ResponseNotificationDto;
import back.vybz.notification_service.notification.infrastructure.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;

    /**
     * 알림 생성
     * @param requestCreateNotificationDto
     */
    @Override
    public void sendNotification(RequestCreateNotificationDto requestCreateNotificationDto) {
        notificationRepository.save(requestCreateNotificationDto.toEntity());
        fcmService.sendFcm(requestCreateNotificationDto.getReceiverUuid(), requestCreateNotificationDto.getNotificationType(),
                requestCreateNotificationDto.getContent(), requestCreateNotificationDto.getTargetId());
    }

    /**
     * 수신자 UUID로 알림 조회 (커서 페이징)
     * @param receiverUuid
     * @param lastId
     * @param pageSize
     */
    @Override
    public CursorPageUtil<ResponseNotificationDto, String> getNotificationsByReceiverUuidWithCursor(String receiverUuid, String lastId, int pageSize) {
        List<Notification> notifications = notificationRepository.findByReceiverUuidWithCursor(receiverUuid, lastId, pageSize + 1);

        boolean hasNext = notifications.size() > pageSize;
        if (hasNext) {
            notifications = notifications.subList(0, pageSize);
        }

        List<ResponseNotificationDto> dto = notifications.stream()
                .map(ResponseNotificationDto::from)
                .toList();

        String nextCursor = hasNext ? notifications.get(notifications.size() - 1).getId() : null;

        return CursorPageUtil.<ResponseNotificationDto, String>builder()
                .content(dto)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .pageSize(pageSize)
                .build();

    }

    /**
     * 알림을 읽음 처리
     * @param notificationId
     */
    @Override
    public void markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOTIFICATION_NOT_FOUND));
        if (!notification.isRead()) {
            notification.markAsRead();
            notificationRepository.save(notification);
        }
    }

    /**
     * 알림 삭제
     * @param notificationId
     */
    @Override
    public void deleteNotification(String notificationId) {
       Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOTIFICATION_NOT_FOUND));
        notificationRepository.delete(notification);
    }

}
