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
    private String senderNickname;
    private String senderProfileImageUrl;
    private String subContent;
    private String targetUrl;
    private boolean read;
    private ZonedDateTime createdAt;

    @Builder
    public ResponseNotificationVo(String id, String senderUuid, String senderNickname, String senderProfileImageUrl,
                                  String subContent, String targetUrl, boolean read, ZonedDateTime createdAt) {
        this.id = id;
        this.senderUuid = senderUuid;
        this.senderNickname = senderNickname;
        this.senderProfileImageUrl = senderProfileImageUrl;
        this.subContent = subContent;
        this.targetUrl = targetUrl;
        this.read = read;
        this.createdAt = createdAt;
    }

}
