package back.vybz.notification_service.fcm.dto.request;

import back.vybz.notification_service.fcm.domain.FcmToken;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestFcmTokenDto {

    private String receiverUuid;
    private String token;

    @Builder
    public RequestFcmTokenDto(String receiverUuid, String token) {
        this.receiverUuid = receiverUuid;
        this.token = token;
    }

    public FcmToken toDocument() {
        return FcmToken.builder()
                .receiverUuid(receiverUuid)
                .token(token)
                .build();
    }

}
