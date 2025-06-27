package back.vybz.notification_service.notification.infrastructure;

import back.vybz.notification_service.notification.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String>, NotificationRepositoryCustom {

}
