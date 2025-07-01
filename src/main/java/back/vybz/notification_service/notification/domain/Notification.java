package back.vybz.notification_service.notification.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Document(collection = "notification")
@CompoundIndexes({
        // 알림 목록 조회용 인덱스 (receiver + deleted + createdAt DESC)
        @CompoundIndex(name = "idx_receiver_deleted_createdAt", def = "{'receiverUuid': 1, 'deleted': 1, 'createdAt': -1}"),

        // 읽지 않은 알림 카운트용 인덱스 (receiver + read + deleted)
        @CompoundIndex(name = "idx_receiver_read_deleted", def = "{'receiverUuid': 1, 'read': 1, 'deleted': 1}")
})
public class Notification {

    @Id
    private String id;

    /**
     * 발신자 uuid
     */
    @Field(name = "sender_uuid")
    private String senderUuid;

    /**
     * 수신자 uuid
     */
    @Field(name = "receiver_uuid")
    private String receiverUuid;

    /**
     * 알림 타입
     */
    @Field(name = "notification_type")
    private NotificationType notificationType;

    /**
     * 알림 내용
     */
    @Field(name = "content")
    private String content;

    /**
     * 서브 내용
     */
    @Field(name = "sub_content")
    private String subContent;

    /**
     * 타겟 id
     */
    @Field(name = "target_id")
    private String targetId;

    /**
     * 읽음 여부
     */
    @Field(name = "read")
    private boolean read;

    /**
     * 삭제 여부
     */
    @Field(name = "deleted")
    private boolean deleted;

    @CreatedDate
    @Field(name = "created_at")
    private Instant createdAt;

    public void markAsRead() {
        this.read = true;
    }

    @Builder
    public Notification(String id, String senderUuid, String receiverUuid, NotificationType notificationType, String content, String subContent, String targetId, boolean read, boolean deleted, Instant createdAt) {
        this.id = id;
        this.senderUuid = senderUuid;
        this.receiverUuid = receiverUuid;
        this.notificationType = notificationType;
        this.content = content;
        this.subContent = subContent;
        this.targetId = targetId;
        this.read = read;
        this.deleted = deleted;
        this.createdAt = createdAt;
    }

}
