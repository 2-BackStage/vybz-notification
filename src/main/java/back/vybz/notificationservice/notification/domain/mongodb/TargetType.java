package back.vybz.notificationservice.notification.domain.mongodb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TargetType {
    FEED("피드"),
    COMMENT("댓글"),
    USER("유저"),
    SCHEDULE("스케줄"),
    SYSTEM("시스템");

    private final String description;
}
