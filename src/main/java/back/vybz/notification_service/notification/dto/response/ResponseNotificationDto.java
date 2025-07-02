package back.vybz.notification_service.notification.dto.response;

import back.vybz.notification_service.client.dto.UserSummary;
import back.vybz.notification_service.common.util.FcmUrlResolver;
import back.vybz.notification_service.common.util.NotificationContentFormatter;
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
    private String senderNickname;
    private String senderProfileImageUrl;
    private String content;
    private String targetUrl;
    private boolean read;
    private ZonedDateTime createdAt;

    @Builder
    public ResponseNotificationDto(String id, String senderUuid, String senderNickname, String senderProfileImageUrl,
                                   String content, String targetUrl, boolean read, ZonedDateTime createdAt) {
        this.id = id;
        this.senderUuid = senderUuid;
        this.senderNickname = senderNickname;
        this.senderProfileImageUrl = senderProfileImageUrl;
        this.content = content;
        this.targetUrl = targetUrl;
        this.read = read;
        this.createdAt = createdAt;
    }

    public static ResponseNotificationDto from(Notification notification, UserSummary sender, FcmUrlResolver fcmUrlResolver) {
        ZonedDateTime kstTime = notification.getCreatedAt().atZone(ZoneId.of("Asia/Seoul"));
        String content = NotificationContentFormatter.format(notification.getNotificationType(), sender.getNickname());
        String targetUrl = fcmUrlResolver.resolveUrl(notification.getNotificationType(), notification.getTargetId());

        return ResponseNotificationDto.builder()
                .id(notification.getId())
                .senderUuid(sender.getUuid())
                .senderNickname(sender.getNickname())
                .senderProfileImageUrl(sender.getProfileImageUrl())
                .content(content)
                .targetUrl(targetUrl)
                .read(notification.isRead())
                .createdAt(kstTime)
                .build();
    }

    public ResponseNotificationVo toVo() {
        return ResponseNotificationVo.builder()
                .id(id)
                .senderUuid(senderUuid)
                .senderNickname(senderNickname)
                .senderProfileImageUrl(senderProfileImageUrl)
                .content(content)
                .targetUrl(targetUrl)
                .read(read)
                .createdAt(createdAt)
                .build();
    }

}
