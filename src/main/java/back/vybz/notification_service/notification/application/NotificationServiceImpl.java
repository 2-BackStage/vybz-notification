package back.vybz.notification_service.notification.application;

import back.vybz.notification_service.client.BuskerInfoClient;
import back.vybz.notification_service.client.UserInfoClient;
import back.vybz.notification_service.client.dto.UserSummary;
import back.vybz.notification_service.common.entity.BaseResponseStatus;
import back.vybz.notification_service.common.exception.BaseException;
import back.vybz.notification_service.common.util.CursorPageUtil;
import back.vybz.notification_service.common.util.FcmUrlResolver;
import back.vybz.notification_service.fcm.application.FcmService;
import back.vybz.notification_service.notification.domain.Notification;
import back.vybz.notification_service.notification.dto.request.RequestCreateNotificationDto;
import back.vybz.notification_service.notification.dto.response.ResponseNotificationDto;
import back.vybz.notification_service.notification.infrastructure.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;
    private final FcmUrlResolver fcmUrlResolver;
    private final UserInfoClient userInfoClient;
    private final BuskerInfoClient buskerInfoClient;

    /**
     * 알림 생성
     * @param requestCreateNotificationDto
     */
    @Override
    public void sendNotification(RequestCreateNotificationDto requestCreateNotificationDto) {
        fcmService.sendFcm(requestCreateNotificationDto.getReceiverUuid(), requestCreateNotificationDto.getNotificationType(),
                requestCreateNotificationDto.getContent(), requestCreateNotificationDto.getTargetId());
        notificationRepository.save(requestCreateNotificationDto.toEntity());
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

        Set<String> senderUuids = notifications.stream()
                .map(Notification::getSenderUuid)
                .collect(Collectors.toSet());
        // 사용자, 버스커 정보 각각 요청
        Map<String, UserSummary> userInfoMap = userInfoClient.getUserSummaryBulk(new ArrayList<>(senderUuids));
        Map<String, UserSummary> buskerInfoMap = buskerInfoClient.getUserSummaryBulk(new ArrayList<>(senderUuids));

        // 병합: user 우선, 없다면 busker 사용
        Map<String, UserSummary> mergedMap = new HashMap<>(buskerInfoMap);
        mergedMap.putAll(userInfoMap);

        List<ResponseNotificationDto> dto = notifications.stream()
                .map(n -> {
                    UserSummary sender = mergedMap.get(n.getSenderUuid());
                    return ResponseNotificationDto.from(n, sender, fcmUrlResolver);
                })
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
