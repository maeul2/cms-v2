package kr.co.yna.cms.v2.yna.contents.application;

import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import kr.co.yna.cms.v2.yna.contents.domain.entity.ContentIdSeq;
import kr.co.yna.cms.v2.yna.contents.domain.entity.ContentType;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Department;
import kr.co.yna.cms.v2.yna.contents.domain.entity.article.Article;
import kr.co.yna.cms.v2.yna.contents.domain.repository.ContentIdSeqRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ContentIdFactoryTest {
    @InjectMocks
    ContentIdFactory contentIdFactory;

    @Mock
    ContentIdSeqRepository contentIdSeqRepository;

    @Test
    void create() {
        //given
        Content.Attribute contentAttribute = Content.Attribute.A;
        ContentType contentType = Article.ContentType.KR;
        Department department = new Department("001", "기사SW개발부");

        ContentIdSeq.Key key = new ContentIdSeq.Key(contentAttribute, contentType);
        ContentIdSeq newContentSeq = new ContentIdSeq(key);

        given(this.contentIdSeqRepository.findByKey(key)).willReturn(Optional.empty()); //해당 콘텐츠 속성의 시퀀스 존재x
        given(this.contentIdSeqRepository.save(newContentSeq)).willReturn(newContentSeq);

        //when
        Content.ID contentId = this.contentIdFactory.create(contentAttribute, contentType, department);

        //then
        Content.ID result = new Content.ID(
                contentAttribute.code() +
                        contentType.code() +
                        OffsetDateTime.now().format(DateTimeFormatter.ofPattern("uuuuMMdd")) +
                        String.format("%06d", 1) +  //첫 시퀀스
                        "001"
        );

        assertEquals(result, contentId);
    }

    @Test
    void next() {
        //given
        Content.Attribute contentAttribute = Content.Attribute.A;
        ContentType contentType = Article.ContentType.KR;
        Department department = new Department("001", "기사SW개발부");

        ContentIdSeq.Key key = new ContentIdSeq.Key(contentAttribute, contentType);
        ContentIdSeq contentNextSeq = new ContentIdSeq(key);

        given(this.contentIdSeqRepository.findByKey(key)).willReturn(Optional.of(contentNextSeq));  //해당 콘텐츠 속성의 시퀀스 존재o
        given(this.contentIdSeqRepository.save(contentNextSeq)).willReturn(contentNextSeq);

        //when
        Content.ID contentId = this.contentIdFactory.create(contentAttribute, contentType, department);

        //then
        Content.ID result = new Content.ID(
                contentAttribute.code() +
                        contentType.code() +
                        OffsetDateTime.now().format(DateTimeFormatter.ofPattern("uuuuMMdd")) +
                        String.format("%06d", 2) +  //다음 시퀀스
                        "001"
        );

        assertEquals(result, contentId);
    }
}