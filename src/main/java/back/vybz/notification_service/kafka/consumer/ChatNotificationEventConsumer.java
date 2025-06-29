package back.vybz.notification_service.kafka.consumer;

import back.vybz.notification_service.client.BuskerInfoClient;
import back.vybz.notification_service.client.UserInfoClient;
import back.vybz.notification_service.client.dto.UserSummary;
import back.vybz.notification_service.common.util.FcmUrlResolver;
import back.vybz.notification_service.common.util.NotificationContentFormatter;
import back.vybz.notification_service.kafka.event.ChatNotificationEvent;
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
public class ChatNotificationEventConsumer {

    private final NotificationService notificationService;
    private final UserInfoClient userInfoClient;
    private final BuskerInfoClient buskerInfoClient;
    private final FcmUrlResolver fcmUrlResolver;

    @KafkaListener(
            topics = "chat-message",
            groupId = "chat-notification-group",
            containerFactory = "chatNotificationKafkaListenerContainerFactory"
    )
    public void consume(ChatNotificationEvent event) {
        log.info("📨 Kafka에서 알림 이벤트 수신: {}", event);

//        UserSummary sender = null;

//        try {
//            // 1. 우선 user로 조회 시도
//            sender = userInfoClient.getUserSummary(event.getSenderUuid());
//        } catch (Exception userEx) {
//            log.warn("🔍 일반 유저 정보 조회 실패, 버스커 조회 시도 (uuid: {})", event.getSenderUuid());
//            try {
//                sender = buskerInfoClient.getBuskerSummary(event.getSenderUuid());
//            } catch (Exception buskerEx) {
//                log.error("🚨 유저/버스커 모두 조회 실패 (uuid: {})", event.getSenderUuid(), buskerEx);
//                return;
//            }
//        }
//
//        String content = NotificationContentFormatter.format(NotificationType.CHAT, sender.getNickname());

        RequestCreateNotificationDto dto = RequestCreateNotificationDto
                .builder()
                .senderUuid(event.getSenderUuid())
                .receiverUuid(event.getReceiverUuid())
                .notificationType(NotificationType.CHAT)
                .targetId(fcmUrlResolver.resolveUrl(NotificationType.CHAT, event.getChatRoomId()))
                .read(false)
                .deleted(false)
                .createdAt(Instant.now())
                .build();

        notificationService.sendNotification(dto);
        log.info("📨 채팅 알림 저장 완료: receiver={}, sender={}", event.getReceiverUuid(), event.getSenderUuid());

    }

}
