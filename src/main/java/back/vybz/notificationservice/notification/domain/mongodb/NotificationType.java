package back.vybz.notificationservice.notification.domain.mongodb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    LIKE("좋아요"),
    COMMENT("댓글"),
    REPLY("답글"),
    FOLLOW("팔로우"),
    MENTION("멘션"),
    SYSTEM("시스템"),
    DONATION("후원");

    private final String description;
}
