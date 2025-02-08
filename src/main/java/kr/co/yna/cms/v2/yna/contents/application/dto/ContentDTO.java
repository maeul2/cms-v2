package kr.co.yna.cms.v2.yna.contents.application.dto;

import kr.co.yna.cms.v2.yna.contents.domain.entity.Content;
import org.springframework.util.Assert;

public class ContentDTO {
    private final Content content;

    public ContentDTO(Content content) {
        Assert.notNull(content, "content must not be null");

        this.content = content;
    }

    public String getId() {
        return this.content.getContentId().getValue();
    }
}
