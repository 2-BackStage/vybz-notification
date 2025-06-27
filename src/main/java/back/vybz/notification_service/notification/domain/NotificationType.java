package back.vybz.notification_service.notification.domain;

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

    public boolean isFromBusker() {
        return switch (this) {
            case FEED, MENTION -> true;
            case DONATION -> false;
            default -> false; // 그 외는 일반 유저 기본 처리
        };
    }

    public boolean isFromUser() {
        return switch (this) {
            case DONATION -> true;
            case FEED, MENTION -> false;
            default -> true; // 나머지는 둘 다 가능 → 기본 true로 처리
        };
    }

}
