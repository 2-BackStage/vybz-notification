package back.vybz.notificationservice.notification.domain.mongodb;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Document
public class Notifications {

    @Id
    private ObjectId id;

    // 알림 수신자 uuid
    @Field(name = "receiver_uuid")
    private String receiverUuid;

    //알림 수신자 타입
    @Field(name = "receiver_type")
    private ReceiverType receiverType;

    //알림 발신자 uuid
    @Field(name = "sender_uuid")
    private String senderUuid;

    //알림 발신자 타입
    @Field(name = "sender_type")
    private SenderType senderType;

    //알림 타입
    @Field(name = "notification_type")
    private NotificationType notificationType;

    //알림 내용
    @Field(name = "notification_content")
    private String notificationContent;

    //target id
    @Field(name = "target_id")
    private ObjectId targetId;

    //target type
    @Field(name = "target_type")
    private TargetType targetType;

    //알림 읽음 여부
    @Field(name = "is_read")
    private Boolean isRead = false;

    //알림 생성 일시
    @CreatedDate
    @Field(name = "created_at")
    private Instant createdAt;

    @Builder
    public Notifications(ObjectId id,
                         String receiverUuid,
                         ReceiverType receiverType,
                         String senderUuid,
                         SenderType senderType,
                         NotificationType notificationType,
                         String notificationContent,
                         ObjectId targetId,
                         TargetType targetType,
                         Boolean isRead) {
        this.id = id;
        this.receiverUuid = receiverUuid;
        this.receiverType = receiverType;
        this.senderUuid = senderUuid;
        this.senderType = senderType;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
        this.targetId = targetId;
        this.targetType = targetType;
        this.isRead = isRead;
    }
}
