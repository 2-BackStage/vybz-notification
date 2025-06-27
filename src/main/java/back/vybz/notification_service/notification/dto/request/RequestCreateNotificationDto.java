package back.vybz.notification_service.notification.dto.request;

import back.vybz.notification_service.common.util.NotificationContentFormatter;
import back.vybz.notification_service.kafka.event.NotificationEvent;
import back.vybz.notification_service.notification.domain.NotificationType;
import back.vybz.notification_service.notification.domain.Notification;
import back.vybz.notification_service.notification.vo.request.RequestCreateNotificationVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class RequestCreateNotificationDto {

    private String senderUuid;
    private String receiverUuid;
    private NotificationType notificationType;
    private String content;
    private String targetId;
    private boolean read;
    private boolean deleted;
    private Instant createdAt;

    @Builder
    public RequestCreateNotificationDto(String senderUuid, String receiverUuid, NotificationType notificationType, String content, String targetId, boolean read, boolean deleted, Instant createdAt) {
        this.senderUuid = senderUuid;
        this.receiverUuid = receiverUuid;
        this.notificationType = notificationType;
        this.content = content;
        this.targetId = targetId;
        this.read = read;
        this.deleted = deleted;
        this.createdAt = createdAt;
    }

    public Notification toEntity() {
        return Notification.builder()
                .senderUuid(senderUuid)
                .receiverUuid(receiverUuid)
                .notificationType(notificationType)
                .content(content)
                .targetId(targetId)
                .read(read)
                .deleted(deleted)
                .createdAt(createdAt)
                .build();
    }

    public static RequestCreateNotificationDto from(RequestCreateNotificationVo requestCreateNotificationVo) {
        return RequestCreateNotificationDto.builder()
                .senderUuid(requestCreateNotificationVo.getSenderUuid())
                .receiverUuid(requestCreateNotificationVo.getReceiverUuid())
                .notificationType(requestCreateNotificationVo.getNotificationType())
                .content(requestCreateNotificationVo.getContent())
                .targetId(requestCreateNotificationVo.getTargetId())
                .read(false)
                .deleted(false)
                .createdAt(Instant.now())
                .build();
    }

}
