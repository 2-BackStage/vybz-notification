package back.vybz.notification_service.client;

import back.vybz.notification_service.client.dto.UserSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-info-service", url = "${service.user-info.url}")
public interface UserInfoClient {

    @GetMapping("/internal/user-info/{userUuid}")
    UserSummary getUserSummary(@PathVariable("userUuid") String userUuid);

}