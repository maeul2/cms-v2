package kr.co.yna.cms.v2.yna.contents.application.article.cmd;

import kr.co.yna.cms.v2.yna.contents.application.cmd.Command;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class ModifyArticle implements Command {
    private final String contentId;
    private final String modifierId;

    private final String title;
    private final String body;
    private final String source;
    private final boolean temp;

    private ModifyArticle(
            String contentId,
            String modifierId,
            String title,
            String body,
            String source,
            boolean temp
    ) {
        Assert.hasText(contentId, "콘텐츠 아이디가 유효하지 않습니다.");
        Assert.hasText(modifierId, "수정자 아이디가 유효하지 않습니다.");

        Assert.hasText(title, "제목이 유효하지 않습니다.");
        Assert.hasText(body, "본문이 유효하지 않습니다.");

        this.contentId = contentId;
        this.modifierId = modifierId;

        this.title = title;
        this.body = body;
        this.source = source;
        this.temp = temp;
    }

    public static ModifyArticleBuilder builder() {
        return new ModifyArticleBuilder();
    }

    @Override
    public String getCommanderId() {
        return this.modifierId;
    }

    public static class ModifyArticleBuilder {
        private String contentId;
        private String modifierId;
        private String title;
        private String body;
        private String source;
        private Boolean temp;

        ModifyArticleBuilder() {
        }

        public ModifyArticleBuilder contentId(String contentId) {
            this.contentId = contentId;
            return this;
        }

        public ModifyArticleBuilder modifierId(String modifierId) {
            this.modifierId = modifierId;
            return this;
        }

        public ModifyArticleBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ModifyArticleBuilder body(String body) {
            this.body = body;
            return this;
        }

        public ModifyArticleBuilder source(String source) {
            this.source = source;
            return this;
        }

        public ModifyArticleBuilder temp(boolean temp) {
            this.temp = temp;
            return this;
        }

        public ModifyArticle build() {
            return new ModifyArticle(this.contentId, this.modifierId, this.title, this.body, this.source, this.temp);
        }

        public String toString() {
            return "ModifyArticle.ModifyArticleBuilder(contentId=" + this.contentId + ", modifierId=" + this.modifierId + ", title=" + this.title + ", body=" + this.body + ", source=" + this.source + ", temp=" + this.temp + ")";
        }
    }
}
