package back.vybz.notification_service.common.util;

import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@NoArgsConstructor
public class MongoCursorPageHelper {

    /**
     * 커서 기반 + 오프셋 기반 하이브리드 페이징 쿼리 구성
     *
     * @param baseCriteria 기본 조회 조건
     * @param lastId 커서 기반 _id (없으면 null)
     * @param pageSize 페이지 크기
     * @return 페이징이 적용된 Query
     */
    public static Query build(Query baseCriteria, String lastId, int pageSize) {

        // 커서 기반: _id > lastId
        if (lastId != null && !lastId.isBlank()) {
            baseCriteria.addCriteria(Criteria.where("_id").lt(new ObjectId(lastId)));
        }

        // 기본 정렬: _id 내림차순
        baseCriteria.with(Sort.by(Sort.Direction.DESC, "_id"));

        // +1 조회: hasNext 판단
        baseCriteria.limit(pageSize + 1);

        return baseCriteria;
    }

}
