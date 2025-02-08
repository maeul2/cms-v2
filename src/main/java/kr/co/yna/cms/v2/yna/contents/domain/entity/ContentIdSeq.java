package kr.co.yna.cms.v2.yna.contents.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Table(name = "CONTENT_ID_SEQ")
@Entity
public class ContentIdSeq {
    @EmbeddedId
    private final Key key;
    @Column(name = "SEQ", nullable = false)
    private int seq;

    public ContentIdSeq(Key key) {
        Assert.notNull(key, "key must not be null");

        this.key = key;
        this.seq = 1;
    }

    public void increaseSeq() {
        this.seq++;
    }

    public Content.ID newContentsId(Department department) {
        Assert.notNull(department, "부서가 null일 수 없습니다.");

        return new Content.ID(
                this.key.contentAttribute() +
                        this.key.contentType() +
                        this.key.createdDate() +
                        String.format("%06d", this.seq) +
                        department.getId()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ContentIdSeq that)) return false;

        return this.seq == that.seq && Objects.equals(this.key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key, this.seq);
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
    @Embeddable
    public static class Key implements Serializable {
        private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");
        private static final DateTimeFormatter UUUU_MM_DD = DateTimeFormatter.ofPattern("uuuuMMdd");

        @Column(name = "CONTENT_ATTRIBUTE", nullable = false, updatable = false)
        private final String contentAttribute;
        @Column(name = "CONTENT_TYPE", nullable = false, updatable = false)
        private final String contentType;
        @Column(name = "CREATED_DATE", nullable = false, updatable = false)
        private final String createdDate;

        public Key(Content.Attribute contentAttribute, ContentType contentType) {
            Assert.notNull(contentAttribute, "contentAttribute must not be null");
            Assert.notNull(contentType, "contentType must not be null");

            this.contentAttribute = contentAttribute.name();
            this.contentType = contentType.code();

            this.createdDate = LocalDate.now(ZONE_ID).format(UUUU_MM_DD);
        }

        private String contentAttribute() {
            return this.contentAttribute;
        }

        private String contentType() {
            return this.contentType;
        }

        private String createdDate() {
            return this.createdDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof Key that)) return false;

            return Objects.equals(this.contentAttribute, that.contentAttribute) &&
                    Objects.equals(this.contentType, that.contentType) &&
                    Objects.equals(this.createdDate, that.createdDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.contentAttribute, this.contentType, this.createdDate);
        }
    }
}
