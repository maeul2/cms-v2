package kr.co.yna.cms.v2.yna.contents.application.article;

import jakarta.persistence.OptimisticLockException;
import kr.co.yna.cms.v2.yna.contents.application.ContentManageSupporter;
import kr.co.yna.cms.v2.yna.contents.application.article.cmd.ModifyArticle;
import kr.co.yna.cms.v2.yna.contents.application.exception.ContentNotFoundException;
import kr.co.yna.cms.v2.yna.contents.application.exception.VersionConflictException;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import kr.co.yna.cms.v2.yna.contents.domain.entity.article.Article;
import kr.co.yna.cms.v2.yna.contents.domain.entity.article.ArticleContent;
import kr.co.yna.cms.v2.yna.contents.domain.event.article.ArticleEventHandler;
import kr.co.yna.cms.v2.yna.contents.domain.repository.ArticleRepository;
import kr.co.yna.cms.v2.yna.contents.domain.service.ContentHistoryService;
import kr.co.yna.cms.v2.yna.contents.domain.service.ContentLockService;
import kr.co.yna.cms.v2.yna.contents.domain.service.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class ModifyArticleService extends ContentManageSupporter<Article, ModifyArticle> {
    private final ArticleRepository articleRepository;
    private final UserInfoService userInfoService;
    private final ContentLockService contentLockService;
    private final ContentHistoryService contentHistoryService;

    public ModifyArticleService(
            ArticleRepository articleRepository,
            UserInfoService userInfoService,
            ContentLockService contentLockService,
            ContentHistoryService contentHistoryService
    ) {
        this.articleRepository = articleRepository;
        this.userInfoService = userInfoService;
        this.contentLockService = contentLockService;
        this.contentHistoryService = contentHistoryService;
    }

    @Override
    protected Article load(ModifyArticle cmd) {
        return this.articleRepository.findById(new Content.ID(cmd.getContentId()))
                .orElseThrow(() -> new ContentNotFoundException("Not Found Article - " + cmd.getContentId()));
    }

    @Override
    protected void lock(Article article) {
        this.contentLockService.checkLock(article);
    }

    @Override
    protected Article handler(Article article) {
        return ArticleEventHandler.handler(article);
    }

    @Override
    protected void action(Article article, ModifyArticle cmd) {
        article.modify(
                ArticleContent.builder()
                        .title(cmd.getTitle())
                        .body(cmd.getBody())
                        .source(cmd.getSource())
                        .temp(cmd.isTemp())
                        .build(),
                this.userInfoService.getCurrentUser()
        );
    }

    @Override
    protected void process(Article article) {
        this.contentHistoryService.history(article);
    }

    @Override
    protected Article store(Article input) {
        try {
            return this.articleRepository.save(input);
        } catch (OptimisticLockException e) {
            throw new VersionConflictException("동기화가 필요합니다. 새로고침 후 다시 시도해주세요.");
        }
    }

    @Override
    protected void unLock(Article article) {
        this.contentLockService.releaseLock(article);
    }
}
