package kr.co.yna.cms.v2.yna.contents.domain.event.article;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Department;
import kr.co.yna.cms.v2.yna.contents.domain.entity.User;
import kr.co.yna.cms.v2.yna.contents.domain.entity.article.Article;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ArticleCreatedTest {
    static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void setUpBeforeClass() {
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void serialize() throws JsonProcessingException {
        //given
        Article article = Article.draft()   //기사 초안
                .contentId(new Content.ID("AKR20250107000001001"))
                .registerer(new User("2022028", "이우석", new Department("001", "기사SW개발부"), true))
                .contentType(Article.ContentType.KR)
                .detailAttribute(Article.DetailAttribute._0)
                .build();

        //when
        ArticleCreated event = new ArticleCreated(article);

        //then
        System.out.println(mapper.writeValueAsString(event));
    }
}