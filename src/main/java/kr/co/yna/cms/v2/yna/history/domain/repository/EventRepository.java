package kr.co.yna.cms.v2.yna.history.domain.repository;

import kr.co.yna.cms.v2.yna.history.domain.entity.AbstractEvent;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface EventRepository<E extends AbstractEvent> extends Repository<E, String> {
    Iterable<E> saveAll(Iterable<E> events);

    List<E> findBySourceIdOrderByCreatedAt(String sourceId);

    List<E> findBySourceIdAndSeqLessThan(String sourceId, int seq);

    Optional<Integer> findMaxSeqBySourceId(String sourceId);
}
