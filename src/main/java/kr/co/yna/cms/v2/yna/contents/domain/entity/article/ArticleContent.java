package kr.co.yna.cms.v2.yna.contents.domain.entity.article;

import jakarta.persistence.*;
import kr.co.yna.cms.v2.yna.contents.domain.entity.Department;
import kr.co.yna.cms.v2.yna.contents.domain.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Embeddable
public class ArticleContent {
    private final String title;
    private final String body;
    private final String source;

    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "WRITER_ID", updatable = false, table = "article_content")),
            @AttributeOverride(name = "name", column = @Column(name = "WRITER_NAME", updatable = false, table = "article_content")),
            @AttributeOverride(name = "department.id", column = @Column(name = "WRITER_DEPT_ID", updatable = false, table = "article_content")),
            @AttributeOverride(name = "department.name", column = @Column(name = "WRITER_DEPT_NAME", updatable = false, table = "article_content"))
    })
    @Embedded
    private Writer writer;
    @Column(name = "WRITE_DATETIME", updatable = false, table = "article_content")
    private OffsetDateTime writeDateTime;

    public static ArticleContentBuilder builder() {
        return new ArticleContentBuilder();
    }

    private ArticleContent(String title, String body, String source) {
        Assert.hasText(title, "title must not be empty");
        Assert.hasText(body, "body must not be empty");

        this.title = title;
        this.body = body;
        this.source = source;
    }

    void setWriter(User writer) {
        Assert.notNull(writer, "writer must not be null");

        this.writer = new Writer(writer.getId(), writer.getName(), new Department(writer.getDeptId(), writer.getDeptName()), writer.isDesk());
        this.writeDateTime = OffsetDateTime.now();
    }

    String getTitle() {
        return this.title;
    }

    String getBody() {
        return this.body;
    }

    Optional<String> getSource() {
        return Optional.ofNullable(this.source);
    }

    Optional<Writer> getWriter() {
        return Optional.ofNullable(this.writer);
    }

    Optional<OffsetDateTime> getWriteDateTime() {
        return Optional.ofNullable(this.writeDateTime);
    }

    boolean isTemp() {
        return this instanceof TempContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ArticleContent that)) return false;

        return Objects.equals(this.title, that.title) &&
                Objects.equals(this.body, that.body) &&
                Objects.equals(this.source, that.source) &&
                Objects.equals(this.writer, that.writer) &&
                Objects.equals(this.writeDateTime, that.writeDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.body, this.source, this.writer, this.writeDateTime);
    }

    @Override
    public String toString() {
        return "ArticleContents{" +
                "title=" + this.title +
                ", body=" + this.body +
                ", source=" + this.source +
                ", writer=" + this.writer +
                ", writeDateTime=" + this.writeDateTime +
                '}';
    }

    public static class ArticleContentBuilder {
        private String title;
        private String body;
        private String source;
        private Boolean temp;

        ArticleContentBuilder() {
        }

        public ArticleContentBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ArticleContentBuilder body(String body) {
            this.body = body;
            return this;
        }

        public ArticleContentBuilder source(String source) {
            this.source = source;
            return this;
        }

        public ArticleContentBuilder temp(boolean temp) {
            this.temp = temp;
            return this;
        }

        public ArticleContent build() {
            if (Boolean.TRUE.equals(this.temp)) {
                return new TempContent(this.title, this.body, this.source);
            }

            return new ArticleContent(this.title, this.body, this.source);
        }

        public String toString() {
            return "ArticleContent.ArticleContentBuilder(title=" + this.title + ", body=" + this.body + ", source=" + this.source + ")";
        }
    }

    private static class TempContent extends ArticleContent {
        private TempContent(String title, String body, String source) {
            super(title, body, source);
        }
    }
}
