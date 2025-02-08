package kr.co.yna.cms.v2.yna.contents.domain.event.article;

import kr.co.yna.cms.v2.yna.common.event.Event;
import kr.co.yna.cms.v2.yna.contents.domain.entity.AbstractEvent;
import kr.co.yna.cms.v2.yna.contents.domain.entity.article.Article;
import lombok.Getter;
import org.springframework.util.Assert;

@Event(name = "등록", history = true)
@Getter
public class ArticleCreated extends AbstractEvent {
    private final Article article;

    public ArticleCreated(Article article) {
        Assert.notNull(article, "Article must not be null");

        this.article = article.copy();
    }
}
