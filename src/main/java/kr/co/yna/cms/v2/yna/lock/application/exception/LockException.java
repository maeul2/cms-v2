package kr.co.yna.cms.v2.yna.lock.application.exception;

import kr.co.yna.cms.v2.yna.lock.domain.entity.Lock;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class LockException extends Exception {
    private final String targetId;
    private final String userId;
    private final String userName;

    public LockException(Lock lock) {
        Assert.notNull(lock, "Lock must not be null");

        this.targetId = lock.getTargetId();
        this.userId = lock.getUserId();
        this.userName = lock.getUserName();
    }
}
