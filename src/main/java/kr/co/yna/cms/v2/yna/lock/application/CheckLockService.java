package kr.co.yna.cms.v2.yna.lock.application;

import kr.co.yna.cms.v2.yna.lock.application.exception.LockException;
import kr.co.yna.cms.v2.yna.lock.application.exception.NoLockException;
import kr.co.yna.cms.v2.yna.lock.domain.entity.Lock;
import kr.co.yna.cms.v2.yna.lock.domain.repository.LockRepository;
import kr.co.yna.cms.v2.yna.lock.domain.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.function.Predicate;

@Service
public class CheckLockService {
    private final LockRepository lockRepository;
    private final UserInfoService userInfoService;

    public CheckLockService(LockRepository lockRepository, UserInfoService userInfoService) {
        this.lockRepository = lockRepository;
        this.userInfoService = userInfoService;
    }

    public Optional<LockDTO> get(String targetId, Class<?> targetType) {
        Assert.hasText(targetId, "Target id must not be empty");
        Assert.notNull(targetType, "Target type must not be null");

        return this.lockRepository.findByTargetIdAndTargetType(targetId, targetType)
                .filter(Predicate.not(Lock::isExpired))
                .map(LockDTO::new);
    }

    public void verify(String targetId, Class<?> targetType) throws LockException, NoLockException {
        Assert.hasText(targetId, "Target id must not be empty");
        Assert.notNull(targetType, "Target type must not be null");

        Optional<Lock> optLock = this.lockRepository.findByTargetIdAndTargetType(targetId, targetType);
        if (optLock.isEmpty()) {
            throw new NoLockException();
        }

        check(optLock.get());
    }

    public void extend(String targetId, Class<?> targetType) throws LockException, NoLockException {
        Lock lock = this.lockRepository.findByTargetIdAndTargetType(targetId, targetType)
                .orElseThrow(NoLockException::new);

        check(lock);
        lock.extend();

        this.lockRepository.save(lock);
    }

    private void check(Lock lock) throws LockException {
        if (lock.isExpired() || !lock.isOwnedBy(this.userInfoService.getCurrentUser())) {
            throw new LockException(lock);
        }
    }
}
