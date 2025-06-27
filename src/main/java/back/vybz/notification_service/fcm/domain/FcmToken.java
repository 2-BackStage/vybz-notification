package back.vybz.notification_service.fcm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor
@Document(collection = "fcm_token")
public class FcmToken {

    @Id
    private String id;

    /**
     * FCM 토큰을 수신하는 대상의 UUID
     */
    @Field(name = "receiver_uuid")
    @Indexed(unique = true)
    private String receiverUuid;

    /**
     * FCM 토큰
     */
    @Field(name = "token")
    private String token;

    public void updateToken(String newToken) {
        this.token = newToken;
    }

    @Builder
    public FcmToken(String id, String receiverUuid, String token) {
        this.id = id;
        this.receiverUuid = receiverUuid;
        this.token = token;
    }

}
