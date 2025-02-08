package kr.co.yna.cms.v2.yna.contents.application;

import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import kr.co.yna.cms.v2.yna.contents.domain.entity.ContentIdSeq;
import kr.co.yna.cms.v2.yna.contents.domain.entity.ContentType;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Department;
import kr.co.yna.cms.v2.yna.contents.domain.repository.ContentIdSeqRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class ContentIdFactory {
    private final ContentIdSeqRepository contentIdSeqRepository;

    public ContentIdFactory(ContentIdSeqRepository contentIdSeqRepository) {
        this.contentIdSeqRepository = contentIdSeqRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Content.ID create(Content.Attribute contentsAttribute, ContentType contentType, Department department) {
        Assert.notNull(contentsAttribute, "콘텐츠 속성이 null일 수 없습니다!!!");
        Assert.notNull(contentType, "콘텐츠 타입이 null일 수 없습니다!!!");
        Assert.notNull(department, "부서가 null일 수 없습니다.");

        ContentIdSeq.Key key = new ContentIdSeq.Key(contentsAttribute, contentType);

        Optional<ContentIdSeq> optContentsIdSeq = this.contentIdSeqRepository.findByKey(key);
        optContentsIdSeq.ifPresent(ContentIdSeq::increaseSeq);

        return this.contentIdSeqRepository.save(optContentsIdSeq.orElse(new ContentIdSeq(key)))
                .newContentsId(department);
    }
}
