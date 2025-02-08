package kr.co.yna.cms.v2.yna.lock.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Embeddable
public class User {
    @Column(name = "USER_ID", nullable = false, updatable = false)
    private final String userId;
    @Column(name = "USER_NAME", nullable = false, updatable = false)
    private final String userName;
    @Column(name = "USER_DEPT_ID", nullable = false, updatable = false)
    private final String userDeptId;
    @Column(name = "USER_DEPT_NAME", nullable = false, updatable = false)
    private final String userDeptName;

    public User(String userId, String userName, String userDeptId, String userDeptName) {
        Assert.hasText(userId, "userId must not be empty");
        Assert.hasText(userName, "userName must not be empty");
        Assert.hasText(userDeptId, "userDeptId must not be empty");
        Assert.hasText(userDeptName, "userDeptName must not be empty");

        this.userId = userId;
        this.userName = userName;
        this.userDeptId = userDeptId;
        this.userDeptName = userDeptName;
    }

    String getUserId() {
        return this.userId;
    }

    String getUserName() {
        return this.userName;
    }
}
