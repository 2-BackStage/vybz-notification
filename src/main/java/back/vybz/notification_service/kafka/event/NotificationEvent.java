package back.vybz.notification_service.kafka.event;

import back.vybz.notification_service.notification.domain.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
public class NotificationEvent {

    private String senderUuid;
    private String receiverUuid;
    private String content;
    private String targetId;
    private Instant sentAt;

    @Builder
    public NotificationEvent(String senderUuid, String receiverUuid, String content, String targetId, Instant sentAt) {
        this.senderUuid = senderUuid;
        this.receiverUuid = receiverUuid;
        this.content = content;
        this.targetId = targetId;
        this.sentAt = sentAt;
    }

}
