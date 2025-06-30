package back.vybz.notification_service.fcm.application;

import back.vybz.notification_service.common.entity.BaseResponseStatus;
import back.vybz.notification_service.common.exception.BaseException;
import back.vybz.notification_service.common.util.FcmSenderUtil;
import back.vybz.notification_service.fcm.domain.FcmToken;
import back.vybz.notification_service.fcm.dto.request.RequestFcmTokenDto;
import back.vybz.notification_service.fcm.dto.response.ResponseFcmTokenDto;
import back.vybz.notification_service.fcm.infrastructure.FcmTokenRepository;
import back.vybz.notification_service.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService {

    private final FcmTokenRepository fcmTokenRepository;
    private final FcmSenderUtil fcmSenderUtil;

    /**
     * FCM 알림 전송
     * @param receiverUuid
     * @param type
     * @param content
     * @param targetId
     */
    @Override
    public void sendFcm(String receiverUuid, NotificationType type, String content, String targetId) {
        fcmTokenRepository.findByReceiverUuid(receiverUuid).ifPresentOrElse(
                fcmToken -> {
                    try {
                        fcmSenderUtil.send(
                                fcmToken.getToken(),
                                "VYBZ 알림",
                                content,
                                Map.of("targetId", targetId)
                        );
                        log.info("📨 FCM 전송 완료: receiver={}, type={}, targetId={}", receiverUuid, type, targetId);
                    } catch (Exception e) {
                        log.error("🚨 FCM 전송 실패: {}", e.getMessage(), e);
                    }
                },
                () -> log.warn("⚠️ FCM 토큰 없음: receiver={}", receiverUuid)
        );
    }

    /**
     * FCM 토큰 생성 또는 업데이트
     * @param requestFcmTokenDto
     */
    @Override
    public void createOrUpdateFcmToken(RequestFcmTokenDto requestFcmTokenDto) {
        fcmTokenRepository.findByReceiverUuid(requestFcmTokenDto.getReceiverUuid())
                .ifPresentOrElse(existing -> existing.updateToken(requestFcmTokenDto.getToken()),
                        () -> fcmTokenRepository.save(requestFcmTokenDto.toDocument()));
    }

    /**
     * 수신자 UUID로 FCM 토큰 조회
     * @param receiverUuid
     */
    @Override
    public ResponseFcmTokenDto getTokenByReceiverUuid(String receiverUuid) {
        FcmToken fcmToken = fcmTokenRepository.findByReceiverUuid(receiverUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FCM_TOKEN_NOT_FOUND));
        return ResponseFcmTokenDto.from(fcmToken);
    }
}