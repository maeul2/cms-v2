package kr.co.yna.cms.v2.yna.contents.domain.event.article;

import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Department;
import kr.co.yna.cms.v2.yna.contents.domain.entity.User;
import kr.co.yna.cms.v2.yna.contents.domain.entity.article.Article;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArticleEventHandlerTest {
    @Test
    void handleArticleCreated() {
        //given
        Article article = Article.draft()   //기사 초안
                .contentId(new Content.ID("AKR20250107000001001"))
                .registerer(new User("2022028", "이우석", new Department("001", "기사SW개발부"), true))
                .contentType(Article.ContentType.KR)
                .detailAttribute(Article.DetailAttribute._0)
                .build();

        //when
        Article eventHandler = ArticleEventHandler.handler(article);

        //then
        assertEquals(1, eventHandler.getEventList().size());
        assertTrue(eventHandler.getEventList().stream().anyMatch(event -> event instanceof ArticleCreated));
        assertEquals(eventHandler, article);
    }
}