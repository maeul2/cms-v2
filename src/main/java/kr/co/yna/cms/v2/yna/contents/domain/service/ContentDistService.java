package kr.co.yna.cms.v2.yna.contents.domain.service;

import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;

public interface ContentDistService<T extends Content> {
    void distribute(T contents);
}
