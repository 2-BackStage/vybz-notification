package back.vybz.notification_service.notification.presentation;

import back.vybz.notification_service.client.BuskerInfoClient;
import back.vybz.notification_service.client.UserInfoClient;
import back.vybz.notification_service.client.dto.UserSummary;
import back.vybz.notification_service.common.entity.BaseResponseEntity;
import back.vybz.notification_service.common.entity.BaseResponseStatus;
import back.vybz.notification_service.common.util.CursorPageUtil;
import back.vybz.notification_service.notification.application.NotificationService;
import back.vybz.notification_service.notification.dto.request.RequestCreateNotificationDto;
import back.vybz.notification_service.notification.dto.response.ResponseNotificationDto;
import back.vybz.notification_service.notification.vo.request.RequestCreateNotificationVo;
import back.vybz.notification_service.notification.vo.response.ResponseNotificationVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 알림 생성 API
     * @param requestCreateNotificationVo
     */
    @Operation(summary = "알림 생성 API", description = "알림을 생성하는 API 입니다.", tags = {"notification-Service"})
    @PostMapping
    public BaseResponseEntity<Void> sendNotification(@RequestBody RequestCreateNotificationVo requestCreateNotificationVo) {
        notificationService.sendNotification(RequestCreateNotificationDto.from(requestCreateNotificationVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    /**
     * 수신자 UUID로 알림 리스트 조회
     * @param receiverUuid
     * @param lastId
     * @param pageSize
     */
    @Operation(summary = "수신자 UUID로 알림 리스트 조회 API", description = "수신자 UUID로 알림 리스트 조회 API 입니다.", tags = {"notification-Service"})
    @GetMapping("/search")
    public BaseResponseEntity<CursorPageUtil<ResponseNotificationVo, String>> getNotificationByReceiverUuidWithCursor(
            @RequestParam String receiverUuid,
            @RequestParam(required = false) String lastId,
            @RequestParam(defaultValue = "20") int pageSize) {

        CursorPageUtil<ResponseNotificationDto, String> result = notificationService.getNotificationsByReceiverUuidWithCursor(receiverUuid, lastId, pageSize);

        return new BaseResponseEntity<>(result.map(ResponseNotificationDto::toVo));
    }

    /**
     * 알림 읽음 처리
     * @param notificationId
     */
    @Operation(summary = "알림 읽음 처리 API", description = "알림을 읽음 처리하는 API 입니다.", tags = {"notification-Service"})
    @PutMapping("/{notificationId}")
    public BaseResponseEntity<Void> markAsRead(@PathVariable("notificationId") String notificationId) {
        notificationService.markAsRead(notificationId);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    /**
     * 알림 삭제
     * @param notificationId
     */
    @Operation(summary = "알림 삭제 API", description = "알림을 삭제하는 API 입니다.", tags = {"notification-Service"})
    @DeleteMapping("/{notificationId}")
    public BaseResponseEntity<Void> deleteNotification(@PathVariable("notificationId") String notificationId) {
        notificationService.deleteNotification(notificationId);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

}
