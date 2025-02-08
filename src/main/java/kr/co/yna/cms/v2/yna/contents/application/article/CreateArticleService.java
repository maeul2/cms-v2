package kr.co.yna.cms.v2.yna.contents.application.article;

import kr.co.yna.cms.v2.yna.contents.application.ContentIdFactory;
import kr.co.yna.cms.v2.yna.contents.application.ContentManageSupporter;
import kr.co.yna.cms.v2.yna.contents.application.article.cmd.CreateArticle;
import kr.co.yna.cms.v2.yna.contents.application.exception.DuplicatedContentIdException;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Department;
import kr.co.yna.cms.v2.yna.contents.domain.entity.User;
import kr.co.yna.cms.v2.yna.contents.domain.entity.article.Article;
import kr.co.yna.cms.v2.yna.contents.domain.event.article.ArticleEventHandler;
import kr.co.yna.cms.v2.yna.contents.domain.repository.ArticleRepository;
import kr.co.yna.cms.v2.yna.contents.domain.service.ContentHistoryService;
import kr.co.yna.cms.v2.yna.contents.domain.service.ContentLockService;
import kr.co.yna.cms.v2.yna.contents.domain.service.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class CreateArticleService extends ContentManageSupporter<Article, CreateArticle> {
    private final ContentIdFactory contentIdFactory;
    private final ArticleRepository articleRepository;
    private final UserInfoService userInfoService;
    private final ContentLockService contentLockService;
    private final ContentHistoryService contentHistoryService;

    public CreateArticleService(
            ContentIdFactory contentIdFactory,
            ArticleRepository articleRepository,
            UserInfoService userInfoService,
            ContentLockService contentLockService,
            ContentHistoryService contentHistoryService
    ) {
        this.contentIdFactory = contentIdFactory;
        this.articleRepository = articleRepository;
        this.userInfoService = userInfoService;
        this.contentLockService = contentLockService;
        this.contentHistoryService = contentHistoryService;
    }

    @Override
    protected Article load(CreateArticle cmd) {
        User drafter = this.userInfoService.findUserById(cmd.getRegistererId());

        Article.ContentType contentType = Article.ContentType.valueOf(cmd.getContentType().toUpperCase());
        Content.ID contentId = this.contentIdFactory.create(Content.Attribute.A, contentType, new Department(drafter.getDeptId(), drafter.getDeptName()));

        checkDuplicatedContentId(contentId);

        return Article.draft()
                .contentId(contentId)
                .contentType(contentType)
                .detailAttribute(Article.DetailAttribute.fromCode(cmd.getDetailAttribute()))
                .registerer(drafter)
                .build();
    }

    private void checkDuplicatedContentId(Content.ID contentId) {
        this.articleRepository.findById(contentId)
                .ifPresent(article -> {
                    throw new DuplicatedContentIdException("이미 존재하는 콘텐츠 아이디 - " + contentId.getValue());
                });
    }

    @Override
    protected void lock(Article article) {
        this.contentLockService.tryLock(article);
    }

    @Override
    protected Article handler(Article article) {
        return ArticleEventHandler.handler(article);
    }

    @Override
    protected void action(Article article, CreateArticle cmd) {
        //do nothing
    }

    @Override
    protected Article store(Article input) {
        return this.articleRepository.save(input);
    }

    @Override
    protected void process(Article article) {
        this.contentHistoryService.history(article);
    }
}
