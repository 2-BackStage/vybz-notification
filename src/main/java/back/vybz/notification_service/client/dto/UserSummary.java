package back.vybz.notification_service.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSummary {

    private String uuid;
    private String nickname;
    private String profileImageUrl;

}
