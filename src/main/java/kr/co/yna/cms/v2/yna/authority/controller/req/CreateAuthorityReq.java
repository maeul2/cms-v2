package kr.co.yna.cms.v2.yna.authority.controller.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CreateAuthorityReq {
    @NotBlank
    private final String name;
    private final String detail;
    @NotNull
    private final Boolean desk;
    @NotNull
    private final Boolean enabled;
    @NotBlank
    private final String target;
    @NotBlank
    private final String action;
    private final Set<Long> authorizations;

    public String getName() {
        return this.name;
    }

    public String getDetail() {
        return Optional.ofNullable(this.detail).orElse("");
    }

    public boolean isDesk() {
        return Optional.ofNullable(this.desk).orElse(false);
    }

    public boolean isEnabled() {
        return Optional.ofNullable(this.enabled).orElse(false);
    }

    public String getTarget() {
        return this.target;
    }

    public String getAction() {
        return this.action;
    }

    public Set<Long> getAuthorizations() {
        return Objects.nonNull(this.authorizations) ? Collections.unmodifiableSet(this.authorizations) : Collections.emptySet();
    }

    @Override
    public String toString() {
        return "CreateAuthorityReq{" +
                "name=" + this.name +
                ", detail=" + this.detail +
                ", desk=" + this.desk +
                ", enabled=" + this.enabled +
                ", target=" + this.target +
                ", action=" + this.action +
                ", auths=" + this.authorizations +
                '}';
    }
}
