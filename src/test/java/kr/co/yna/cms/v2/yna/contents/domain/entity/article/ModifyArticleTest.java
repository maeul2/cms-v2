package kr.co.yna.cms.v2.yna.contents.domain.entity.article;

import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Department;
import kr.co.yna.cms.v2.yna.contents.domain.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModifyArticleTest {
    @Test
    void tempDraft() {
        //given
        User writer = new User("2022028", "이우석", new Department("001", "기사SW개발부"), true);
        Article draft = Article.draft()
                .contentId(new Content.ID("AKR20250107000001001"))
                .registerer(writer)
                .contentType(Article.ContentType.KR)
                .detailAttribute(Article.DetailAttribute._0)
                .build();

        //when
        draft.modify(
                ArticleContent.builder()
                        .title("제목")
                        .body("본문")
                        .source("출처")
                        .temp(true)
                        .build(),
                writer
        );

        //then
        assertTrue(draft.getWriter().isEmpty());
        assertEquals("제목", draft.getTitle());
        assertEquals("본문", draft.getBody());
    }

    @Test
    void modifyDraft() {
        //given
        User writer = new User("2022028", "이우석", new Department("001", "기사SW개발부"), true);
        Article draft = Article.draft()
                .contentId(new Content.ID("AKR20250107000001001"))
                .registerer(writer)
                .contentType(Article.ContentType.KR)
                .detailAttribute(Article.DetailAttribute._0)
                .build();

        //when
        draft.modify(
                ArticleContent.builder()
                        .title("제목")
                        .body("본문")
                        .source("출처")
                        .temp(false)
                        .build(),
                writer
        );

        //then
        assertEquals(writer, draft.getWriter().orElseThrow());
        assertEquals("제목", draft.getTitle());
        assertEquals("본문", draft.getBody());
    }

    @Test
    void modifyArticle() {
        //given
        User writer = new User("2022028", "이우석", new Department("001", "기사SW개발부"), true);
        Article draft = Article.draft()
                .contentId(new Content.ID("AKR20250107000001001"))
                .registerer(writer)
                .contentType(Article.ContentType.KR)
                .detailAttribute(Article.DetailAttribute._0)
                .build();

        draft.modify(
                ArticleContent.builder()
                        .title("제목")
                        .body("본문")
                        .source("출처")
                        .temp(false)
                        .build(),
                writer
        );

        //when
        User modifier = new User("2022029", "박용준", new Department("002", "기사SW개발부"), true);
        Article article = draft.copy();

        article.modify(
                ArticleContent.builder()
                        .title("수정 후 제목")
                        .body("수정 후 본문")
                        .source("출처")
                        .temp(false)
                        .build(),
                modifier
        );

        //then
        assertEquals(writer, article.getWriter().orElseThrow());
        assertEquals("수정 후 제목", article.getTitle());
        assertEquals("수정 후 본문", article.getBody());
    }

    @Test
    void tempModify() {
        //given
        User writer = new User("2022028", "이우석", new Department("001", "기사SW개발부"), true);
        Article draft = Article.draft()
                .contentId(new Content.ID("AKR20250107000001001"))
                .registerer(writer)
                .contentType(Article.ContentType.KR)
                .detailAttribute(Article.DetailAttribute._0)
                .build();

        draft.modify(
                ArticleContent.builder()
                        .title("제목")
                        .body("본문")
                        .source("출처")
                        .temp(true)
                        .build(),
                writer
        );

        //when
        User modifier = new User("2022029", "박용준", new Department("002", "기사SW개발부"), true);
        Article article = draft.copy();

        article.modify(
                ArticleContent.builder()
                        .title("수정 후 제목")
                        .body("수정 후 본문")
                        .source("출처")
                        .temp(true)
                        .build(),
                modifier
        );

        //then
        assertTrue(article.getWriter().isEmpty());
        assertEquals("수정 후 제목", article.getTitle());
        assertEquals("수정 후 본문", article.getBody());
    }
}
