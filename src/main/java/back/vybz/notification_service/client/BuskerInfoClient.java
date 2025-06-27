package back.vybz.notification_service.client;

import back.vybz.notification_service.client.dto.UserSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "busker-info-client", url = "${service.busker-info.url}")
public interface BuskerInfoClient {

    @GetMapping("/internal/busker-info/{buskerUuid}")
    UserSummary getBuskerSummary(@PathVariable("buskerUuid") String buskerUuid);

}
