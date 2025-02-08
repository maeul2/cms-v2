/*
package kr.co.yna.cms.v2.yna.authority.domain.entity;

import jakarta.persistence.*;
import kr.co.yna.cms.v2.yna.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@DiscriminatorValue("Y")
public class Role extends AbstractAuth {
    @JoinTable(
            name = "ROLE_AUTH",
            joinColumns = {
                    @JoinColumn(
                        name = "ROLE_ID",
                            referencedColumnName = "AUTH_ID"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "AUTH_ID",
                            referencedColumnName = "AUTH_ID"
                    )
            }
    )
    @OneToMany(fetch = FetchType.LAZY)
    private Set<AbstractAuth> authorities;

    @Builder
    private Role(String name, String detail, boolean desk, boolean enabled, User creator, Collection<Authority> authorities) {
        super(name, detail, desk, enabled, creator);

        this.authorities = Objects.nonNull(authorities) ? Set.copyOf(authorities) : null;
    }

    public void changeAuthorities(Collection<Authority> authorities) {
        this.authorities = Objects.nonNull(authorities) ? Set.copyOf(authorities) : null;
    }

    @Override
    public boolean hasTargetAction(Target target, Action action) {
        if (CollectionUtils.isEmpty(this.authorities)) {
            return false;
        }

        return this.authorities.stream().anyMatch(auth -> auth.hasTargetAction(target, action));
    }

    @Override
    public String toString() {
        return "Role{" + super.toString() +
                ", authorizations=" + this.authorities +
                '}';
    }
}
*/
