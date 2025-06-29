package back.vybz.notification_service.client;

import back.vybz.notification_service.client.dto.UserSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "busker-info-client", url = "${service.busker-info.url}")
public interface BuskerInfoClient {

    @GetMapping("/internal/busker-info/{buskerUuid}")
    UserSummary getBuskerSummary(@PathVariable("buskerUuid") String buskerUuid);

    @PostMapping("/internal/busker-info/summary-bulk")
    Map<String, UserSummary> getUserSummaryBulk(@RequestBody List<String> buskerUuid);

}