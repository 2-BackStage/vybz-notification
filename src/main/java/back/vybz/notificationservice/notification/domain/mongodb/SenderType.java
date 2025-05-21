package back.vybz.notificationservice.notification.domain.mongodb;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SenderType {
    USER("유저"),
    BUSKER("버스커"),
    ADMIN("관리자");

    private final String description;
}
