package kr.co.yna.cms.v2.yna.authority.application.dto;

import lombok.Builder;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public record CreateCommand(
        String name,
        String detail,
        boolean desk,
        boolean enabled,
        String target,
        String action,
        Set<Long> authorizations,
        String creatorId
) {
    @Builder
    public CreateCommand {
        Assert.hasText(name, "유효하지 않은 권한(역할)명입니다.");
        Assert.hasText(target, "유효하지 않은 대상입니다.");
        Assert.hasText(action, "유효하지 않은 행위입니다.");
        Assert.hasText(creatorId, "유효하지 않은 등록자 아이디입니다.");
    }

    @Override
    public String detail() {
        return Optional.ofNullable(this.detail).orElse("");
    }

    public Set<Long> authorizations() {
        return Objects.nonNull(this.authorizations) ? Collections.unmodifiableSet(this.authorizations) : Collections.emptySet();
    }

    @Override
    public String toString() {
        return "CreateCommand{" +
                "name=" + this.name +
                ", detail=" + this.detail +
                ", desk=" + this.desk +
                ", enabled=" + this.enabled +
                ", target=" + this.target +
                ", action=" + this.action +
                ", authorizations=" + this.authorizations +
                ", creator=" + this.creatorId +
                '}';
    }
}
