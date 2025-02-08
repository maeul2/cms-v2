package kr.co.yna.cms.v2.yna.history.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "CONTENT_EVENT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"SOURCE_ID", "SEQ"})
})
@Entity
public class ContentEvent extends AbstractEvent {
    @Builder
    private ContentEvent(String sourceId, String sourceType, Class<? extends kr.co.yna.cms.v2.yna.contents.domain.entity.AbstractEvent> eventType, Integer seq, String payload) {
        super(sourceId, sourceType, eventType, seq, payload);
    }
}
