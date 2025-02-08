package kr.co.yna.cms.v2.yna.history.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.OffsetDateTime;
import java.util.Objects;


@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@MappedSuperclass
public abstract class AbstractEvent {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "EVENT_ID", nullable = false, unique = true, updatable = false)
    private final String eventId;
    @Column(name = "SOURCE_ID", nullable = false, updatable = false)
    private final String sourceId;
    @Column(name = "SOURCE_TYPE", nullable = false, updatable = false)
    private final String sourceType;
    @Column(name = "EVENT_TYPE", nullable = false, updatable = false)
    private final Class<?> eventType;
    @Column(name = "SEQ", nullable = false, updatable = false)
    private final Integer seq;
    @Lob
    @Column(name = "PAYLOAD", nullable = false, updatable = false)
    private final String payload;
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private final OffsetDateTime createdAt;

    protected AbstractEvent(String sourceId, String sourceType, Class<?> eventType, int seq, String payload) {
        Assert.hasText(sourceId, "sourceId must not be empty");
        Assert.hasText(sourceType, "sourceType must not be empty");
        Assert.notNull(eventType, "eventType must not be null");
        Assert.hasText(payload, "payload must not be empty");

        this.eventId = null;
        this.eventType = eventType;
        this.seq = seq;
        this.payload = payload;

        this.sourceId = sourceId;
        this.sourceType = sourceType;

        this.createdAt = OffsetDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AbstractEvent that)) return false;

        return Objects.equals(this.eventId, that.eventId) &&
                Objects.equals(this.sourceId, that.sourceId) &&
                Objects.equals(this.eventType, that.eventType) &&
                Objects.equals(this.seq, that.seq) &&
                Objects.equals(this.payload, that.payload) &&
                Objects.equals(this.createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.eventId, this.sourceId, this.eventType, this.seq, this.payload, this.createdAt);
    }
}
