package back.vybz.notification_service.fcm.presentation;

import back.vybz.notification_service.common.entity.BaseResponseEntity;
import back.vybz.notification_service.common.entity.BaseResponseStatus;
import back.vybz.notification_service.fcm.application.FcmService;
import back.vybz.notification_service.fcm.dto.request.RequestFcmTokenDto;
import back.vybz.notification_service.fcm.dto.response.ResponseFcmTokenDto;
import back.vybz.notification_service.fcm.vo.response.ResponseFcmTokenVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm-token")
public class FcmTokenController {

    private final FcmService fcmService;

    /**
     * FCM 토큰 저장/업데이트
     * @param requestFcmTokenDto
     */
    @Operation(summary = "FCM 토큰 저장/업데이트 API", description = "사용자의 FCM 토큰을 저장하거나 업데이트하는 API 입니다.", tags = {"fcm-Service"})
    @PostMapping
    public BaseResponseEntity<Void> saveOrUpdateFcmToken(@RequestBody RequestFcmTokenDto requestFcmTokenDto) {
        fcmService.createOrUpdateFcmToken(requestFcmTokenDto);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    /**
     * 수신자 UUID로 FCM 토큰 조회
     * @param receiverUuid
     */
    @Operation(summary = "수신자 UUID로 FCM 토큰 조회 API", description = "수신자 UUID로 FCM 토큰을 조회하는 API 입니다.", tags = {"fcm-Service"})
    @GetMapping("/{receiverUuid}")
    public BaseResponseEntity<ResponseFcmTokenVo> getFcmTokenByReceiverUuid(@PathVariable("receiverUuid") String receiverUuid) {
        ResponseFcmTokenDto responseFcmTokenDto = fcmService.getTokenByReceiverUuid(receiverUuid);
        return new BaseResponseEntity<>(responseFcmTokenDto.toVo());
    }

}
