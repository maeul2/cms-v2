package kr.co.yna.cms.v2.yna.contents.domain.repository;

import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface ContentRepository<T extends Content> extends Repository<T, Content.ID> {
    T save(T content);

    Optional<T> findById(Content.ID contentsId);
}
