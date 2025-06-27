package back.vybz.notification_service.kafka.consumer;

import back.vybz.notification_service.client.UserInfoClient;
import back.vybz.notification_service.client.dto.UserSummary;
import back.vybz.notification_service.common.util.FcmUrlResolver;
import back.vybz.notification_service.common.util.NotificationContentFormatter;
import back.vybz.notification_service.kafka.event.FollowNotificationEvent;
import back.vybz.notification_service.notification.application.NotificationService;
import back.vybz.notification_service.notification.domain.NotificationType;
import back.vybz.notification_service.notification.dto.request.RequestCreateNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowNotificationEventConsumer {

    private final NotificationService notificationService;
    private final UserInfoClient userInfoClient;
    private final FcmUrlResolver fcmUrlResolver;

    @KafkaListener(
            topics = "create-follow",
            groupId = "follow-notification-group",
            containerFactory = "followNotificationKafkaListenerContainerFactory"
    )
    public void consume(FollowNotificationEvent event) {
        log.info("📨 Kafka에서 알림 이벤트 수신: {}", event);

        UserSummary sender;

        try {
            sender = userInfoClient.getUserSummary(event.getUserUuid());
        } catch (Exception e) {
            log.error("🚨 유저 정보 조회 실패 (uuid: {}): {}", event.getUserUuid(), e.getMessage(), e);
            return;
        }

        String content = NotificationContentFormatter.format(NotificationType.FOLLOW, sender.getNickname());

        RequestCreateNotificationDto dto = RequestCreateNotificationDto
                .builder()
                .senderUuid(sender.getUuid())
                .receiverUuid(event.getBuskerUuid())
                .notificationType(NotificationType.FOLLOW)
                .content(content)
                .targetId(fcmUrlResolver.resolveUrl(NotificationType.FOLLOW, event.getUserUuid()))
                .read(false)
                .deleted(false)
                .createdAt(Instant.now())
                .build();

        notificationService.sendNotification(dto);
        log.info("📨 알림 생성 완료: receiver={}, targetId={}", event.getBuskerUuid(), event.getUserUuid());

    }

}
