package kr.co.yna.cms.v2.yna.contents.domain.service;

import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;

public interface ContentIndexService<T extends Content> {
    void index(T contents);
}
