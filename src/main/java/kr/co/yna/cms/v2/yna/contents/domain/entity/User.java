package kr.co.yna.cms.v2.yna.contents.domain.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@MappedSuperclass
@Embeddable
public class User {
    private final String id;
    private final String name;
    @Embedded
    private final Department department;

    @Transient
    private final boolean isDesk;

    public User(String userId, String userName, Department userDept, boolean isDesk) {
        Assert.hasText(userId, "userId must not be empty");
        Assert.hasText(userName, "userName must not be empty");
        Assert.notNull(userDept, "userDept must not be null");

        this.id = userId;
        this.name = userName;
        this.department = userDept;

        this.isDesk = isDesk;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDeptId() {
        return this.department.getId();
    }

    public String getDeptName() {
        return this.department.getName();
    }

    public boolean isDesk() {
        return this.isDesk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User that)) return false;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.department, that.department) &&
                Objects.equals(this.isDesk, that.isDesk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.department, this.isDesk);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.id +
                ", name=" + this.name +
                ", department=" + this.department +
                ", isDesk=" + this.isDesk +
                '}';
    }
}
