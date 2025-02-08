package kr.co.yna.cms.v2.yna.contents.domain.entity;

import kr.co.yna.cms.v2.yna.contents.domain.entity.article.Article;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ContentTypeTest {
    @Test
    void equalsAndSame() {
        //given
        ContentType contentType1;
        ContentType contentType2;

        //when
        contentType1 = Article.ContentType.KR;
        contentType2 = Article.ContentType.KR;

        //then
        assertEquals(contentType1, contentType2);
        assertSame(contentType1, contentType2);
    }
}