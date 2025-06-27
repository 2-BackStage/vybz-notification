package back.vybz.notification_service.common.util;

import back.vybz.notification_service.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FcmUrlResolver {

    @Value("${fcm.base-url}")
    private String baseUrl;

    // TODO 프론트 url 확인 후 수정
    public String resolveUrl(NotificationType notificationType, String targetId) {
        return switch (notificationType) {
            case LIKE, FEED, COMMENT, MENTION -> baseUrl + "/feed/" + targetId;
            case FOLLOW -> baseUrl + "/profile/" + targetId;
            case SYSTEM -> baseUrl + "/system";
            case CHAT -> baseUrl + "/chat/" + targetId;
            case DONATION -> baseUrl + "/donation/" + targetId;
            default -> baseUrl;
        };
    }

}
