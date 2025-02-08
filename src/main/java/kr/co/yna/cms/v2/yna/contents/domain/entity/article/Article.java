package kr.co.yna.cms.v2.yna.contents.domain.entity.article;

import jakarta.persistence.*;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import kr.co.yna.cms.v2.yna.contents.domain.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "CMS_CONTENT")
@SecondaryTable(
        name = "ARTICLE_CONTENT",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "ARTICLE_ID", referencedColumnName = "CONTENT_ID")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Article extends Content {
    @Enumerated(EnumType.STRING)
    @Column(name = "CONTENT_TYPE", updatable = false, nullable = false)
    private final ContentType contentType;

    @Convert(converter = DetailAttribute.Converter.class)
    @Column(name = "DETAIL_ATTRIBUTE", updatable = false, nullable = false)
    private final DetailAttribute detailAttribute;

    @AttributeOverrides({
            @AttributeOverride(name = "title", column = @Column(name = "TITLE", table = "article_content")),
            @AttributeOverride(name = "body", column =  @Column(name = "BODY", table = "article_content")),
            @AttributeOverride(name = "source", column = @Column(name = "SOURCE", table = "article_content"))
    })
    @Embedded
    private ArticleContent articleContent;

    public static DraftBuilder draft() {
        return new DraftBuilder();
    }

    private Article(
            Content.ID contentId,
            User registerer,
            ContentType contentType,
            DetailAttribute detailAttribute
    ) {
        super(
                contentId,
                Attribute.A,
                registerer,
                OffsetDateTime.now()
        );

        Assert.notNull(contentType, "콘텐츠 타입이 null일 수 없습니다.");
        Assert.notNull(detailAttribute, "상세 속성이 null일 수 없습니다.");

        this.contentType = contentType;
        this.detailAttribute = detailAttribute;
    }

    public Article copy() {
        return new Article(this);
    }

    private Article(Article origin) {
        super(
                origin.getContentId(),
                origin.getContentAttribute(),
                origin.getRegister(),
                origin.getRegisterDateTime()
        );

        this.contentType = origin.contentType;
        this.detailAttribute = origin.detailAttribute;
        this.articleContent = origin.articleContent;
    }

    public void modify(ArticleContent articleContent, User modifier) {
        Assert.notNull(articleContent, "articleContent must not be null.");
        Assert.notNull(modifier, "modifier must not be null.");

        if (isDraft() && !articleContent.isTemp()) {
            articleContent.setWriter(modifier);
        } else {
            getWriter().ifPresent(articleContent::setWriter);
        }

        this.articleContent = articleContent;

        audit(modifier);
    }

    public boolean isDraft() {
        return (this instanceof Draft && null == this.articleContent) ||    //Draft타입이지만 수정이 한 번이라도 된적 있으면 초안 아님
                (null == this.articleContent || this.articleContent.getWriter().isEmpty()); //수정 된 적 없거나 수정 된적 있어도 '송고'된 적 없으면 초안
    }

    @Override
    public void setStatus() {
        boolean isDesk = getUpdater().map(User::isDesk)
                .orElseThrow(() -> new IllegalStateException("Modifier not set"));

        if (isDesk) {
            if (isTemp()) {
                changeStatus(Status.DESK_HOLD);
            } else {
                changeStatus(Status.DESK_DONE);
            }
        } else {
            if (isTemp()) {
                changeStatus(Status.EDITOR_HOLD);
            } else {
                changeStatus(Status.DESK_READY);
            }
        }
    }

    private boolean isTemp() {
        if (null == this.articleContent) {
            throw new UnsupportedOperationException();
        }

        return this.articleContent.isTemp();
    }

    @Override
    public ContentType getContentType() {
        return this.contentType;
    }

    @Override
    public DetailAttribute getDetailAttribute() {
        return this.detailAttribute;
    }

    public String getTitle() {
        return Optional.ofNullable(this.articleContent)
                .map(ArticleContent::getTitle)
                .orElse("");
    }

    public String getBody() {
        return Optional.ofNullable(this.articleContent)
                .map(ArticleContent::getBody)
                .orElse("");
    }

    public Optional<String> getSource() {
        return Optional.ofNullable(this.articleContent)
                .flatMap(ArticleContent::getSource);
    }

    public Optional<User> getWriter() {
        return Optional.ofNullable(this.articleContent)
                .flatMap(ArticleContent::getWriter);
    }

    public Optional<OffsetDateTime> getWriteDateTime() {
        return Optional.ofNullable(this.articleContent)
                .flatMap(ArticleContent::getWriteDateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Article that)) return false;
        if (!super.equals(o)) return false;

        return this.contentType == that.contentType &&
                this.detailAttribute == that.detailAttribute &&
                Objects.equals(this.articleContent, that.articleContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.contentType, this.detailAttribute, this.articleContent);
    }

    @Override
    public String toString() {
        return "Article{" +
                "content=" + super.toString() +
                ", contentType=" + this.contentType +
                ", detailAttribute=" + this.detailAttribute +
                ", articleContent=" + this.articleContent +
                '}';
    }

    public enum ContentType implements kr.co.yna.cms.v2.yna.contents.domain.entity.ContentType {
        KR("국문"),
        EN("영문"),
        SP("스페인어"),
        JP("일문"),
        FR("프랑스어"),
        CK("중국어 간체"),
        CB("중국어 번체"),
        AR("아랍어");

        private final String desc;

        ContentType(String desc) {
            this.desc = desc;
        }

        @Override
        public String code() {
            return this.name();
        }

        public static ContentType fromCode(String code) {
            return valueOf(code);
        }

        @Override
        public String toString() {
            return "ContentsType{" +
                    "code=" + this.code() +
                    ", desc=" + this.desc +
                    '}';
        }
    }

    public enum DetailAttribute implements kr.co.yna.cms.v2.yna.contents.domain.entity.DetailAttribute {
        _0("0", "국문기사"),
        _1("1", "뉴스 뒤 뉴스"),
        _2("2", "오늘의 일정"),
        _3("3", "인포맥스"),
        _4("4", "신화사 국문기사"),
        _5("5", "KTX 문자뉴스"),
        _6("6", "MOB 문자뉴스"),
        _7("7", "오늘의 주요뉴스 문자뉴스"),
        _8("8", "SMS 메시지"),
        _9("9", "TV 스크립트"),
        A("A", "편집회의 자료"),
        B("B", "정보보고"),
        C("C", "참고자료"),
        D("D", "홈페이지 전용기사"),
        X("X", "인사"),
        Y("Y", "부고"),
        Z("Z", "동정");

        private final String code;
        private final String desc;

        DetailAttribute(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public String code() {
            return this.code;
        }

        @Override
        public String toString() {
            return "DetailAttribute{" +
                    "code=" + this.code +
                    ", desc=" + this.desc +
                    '}';
        }

        public static DetailAttribute fromCode(String code) {
            for (DetailAttribute detailAttribute : values()) {
                if (detailAttribute.code.equals(code)) {
                    return detailAttribute;
                }
            }

            throw new NoSuchElementException("code - " + code);
        }

        @jakarta.persistence.Converter
        private static class Converter implements AttributeConverter<DetailAttribute, String> {
            @Override
            public String convertToDatabaseColumn(DetailAttribute attribute) {
                return attribute.code();
            }

            @Override
            public DetailAttribute convertToEntityAttribute(String dbData) {
                return DetailAttribute.fromCode(dbData);
            }
        }
    }

    public static class DraftBuilder {
        private ID contentId;
        private User registerer;
        private ContentType contentType;
        private DetailAttribute detailAttribute;

        DraftBuilder() {
        }

        public DraftBuilder contentId(ID contentId) {
            this.contentId = contentId;
            return this;
        }

        public DraftBuilder registerer(User registerer) {
            this.registerer = registerer;
            return this;
        }

        public DraftBuilder contentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public DraftBuilder detailAttribute(DetailAttribute detailAttribute) {
            this.detailAttribute = detailAttribute;
            return this;
        }

        public Article build() {
            return new Draft(this.contentId, this.registerer, this.contentType, this.detailAttribute);
        }

        public String toString() {
            return "Article$DraftBuilder(contentId=" + this.contentId + ", registerer=" + this.registerer + ", contentType=" + this.contentType + ", detailAttribute=" + this.detailAttribute + ")";
        }
    }

    private static class Draft extends Article {
        private Draft(
                ID contentId,
                User registerer,
                ContentType contentType,
                DetailAttribute detailAttribute
        ) {
            super(contentId, registerer, contentType, detailAttribute);
        }

        @Override
        public void setStatus() {
            changeStatus(Status.EDITOR_READY);
        }
    }
}
