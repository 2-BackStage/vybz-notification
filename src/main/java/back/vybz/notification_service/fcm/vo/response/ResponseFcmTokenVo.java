package back.vybz.notification_service.fcm.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseFcmTokenVo {

    private String receiverUuid;
    private String token;

    @Builder
    public ResponseFcmTokenVo(String receiverUuid, String token) {
        this.receiverUuid = receiverUuid;
        this.token = token;
    }

}
