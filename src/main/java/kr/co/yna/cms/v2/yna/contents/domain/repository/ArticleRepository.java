package kr.co.yna.cms.v2.yna.contents.domain.repository;

import kr.co.yna.cms.v2.yna.contents.domain.entity.article.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ContentRepository<Article> {
}
