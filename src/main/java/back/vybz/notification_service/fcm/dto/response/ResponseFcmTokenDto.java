package back.vybz.notification_service.fcm.dto.response;

import back.vybz.notification_service.fcm.domain.FcmToken;
import back.vybz.notification_service.fcm.vo.response.ResponseFcmTokenVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseFcmTokenDto {

    private String receiverUuid;
    private String token;

    @Builder
    public ResponseFcmTokenDto(String receiverUuid, String token) {
        this.receiverUuid = receiverUuid;
        this.token = token;
    }

    public static ResponseFcmTokenDto from(FcmToken fcmToken) {
        return ResponseFcmTokenDto.builder()
                .receiverUuid(fcmToken.getReceiverUuid())
                .token(fcmToken.getToken())
                .build();
    }

    public ResponseFcmTokenVo toVo() {
        return ResponseFcmTokenVo.builder()
                .receiverUuid(receiverUuid)
                .token(token)
                .build();
    }

}
