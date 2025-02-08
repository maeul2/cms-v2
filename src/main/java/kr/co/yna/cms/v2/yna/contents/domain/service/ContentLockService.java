package kr.co.yna.cms.v2.yna.contents.domain.service;

import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;

public interface ContentLockService {
    void tryLock(Content content);

    void checkLock(Content content);

    void releaseLock(Content content);
}
