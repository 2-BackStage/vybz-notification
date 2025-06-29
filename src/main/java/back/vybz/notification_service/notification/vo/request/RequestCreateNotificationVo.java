package back.vybz.notification_service.notification.vo.request;

import back.vybz.notification_service.notification.domain.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestCreateNotificationVo {

    private String senderUuid;
    private String receiverUuid;
    private NotificationType notificationType;
    private String targetId;

}
