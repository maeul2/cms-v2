/*
package kr.co.yna.cms.v2.yna.authority.domain.entity;

import jakarta.persistence.*;
import kr.co.yna.cms.v2.yna.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "AUTHORITY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ROLE_YN")
@Entity
public abstract class AbstractAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTH_ID")
    private final Long id;

    @Column(name = "NAME", unique = true)
    private final String name;
    @Column(name = "DETAIL")
    private String detail;

    @Column(name = "DESK_YN")
    private Boolean desk;
    @Column(name = "USE_YN")
    private Boolean enabled;
    
    @Column(name = "CREATOR_ID", updatable = false)
    private final String creatorId;

    protected AbstractAuth(String name, String detail, boolean desk, boolean enabled, User creator) {
        Assert.hasText(name, "권한명이 유효하지 않습니다.");
        Assert.notNull(creator, "생성자가 null일 수 없습니다.");

        this.id = null;

        this.name = name;
        this.detail = detail;

        this.desk = desk;
        this.enabled = enabled;

        this.creatorId = Objects.requireNonNull(creator.getId());
    }

    public void changeDetail(String detail) {
        Assert.hasText(detail, "설명은 한 글자 이상의 문자열이어야 합니다!!!");

        this.detail = detail;
    }

    public void changeDesk(boolean desk) {
        this.desk = desk;
    }

    public void changeEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public abstract boolean hasTargetAction(Target target, Action action);

    public long getId() {
        return Optional.ofNullable(this.id).orElseThrow(IllegalStateException::new);
    }

    public String getName() {
        return this.name;
    }

    public String getDetail() {
        return Optional.ofNullable(this.detail).orElse("");
    }

    public boolean isDesk() {
        return this.desk;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        AbstractAuth that = (AbstractAuth) object;

        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public String toString() {
        return "id=" + this.id +
                ", name=" + this.name +
                ", detail=" + this.detail +
                ", desk=" + this.desk +
                ", enabled=" + this.enabled +
                ", creatorId=" + this.creatorId;
    }
}
*/
