package kr.co.yna.cms.v2.yna.lock.application;

import kr.co.yna.cms.v2.yna.lock.domain.entity.Lock;
import org.springframework.util.Assert;

public class LockDTO {
    private final Lock lock;

    public LockDTO(Lock lock) {
        Assert.notNull(lock, "Lock must not be null");

        this.lock = lock;
    }

    public String getTargetId() {
        return lock.getTargetId();
    }

    public String getUserId() {
        return lock.getUserId();
    }

    public String getUserName() {
        return lock.getUserName();
    }
}
