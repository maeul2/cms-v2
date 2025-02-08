/*
package kr.co.yna.cms.v2.yna.user.domain.entity;

import jakarta.persistence.Embeddable;
import kr.co.yna.cms.v2.yna.authority.domain.entity.Authority;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Embeddable
public class UserAuthority {
    private final Long authId;

    private final OffsetDateTime expDateTime;

    public UserAuthority(Authority authority, OffsetDateTime expDateTime) {
        Assert.notNull(authority, "권한이 null일 수 없습니다.");

        this.authId = authority.getId();
        this.expDateTime = expDateTime;
    }

    public UserAuthority(Authority authority) {
        Assert.notNull(authority, "권한이 null일 수 없습니다.");

        this.authId = authority.getId();
        this.expDateTime = OffsetDateTime.MAX;
    }

    public long getAuthId() {
        return this.authId;
    }

    public OffsetDateTime getExpDateTime() {
        return this.expDateTime;
    }

    public static Predicate<UserAuthority> expirationFilter() {
        return (UserAuthority userAuthority) -> !userAuthority.getExpDateTime().isBefore(OffsetDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAuthority that = (UserAuthority) o;

        return Objects.equals(this.authId, that.authId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.authId);
    }

    @Override
    public String toString() {
        return "UserAuthority{" +
                "authId=" + this.authId +
                ", expDateTime=" + this.expDateTime +
                '}';
    }
}
*/
