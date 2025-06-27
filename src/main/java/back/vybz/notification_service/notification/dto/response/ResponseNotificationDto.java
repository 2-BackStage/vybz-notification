package back.vybz.notification_service.notification.dto.response;

import back.vybz.notification_service.notification.domain.NotificationType;
import back.vybz.notification_service.notification.domain.Notification;
import back.vybz.notification_service.notification.vo.response.ResponseNotificationVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class ResponseNotificationDto {

    private String id;
    private String senderUuid;
    private String receiverUuid;
    private NotificationType notificationType;
    private String targetId;
    private String content;
    private boolean read;
    private ZonedDateTime createdAt;

    @Builder
    public ResponseNotificationDto(String id, String senderUuid, String receiverUuid, NotificationType notificationType, String targetId, String content, boolean read, ZonedDateTime createdAt) {
        this.id = id;
        this.senderUuid = senderUuid;
        this.receiverUuid = receiverUuid;
        this.notificationType = notificationType;
        this.targetId = targetId;
        this.content = content;
        this.read = read;
        this.createdAt = createdAt;
    }

    public static ResponseNotificationDto from(Notification notification) {
        ZonedDateTime kstTime = notification.getCreatedAt()
                .atZone(ZoneId.of("Asia/Seoul"));
        return ResponseNotificationDto.builder()
                .id(notification.getId())
                .senderUuid(notification.getSenderUuid())
                .receiverUuid(notification.getReceiverUuid())
                .notificationType(notification.getNotificationType())
                .targetId(notification.getTargetId())
                .content(notification.getContent())
                .read(notification.isRead())
                .createdAt(kstTime)
                .build();
    }

    public ResponseNotificationVo toVo() {
        return ResponseNotificationVo.builder()
                .id(id)
                .senderUuid(senderUuid)
                .receiverUuid(receiverUuid)
                .notificationType(notificationType)
                .targetId(targetId)
                .content(content)
                .read(read)
                .createdAt(createdAt)
                .build();
    }

}
