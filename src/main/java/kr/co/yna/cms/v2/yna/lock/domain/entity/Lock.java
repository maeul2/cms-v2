package kr.co.yna.cms.v2.yna.lock.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.OffsetDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "CMS_LOCK", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"TARGET_ID", "USER_ID"}),
        @UniqueConstraint(columnNames = {"TARGET_ID", "TARGET_TYPE"})
})
@Entity
public class Lock {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "LOCK_ID", nullable = false, unique = true, updatable = false)
    @Id
    private final String lockId;
    @Column(name = "TARGET_ID", nullable = false, updatable = false)
    private final String targetId;
    @Column(name = "TARGET_TYPE", nullable = false, updatable = false)
    private final Class<?> targetType;
    @Column(name = "EXPIRY_DATETIME", nullable = false)
    private OffsetDateTime expiryDateTime;
    @Embedded
    private final User user;

    @Builder
    private Lock(String targetId, Class<?> targetType, OffsetDateTime expiryDateTime, User user) {
        Assert.hasText(targetId, "targetId must not be empty");
        Assert.notNull(targetType, "targetType must not be null");
        Assert.notNull(expiryDateTime, "expiryDateTime must not be null");
        Assert.notNull(user, "user must not be null");

        this.lockId = null;
        this.targetId = targetId;
        this.targetType = targetType;
        this.expiryDateTime = expiryDateTime;
        this.user = user;
    }

    public boolean isExpired() {
        return OffsetDateTime.now().isAfter(this.expiryDateTime);
    }

    public void extend() {
        this.expiryDateTime = this.expiryDateTime.plusMinutes(1);
    }

    public boolean isOwnedBy(User user) {
        return this.user.equals(user);
    }

    public String getTargetId() {
        return this.targetId;
    }

    public String getUserId() {
        return this.user.getUserId();
    }

    public String getUserName() {
        return this.user.getUserName();
    }
}
