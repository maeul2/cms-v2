package kr.co.yna.cms.v2.yna.contents.domain.entity;

import jakarta.persistence.*;
import kr.co.yna.cms.v2.yna.common.util.EnumCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.util.Assert;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@MappedSuperclass
public abstract class Content extends EventHandler {
    @EmbeddedId
    private final ID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONTENT_ATTRIBUTE", updatable = false, nullable = false)
    private final Attribute contentAttribute;

    @Convert(converter = Status.Converter.class)
    @Column(name = "CMS_STATUS", nullable = false)
    private Status status;

    @Embedded
    private final Registerer registerer;
    @Column(name = "REGISTER_DATETIME", updatable = false, nullable = false)
    private final OffsetDateTime registerDateTime;

    @Embedded
    private Updater updater;
    @Column(name = "UPDATE_DATETIME")
    private OffsetDateTime updateDateTime;

    @Embedded
    private Desk desk;
    @Column(name = "DESK_DATETIME")
    private OffsetDateTime deskDateTime;

    @Column(name = "DIST_DATETIME")
    private OffsetDateTime distDateTime;

    @Column(name = "DELETE_YN", nullable = false)
    private Boolean deleted;

    @Version
    private long version;

    protected Content(
            ID contentsId,
            Attribute contentAttribute,
            User registerer,
            OffsetDateTime registerDateTime
    ) {
        Assert.notNull(contentsId, "콘텐츠 아이디가 null일 수 없습니다.");
        Assert.notNull(contentAttribute, "콘텐츠 속성이 null일 수 없습니다.");
        Assert.notNull(registerer, "등록자가 null일 수 없습니다.");
        Assert.notNull(registerDateTime, "등록 일시가 null일 수 없습니다.");

        this.id = contentsId;
        this.contentAttribute = contentAttribute;

        this.registerer = new Registerer(registerer.getId(), registerer.getName(), new Department(registerer.getDeptId(), registerer.getDeptName()), registerer.isDesk());
        this.registerDateTime = registerDateTime;

        this.deleted = false;
    }

    protected void audit(User user) {
        Assert.notNull(user, "수정자가 null일 수 없습니다.");

        this.updater = new Updater(
                user.getId(), user.getName(),
                new Department(user.getDeptId(), user.getDeptName()),
                user.isDesk()
        );
        this.updateDateTime = OffsetDateTime.now();

        if (user.isDesk()) {
            this.desk = new Desk(
                    user.getId(), user.getName(),
                    new Department(user.getDeptId(), user.getDeptName()),
                    user.isDesk()
            );
            this.deskDateTime = OffsetDateTime.now();
        }
    }

    public abstract void setStatus();

    protected void changeStatus(Status newStatus) {
        Assert.notNull(newStatus, "status must not be null.");

        this.status = newStatus;
    }

    public void delete() {
        this.deleted = true;
    }

    public ID getContentId() {
        return this.id;
    }

    public Attribute getContentAttribute() {
        return this.contentAttribute;
    }

    public abstract ContentType getContentType();
    public abstract DetailAttribute getDetailAttribute();

    public Status getStatus() {
        return this.status;
    }

    public User getRegister() {
        return this.registerer;
    }

    public OffsetDateTime getRegisterDateTime() {
        return this.registerDateTime;
    }

    public Optional<User> getUpdater() {
        return Optional.ofNullable(this.updater);
    }

    public Optional<OffsetDateTime> getUpdateDateTime() {
        return Optional.ofNullable(this.updateDateTime);
    }

    public Optional<User> getDesk() {
        return Optional.ofNullable(this.desk);
    }

    public Optional<OffsetDateTime> getDeskDateTime() {
        return Optional.ofNullable(this.deskDateTime);
    }

    public Optional<OffsetDateTime> getDistDateTime() {
        return Optional.ofNullable(this.distDateTime);
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
    @Embeddable
    public static class ID {
        @Column(name = "CONTENT_ID", updatable = false, nullable = false, unique = true)
        private final String value;

        public ID(String value) {
            Assert.hasText(value, "id value must not be null or empty");
            Assert.isTrue(20 == value.length(), "id value length must be 20");

            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof ID that)) return false;

            return Objects.equals(this.value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.value);
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    @Getter
    public enum Attribute implements EnumCode {
        A("기사"), P("사진");

        private final String desc;

        Attribute(String desc) {
            this.desc = desc;
        }

        @Override
        public String code() {
            return this.name();
        }

        public static Attribute fromCode(String code) {
            for (Attribute attribute : values()) {
                if (attribute.code().equals(code)) {
                    return attribute;
                }
            }

            throw new NoSuchElementException("code - " + code);
        }

        @Override
        public String toString() {
            return "Attribute{" +
                    "code=" + this.code() +
                    ", desc=" + this.desc +
                    '}';
        }
    }

    public enum Status implements EnumCode {
        DESK_BACK("DB", "재작성 지시"),
        DESK_DONE("DD", "데스크 완료"),
        DESK_KILL("DK", "데스크 삭제"),
        DESK_READY("DR", "데스크 대기"),
        DESK_SEND("DS", "데스크 송고"),
        DESK_HOLD("DH", "데스크 임시저장"),
        EDITOR_HOLD("EH", "작업자 임시저장 상태"),
        EDITOR_READY("ER", "작업(후통제) 대기"),
        EDITOR_KILL("EK", "작업자 삭제"),
        DESK_REQUEST("DQ", "데스크가 NPS로 콘텐츠 삭제요청");

        private final String code;
        private final String desc;

        Status(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static Status fromCode(String code) {
            for (Status status : values()) {
                if (status.code.equals(code)) {
                    return status;
                }
            }

            throw new NoSuchElementException("code - " + code);
        }

        @Override
        public String code() {
            return this.code;
        }

        @Override
        public String toString() {
            return "Status{" +
                    "code=" + this.code +
                    ", desc=" + this.desc +
                    '}';
        }

        @jakarta.persistence.Converter
        private static class Converter implements AttributeConverter<Status, String> {
            @Override
            public String convertToDatabaseColumn(Status attribute) {
                return attribute.code();
            }

            @Override
            public Status convertToEntityAttribute(String dbData) {
                return Status.fromCode(dbData);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Content that)) return false;

        return Objects.equals(this.id, that.id) &&
                this.status == that.status &&
                Objects.equals(this.registerer, that.registerer) &&
                Objects.equals(this.registerDateTime, that.registerDateTime) &&
                Objects.equals(this.updater, that.updater) &&
                Objects.equals(this.updateDateTime, that.updateDateTime) &&
                Objects.equals(this.desk, that.desk) &&
                Objects.equals(this.deskDateTime, that.deskDateTime) &&
                Objects.equals(this.distDateTime, that.distDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.status,
                this.registerer, this.registerDateTime,
                this.updater, this.updateDateTime,
                this.desk, this.deskDateTime,
                this.distDateTime
        );
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + this.id +
                ", contentAttribute=" + this.contentAttribute +
                ", status=" + this.status +
                ", registerer=" + this.registerer +
                ", registerDateTime=" + this.registerDateTime +
                ", updater=" + this.updater +
                ", updateDateTime=" + this.updateDateTime +
                ", desk=" + this.desk +
                ", deskDateTime=" + this.deskDateTime +
                ", distDateTime=" + this.distDateTime +
                ", deleted=" + this.deleted +
                '}';
    }
}
