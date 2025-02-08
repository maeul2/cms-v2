package kr.co.yna.cms.v2.yna.contents.application.article.cmd;

import kr.co.yna.cms.v2.yna.contents.application.cmd.Command;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

@ToString
@Getter
public class CreateArticle implements Command {
    private final String contentType;
    private final String detailAttribute;
    private final String registererId;

    private CreateArticle(String contentType, String detailAttribute, String registererId) {
        Assert.hasText(contentType, "contentsType must not be empty");
        Assert.hasText(detailAttribute, "detailAttribute must not be empty");
        Assert.hasText(registererId, "drafterId must not be empty");

        this.contentType = contentType;
        this.detailAttribute = detailAttribute;
        this.registererId = registererId;
    }

    public static CreateArticleBuilder builder() {
        return new CreateArticleBuilder();
    }

    @Override
    public String getCommanderId() {
        return this.registererId;
    }

    public static class CreateArticleBuilder {
        private String contentType;
        private String detailAttribute;
        private String registererId;

        CreateArticleBuilder() {
        }

        public CreateArticleBuilder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public CreateArticleBuilder detailAttribute(String detailAttribute) {
            this.detailAttribute = detailAttribute;
            return this;
        }

        public CreateArticleBuilder registererId(String registererId) {
            this.registererId = registererId;
            return this;
        }

        public CreateArticle build() {
            return new CreateArticle(this.contentType, this.detailAttribute, this.registererId);
        }

        public String toString() {
            return "CreateArticle.CreateArticleBuilder(contentType=" + this.contentType + ", detailAttribute=" + this.detailAttribute + ", registererId=" + this.registererId + ")";
        }
    }
}
