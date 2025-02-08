package kr.co.yna.cms.v2.yna.lock.domain.repository;

import kr.co.yna.cms.v2.yna.lock.domain.entity.Lock;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface LockRepository extends Repository<Lock, String> {
    Optional<Lock> findByTargetIdAndTargetType(String targetId, Class<?> targetType);

    Lock save(Lock lock);

    void delete(Lock lock);
}
