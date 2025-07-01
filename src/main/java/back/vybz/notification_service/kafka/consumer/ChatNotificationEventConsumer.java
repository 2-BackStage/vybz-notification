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
import java.util.*;
import java.util.stream.Collectors;

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
    public void consume(List<ChatNotificationEvent> events) {
        log.info("📨 Kafka에서 알림 이벤트 수신: {}", events);

        Set<String> senderUuids = events.stream()
                .map(ChatNotificationEvent::getSenderUuid)
                .collect(Collectors.toSet());

        // 1. userInfo, buskerInfo에서 각각 bulk 조회
        Map<String, UserSummary> userInfoMap = userInfoClient.getUserSummaryBulk(new ArrayList<>(senderUuids));
        Map<String, UserSummary> buskerInfoMap = buskerInfoClient.getUserSummaryBulk(new ArrayList<>(senderUuids));

        // 2. 병합 (user 우선, 없으면 busker)
        Map<String, UserSummary> merged = new HashMap<>(buskerInfoMap);
        merged.putAll(userInfoMap);

        // 3. 알림 생성
        for (ChatNotificationEvent event : events) {
            UserSummary sender = merged.get(event.getSenderUuid());
            if (sender == null) {
                log.warn("🚨 sender 정보 없음: uuid={}", event.getSenderUuid());
                continue;
            }

            String content = NotificationContentFormatter.format(NotificationType.CHAT, sender.getNickname());

            RequestCreateNotificationDto dto = RequestCreateNotificationDto
                    .builder()
                    .senderUuid(event.getSenderUuid())
                    .receiverUuid(event.getReceiverUuid())
                    .notificationType(NotificationType.CHAT)
                    .content(content)
                    .subContent(event.getContent())
                    .targetId(fcmUrlResolver.resolveUrl(NotificationType.CHAT, event.getChatRoomId()))
                    .read(false)
                    .deleted(false)
                    .createdAt(Instant.now())
                    .build();

            notificationService.sendNotification(dto);
            log.info("📨 채팅 알림 저장 완료: receiver={}, sender={}", event.getReceiverUuid(), event.getSenderUuid());

        }
    }

}
