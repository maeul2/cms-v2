/*
package kr.co.yna.cms.v2.yna.user.domain.entity;

import jakarta.persistence.*;
import kr.co.yna.cms.v2.yna.department.domain.entity.Department;
import kr.co.yna.cms.v2.yna.user.domain.service.exception.PasswordNotMatchException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "USER")
public class User implements Persistable<String> {
    @Id
    @Column(name = "USER_ID", updatable = false)
    private final String id;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "NAME", updatable = false)
    private final String name;
    @Column(name = "DEPT_ID")
    private String deptId;
    @ElementCollection
    @CollectionTable(
            name = "USER_ATHORITY",
            joinColumns = @JoinColumn(name = "USER_ID")
    )
    @AttributeOverrides({
            @AttributeOverride(
                    name = "authId",
                    column = @Column(name = "AUTH_ID", updatable = false)
            ),
            @AttributeOverride(
                    name = "expDateTime",
                    column = @Column(name = "EXPIRATION_DATETIME", updatable = false)
            )
    })
    private Set<UserAuthority> authorities;
    @Column(name = "JOIN_DATE")
    private final LocalDate joinDate;

    @CreatedDate
    @Column(name = "CREATED_DATETIME", updatable = false)
    private Instant createdAt;
    @LastModifiedDate
    @Column(name = "MODIFIED_DATETIME")
    private Instant modifiedAt;

    @Builder
    private User(
            String id,
            String password,
            String name,
            Department dept,
            Collection<UserAuthority> authorities,
            LocalDate joinDate
    ) {
        Assert.hasText(id, "유효하지 않은 사용자 아이디 입니다.");
        Assert.hasText(password, "유효하지 않은 비밀번호 입니다.");
        Assert.notNull(dept, "사용자 부서가 null일 수 없습니다.");
        Assert.notNull(joinDate, "입사일이 null일 수 없습니다.");

        this.id = id;
        this.password = password;
        this.name = name;
        this.deptId = dept.getId();

        this.authorities = Objects.isNull(authorities) ? null : Set.copyOf(authorities);

        this.joinDate = joinDate;
    }

    public void changePassword(String oldPw, String newPw) {
        Assert.hasText(oldPw, "기존 비밀번호가 유효하지 않은 문자열입니다!!!");
        Assert.hasText(newPw, "새 비밀번호가 유효하지 않은 문자열입니다!!!");

        if (!oldPw.equals(this.password)) {
            throw new PasswordNotMatchException();
        }

        this.password = newPw;
    }

    public void changeDepartment(Department dept) {
        Assert.notNull(dept, "부서가 null일 수 없습니다!!!");

        this.deptId = dept.getId();
    }

    public void changeUserAuthorities(Collection<UserAuthority> authorities) {
        this.authorities = Objects.isNull(authorities) ? null : Set.copyOf(authorities);
    }

    public Set<UserAuthority> getValidAuthorities(Predicate<UserAuthority> filter) {
        return this.getAuthorities()
                .stream()
                .filter(filter)
                .collect(Collectors.toUnmodifiableSet());
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDeptId() {
        return this.deptId;
    }

    public String getPassword() {
        return this.password;
    }

    public Set<UserAuthority> getAuthorities() {
        return Objects.nonNull(this.authorities) ? Set.copyOf(this.authorities) : Collections.emptySet();
    }

    private LocalDate getJoinDate() {
        return this.joinDate;
    }

    @Override
    public boolean isNew() {
        return Objects.isNull(this.createdAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(this.id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.id +
                ", name=" + this.name +
                ", dept=" + this.id +
                ", joinDate=" + this.joinDate +
                ", authorizations=" + this.authorities +
                "}";
    }
}
*/
