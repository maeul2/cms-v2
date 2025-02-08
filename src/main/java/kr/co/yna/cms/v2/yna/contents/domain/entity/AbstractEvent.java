package kr.co.yna.cms.v2.yna.contents.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractEvent {
    @JsonIgnore
    public Class<? extends AbstractEvent> getType() {
        return this.getClass();
    }
}
