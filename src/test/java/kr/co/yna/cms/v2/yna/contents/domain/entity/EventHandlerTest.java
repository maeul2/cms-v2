package kr.co.yna.cms.v2.yna.contents.domain.entity;

import kr.co.yna.cms.v2.yna.contents.domain.entity.article.Article;
import kr.co.yna.cms.v2.yna.contents.domain.entity.article.ArticleContent;
import kr.co.yna.cms.v2.yna.contents.domain.event.ContentStatusChanged;
import kr.co.yna.cms.v2.yna.contents.domain.event.article.ArticleCreated;
import kr.co.yna.cms.v2.yna.contents.domain.event.article.ArticleEventHandler;
import kr.co.yna.cms.v2.yna.contents.domain.event.article.ArticleModified;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventHandlerTest {
    @Test
    void apply() {
        //given
        User writer = new User("2022029", "박용준", new Department("001", "기사SW개발부"), false);
        User desk = new User("2022028", "이우석", new Department("001", "기사SW개발부"), true);

        List<AbstractEvent> events = new ArrayList<>();

        //기사 생성
        events.add(new ArticleCreated(
                Article.draft()
                        .contentId(new Content.ID("AKR20250107000001001"))
                        .registerer(writer)
                        .contentType(Article.ContentType.KR)
                        .detailAttribute(Article.DetailAttribute._0)
                        .build()
        ));

        //기사 송고
        events.add(new ArticleModified(
                ArticleContent.builder()
                        .title("수정 후 제목")
                        .body("수정 후 본문")
                        .source("수정 후 출처")
                        .build(),
                writer
        ));
        events.add(new ContentStatusChanged());

        //기사 데스킹
        events.add(new ArticleModified(
                ArticleContent.builder()
                        .title("데스킹 후 제목")
                        .body("데스킹 후 본문")
                        .source("데스킹 후 출처")
                        .build(),
                desk
        ));
        events.add(new ContentStatusChanged());

        //when
        Article article = ArticleEventHandler.replayer();
        events.forEach(event -> article.apply(event, false));

        //then
        assertEquals(writer, article.getRegister());
        assertEquals(writer, article.getWriter().get());
        assertEquals(desk, article.getDesk().get());
        assertEquals(desk, article.getUpdater().get());
        assertEquals("데스킹 후 제목", article.getTitle());
        assertEquals("데스킹 후 본문", article.getBody());
        assertEquals(Content.Status.DESK_DONE, article.getStatus());
    }
}