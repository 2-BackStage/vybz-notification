package back.vybz.notification_service.kafka.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowNotificationEvent {

    private String userUuid;
    private String buskerUuid;

    @Builder
    public FollowNotificationEvent(String userUuid, String buskerUuid) {
        this.userUuid = userUuid;
        this.buskerUuid = buskerUuid;
    }

}
