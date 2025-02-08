/*
package kr.co.yna.cms.v2.yna.authority.domain.entity;

import jakarta.persistence.*;
import kr.co.yna.cms.v2.yna.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@DiscriminatorValue("N")
public class Authority extends AbstractAuth {
    @AttributeOverride(
            name = "code",
            column = @Column(name = "TARGET", updatable = false)
    )
    @Column(name = "TARGET")
    private Target target;
    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION")
    private Action action;

    @Builder
    private Authority(
            String name,
            String detail,
            boolean desk,
            boolean enabled,
            Target target,
            Action action,
            User creator
    ) {
        super(name, detail, desk, enabled, creator);

        Assert.notNull(target, "대상이 null일 수 없습니다.");
        Assert.notNull(action, "행위가 null일 수 없습니다.");

        this.target = target;
        this.action = action;
    }

    public Target getTarget() {
        return this.target;
    }

    public Action getAction() {
        return this.action;
    }

    @Override
    public boolean hasTargetAction(Target target, Action action) {
        Assert.notNull(target, "대상이 null일 수 없습니다.");
        Assert.notNull(action, "행위가 null일 수 없습니다.");

        return target.equals(this.getTarget()) && action.equals(this.getAction());
    }

    @Override
    public String toString() {
        return "Authorization{" + super.toString() +
                ", target=" + this.target +
                ", action=" + this.action +
                '}';
    }
}
*/
