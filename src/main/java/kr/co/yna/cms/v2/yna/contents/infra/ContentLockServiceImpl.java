package kr.co.yna.cms.v2.yna.contents.infra;

import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import kr.co.yna.cms.v2.yna.contents.domain.service.AlreadyLockedException;
import kr.co.yna.cms.v2.yna.contents.domain.service.ContentLockService;
import kr.co.yna.cms.v2.yna.lock.application.CheckLockService;
import kr.co.yna.cms.v2.yna.lock.application.CreateLockService;
import kr.co.yna.cms.v2.yna.lock.application.DeleteLockService;
import kr.co.yna.cms.v2.yna.lock.application.exception.LockException;
import kr.co.yna.cms.v2.yna.lock.application.exception.NoLockException;
import org.springframework.stereotype.Component;

@Component
public class ContentLockServiceImpl implements ContentLockService {
    private final CreateLockService createLockService;
    private final CheckLockService checkLockService;
    private final DeleteLockService deleteLockService;

    public ContentLockServiceImpl(CreateLockService createLockService, CheckLockService checkLockService, DeleteLockService deleteLockService) {
        this.createLockService = createLockService;
        this.checkLockService = checkLockService;
        this.deleteLockService = deleteLockService;
    }

    @Override
    public void tryLock(Content content) {
        try {
            this.createLockService.create(content.getContentId().getValue(), content.getClass());
        } catch (LockException e) {
            throw new AlreadyLockedException(e.getTargetId(), e.getUserId(), e.getUserName());
        }
    }

    @Override
    public void checkLock(Content content) {
        try {
            this.checkLockService.verify(content.getContentId().getValue(), content.getClass());
        } catch (LockException | NoLockException e) {
            throw new IllegalStateException("잠금 실패!");
        }
    }

    @Override
    public void releaseLock(Content content) {
        try {
            this.deleteLockService.delete(content.getContentId().getValue(), content.getClass());
        } catch (LockException | NoLockException e) {
            throw new IllegalStateException("잠금 해제 실패!");
        }
    }
}
