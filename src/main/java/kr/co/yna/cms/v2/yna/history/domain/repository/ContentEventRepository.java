package kr.co.yna.cms.v2.yna.history.domain.repository;

import kr.co.yna.cms.v2.yna.history.domain.entity.ContentEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentEventRepository extends EventRepository<ContentEvent> {
    @Override
    @Query("select max(e.seq) from ContentEvent e where e.sourceId = :sourceId")
    Optional<Integer> findMaxSeqBySourceId(String sourceId);
}
