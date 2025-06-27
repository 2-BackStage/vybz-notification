package back.vybz.notification_service.common.util;

import back.vybz.notification_service.notification.domain.NotificationType;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotificationContentFormatter {

    public static String format(NotificationType notificationType, String senderNickname) {
        return switch (notificationType) {
            case LIKE -> senderNickname + " 님이 좋아요를 눌렀습니다.";
            case COMMENT -> senderNickname + " 님이 댓글을 남겼습니다.";
            case FEED -> senderNickname + " 님이 피드를 작성했습니다.";
            case FOLLOW -> senderNickname + " 님이 팔로우했습니다.";
            case MENTION -> senderNickname + " 님이 멘션했습니다.";
            case SYSTEM -> "시스템 알림이 도착했습니다.";
            case CHAT -> senderNickname + " 님이 메시지를 보냈습니다.";
            case DONATION -> senderNickname + " 님이 후원했습니다.";
        };
    }

}
