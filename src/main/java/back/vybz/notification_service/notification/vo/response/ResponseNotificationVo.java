package back.vybz.notification_service.notification.vo.response;

import back.vybz.notification_service.notification.domain.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class ResponseNotificationVo {

    private String id;
    private String senderUuid;
    private String receiverUuid;
    private NotificationType notificationType;
    private String targetId;
    private String content;
    private boolean read;
    private ZonedDateTime createdAt;

    @Builder
    public ResponseNotificationVo(String id, String senderUuid, String receiverUuid, NotificationType notificationType, String targetId, String content, boolean read, ZonedDateTime createdAt) {
        this.id = id;
        this.senderUuid = senderUuid;
        this.receiverUuid = receiverUuid;
        this.notificationType = notificationType;
        this.targetId = targetId;
        this.content = content;
        this.read = read;
        this.createdAt = createdAt;
    }

}
