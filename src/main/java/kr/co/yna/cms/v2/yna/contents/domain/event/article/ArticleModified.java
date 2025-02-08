package kr.co.yna.cms.v2.yna.contents.domain.event.article;

import kr.co.yna.cms.v2.yna.common.event.Event;
import kr.co.yna.cms.v2.yna.contents.domain.entity.AbstractEvent;
import kr.co.yna.cms.v2.yna.contents.domain.entity.User;
import kr.co.yna.cms.v2.yna.contents.domain.entity.article.ArticleContent;
import lombok.Getter;
import org.springframework.util.Assert;

@Event(name = "수정", history = true)
@Getter
public class ArticleModified extends AbstractEvent {
    private final ArticleContent articleContent;
    private final User modifier;

    public ArticleModified(ArticleContent articleContent, User modifier) {
        Assert.notNull(articleContent, "articleContent must not be null");
        Assert.notNull(modifier, "modifier must not be null");

        this.articleContent = articleContent;
        this.modifier = modifier;
    }
}
