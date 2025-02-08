package kr.co.yna.cms.v2.yna.lock.application;

import kr.co.yna.cms.v2.yna.lock.application.exception.LockException;
import kr.co.yna.cms.v2.yna.lock.application.exception.NoLockException;
import kr.co.yna.cms.v2.yna.lock.domain.entity.Lock;
import kr.co.yna.cms.v2.yna.lock.domain.repository.LockRepository;
import kr.co.yna.cms.v2.yna.lock.domain.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class DeleteLockService {
    private final LockRepository lockRepository;
    private final UserInfoService userInfoService;

    public DeleteLockService(LockRepository lockRepository, UserInfoService userInfoService) {
        this.lockRepository = lockRepository;
        this.userInfoService = userInfoService;
    }

    public void delete(String targetId, Class<?> targetType) throws LockException, NoLockException {
        Assert.hasText(targetId, "Target id must not be empty");
        Assert.notNull(targetType, "Target type must not be null");

        Optional<Lock> optLock = this.lockRepository.findByTargetIdAndTargetType(targetId, targetType);
        if (optLock.isPresent()) {
            Lock lock = optLock.get();

            if (!lock.isOwnedBy(this.userInfoService.getCurrentUser())) {
                throw new LockException(lock);
            }

            this.lockRepository.delete(lock);
        } else {
            throw new NoLockException();
        }
    }
}
