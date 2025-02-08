package kr.co.yna.cms.v2.yna.contents.domain.event.article;

import kr.co.yna.cms.v2.yna.contents.domain.entity.User;
import kr.co.yna.cms.v2.yna.contents.domain.entity.article.Article;
import kr.co.yna.cms.v2.yna.contents.domain.entity.article.ArticleContent;
import kr.co.yna.cms.v2.yna.contents.domain.event.ContentDeleted;
import kr.co.yna.cms.v2.yna.contents.domain.event.ContentStatusChanged;
import org.springframework.util.Assert;

import java.time.OffsetDateTime;
import java.util.Optional;

public class ArticleEventHandler extends Article {  //데코레이터 패턴
    private Article actor;  //위임

    public static Article handler(Article article) {
        Assert.notNull(article, "Article must not be null");

        if (article.isDraft()) {
            return new ArticleEventHandler(new ArticleCreated(article));
        }

        return new ArticleEventHandler(article);
    }

    private ArticleEventHandler(ArticleCreated event) {
        Assert.notNull(event, "ArticleCreated argument cannot be null");

        apply(event);
    }

    private ArticleEventHandler(Article actor) {
        Assert.notNull(actor, "actor must not be null");

        this.actor = actor;
    }

    public static Article replayer() {
        return new ArticleEventHandler();
    }

    private ArticleEventHandler() {
        this.actor = null;
    }

    public void handle(ArticleCreated event) {
        this.actor = event.getArticle();
    }

    @Override
    public void modify(ArticleContent articleContent, User modifier) {
        apply(new ArticleModified(articleContent, modifier));
    }

    public void handle(ArticleModified event) {
        this.actor.modify(event.getArticleContent(), event.getModifier());
    }

    @Override
    public boolean isDraft() {
        return this.actor.isDraft();
    }

    @Override
    public void setStatus() {
        apply(new ContentStatusChanged());
    }

    public void handle(ContentStatusChanged event) {
        this.actor.setStatus();
    }

    @Override
    public void delete() {
        apply(new ContentDeleted());
    }

    public void handle(ContentDeleted event) {
        this.actor.delete();
    }

    //아래는 접근자 위임

    @Override
    public ID getContentId() {
        return this.actor.getContentId();
    }

    @Override
    public Attribute getContentAttribute() {
        return this.actor.getContentAttribute();
    }

    @Override
    public Status getStatus() {
        return this.actor.getStatus();
    }

    @Override
    public User getRegister() {
        return this.actor.getRegister();
    }

    @Override
    public OffsetDateTime getRegisterDateTime() {
        return this.actor.getRegisterDateTime();
    }

    @Override
    public Optional<User> getUpdater() {
        return this.actor.getUpdater();
    }

    @Override
    public Optional<OffsetDateTime> getUpdateDateTime() {
        return this.actor.getUpdateDateTime();
    }

    @Override
    public Optional<User> getDesk() {
        return this.actor.getDesk();
    }

    @Override
    public Optional<OffsetDateTime> getDeskDateTime() {
        return this.actor.getDeskDateTime();
    }

    @Override
    public Optional<OffsetDateTime> getDistDateTime() {
        return this.actor.getDistDateTime();
    }

    @Override
    public boolean isDeleted() {
        return this.actor.isDeleted();
    }

    @Override
    public ContentType getContentType() {
        return this.actor.getContentType();
    }

    @Override
    public DetailAttribute getDetailAttribute() {
        return this.actor.getDetailAttribute();
    }

    @Override
    public String getTitle() {
        return this.actor.getTitle();
    }

    @Override
    public String getBody() {
        return this.actor.getBody();
    }

    @Override
    public Optional<String> getSource() {
        return this.actor.getSource();
    }

    @Override
    public Optional<User> getWriter() {
        return this.actor.getWriter();
    }

    @Override
    public Optional<OffsetDateTime> getWriteDateTime() {
        return this.actor.getWriteDateTime();
    }

    @Override
    public boolean equals(Object o) {
        return this.actor.equals(o);
    }

    @Override
    public int hashCode() {
        return this.actor.hashCode();
    }

    @Override
    public String toString() {
        return this.actor.toString();
    }
}
