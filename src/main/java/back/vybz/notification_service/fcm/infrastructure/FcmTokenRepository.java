package back.vybz.notification_service.fcm.infrastructure;

import back.vybz.notification_service.fcm.domain.FcmToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FcmTokenRepository extends MongoRepository<FcmToken, String> {

    /**
     * receiverUuid로 FcmToken 조회
     * @param receiverUuid
     */
    Optional<FcmToken> findByReceiverUuid(String receiverUuid);

    Optional<Object> findByToken(String fcmTokenValue);
}
