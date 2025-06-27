package back.vybz.notification_service.notification.infrastructure;

import back.vybz.notification_service.common.util.MongoCursorPageHelper;
import back.vybz.notification_service.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    /**
     * 알림 페이징 조회
     * @param receiverUuid
     * @param lastId
     * @param pageSize
     */
    @Override
    public List<Notification> findByReceiverUuidWithCursor(String receiverUuid, String lastId, int pageSize) {
        Query baseQuery = new Query();
        baseQuery.addCriteria(Criteria.where("receiverUuid").is(receiverUuid));

        Query finalQuery = MongoCursorPageHelper.build(baseQuery, lastId, pageSize);

        log.info("알림 페이징 조회 - userUuid={}, pageSize={}, lastId={}", receiverUuid, pageSize, lastId);

        return mongoTemplate.find(finalQuery, Notification.class);
    }
}
