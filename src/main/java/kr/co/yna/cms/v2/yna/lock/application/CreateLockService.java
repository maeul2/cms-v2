package kr.co.yna.cms.v2.yna.lock.application;

import kr.co.yna.cms.v2.yna.lock.application.exception.LockException;
import kr.co.yna.cms.v2.yna.lock.domain.entity.Lock;
import kr.co.yna.cms.v2.yna.lock.domain.entity.User;
import kr.co.yna.cms.v2.yna.lock.domain.repository.LockRepository;
import kr.co.yna.cms.v2.yna.lock.domain.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class CreateLockService {
    private final LockRepository lockRepository;
    private final UserInfoService userInfoService;

    public CreateLockService(LockRepository lockRepository, UserInfoService userInfoService) {
        this.lockRepository = lockRepository;
        this.userInfoService = userInfoService;
    }

    public void create(String targetId, Class<?> targetType) throws LockException {
        Assert.hasText(targetId, "Target ID must not be empty");
        Assert.notNull(targetType, "Target type must not be null");

        Optional<Lock> optLock = this.lockRepository.findByTargetIdAndTargetType(targetId, targetType);
        User currentUser = this.userInfoService.getCurrentUser();

        Lock newLock = null;
        if (optLock.isPresent()) {
            Lock lock = optLock.get();

            if (lock.isExpired()) {
                this.lockRepository.delete(lock);

                newLock = Lock.builder()
                        .targetId(targetId)
                        .targetType(targetType)
                        .expiryDateTime(OffsetDateTime.now().plusHours(1))
                        .user(currentUser)
                        .build();
            } else {
                if (!lock.isOwnedBy(currentUser)) {
                    //TODO: 데스크가 잠금 획득 시도하는 경우?

                    throw new LockException(lock);
                }
            }
        } else {
            newLock = Lock.builder()
                    .targetId(targetId)
                    .targetType(targetType)
                    .expiryDateTime(OffsetDateTime.now().plusHours(1))
                    .user(currentUser)
                    .build();
        }

        this.lockRepository.save(newLock);
    }
}
