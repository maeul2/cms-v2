package kr.co.yna.cms.v2.yna.contents.domain.repository;

import jakarta.persistence.LockModeType;
import kr.co.yna.cms.v2.yna.contents.domain.entity.ContentIdSeq;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentIdSeqRepository extends org.springframework.data.repository.Repository<ContentIdSeq, ContentIdSeq.Key> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ContentIdSeq> findByKey(ContentIdSeq.Key key);

    ContentIdSeq save(ContentIdSeq idSeq);
}
