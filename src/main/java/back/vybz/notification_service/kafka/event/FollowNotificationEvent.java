package back.vybz.notification_service.kafka.event;

import lombok.Getter;

@Getter
public class FollowNotificationEvent {

    private String userUuid;
    private String buskerUuid;

}
