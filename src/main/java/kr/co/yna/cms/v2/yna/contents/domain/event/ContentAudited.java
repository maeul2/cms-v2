package kr.co.yna.cms.v2.yna.contents.domain.event;

import kr.co.yna.cms.v2.yna.common.event.Event;
import kr.co.yna.cms.v2.yna.contents.domain.entity.AbstractEvent;
import kr.co.yna.cms.v2.yna.contents.domain.entity.User;
import lombok.Getter;
import org.springframework.util.Assert;

@Event
@Getter
public class ContentAudited extends AbstractEvent {
    private final User updater;

    public ContentAudited(User updater) {
        Assert.notNull(updater, "Updater must not be null");

        this.updater = updater;
    }
}
