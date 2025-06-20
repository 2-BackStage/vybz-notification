package back.vybz.notificationservice.notification.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    LIKE("좋아요"),
    COMMENT("댓글"),
    FEED("피드"),
    FOLLOW("팔로우"),
    MENTION("멘션"),
    SYSTEM("시스템"),
    CHAT("채팅"),
    DONATION("후원");

    private final String description;
}
