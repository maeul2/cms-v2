package kr.co.yna.cms.v2.yna.contents.domain.service;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class AlreadyLockedException extends RuntimeException {
    private final String contentId;
    private final String userId;
    private final String userName;

    public AlreadyLockedException(String contentId, String userId, String userName) {
        Assert.hasText(contentId, "contentId must not be empty");
        Assert.hasText(userId, "userId must not be empty");
        Assert.hasText(userName, "userName must not be empty");

        this.contentId = contentId;
        this.userId = userId;
        this.userName = userName;
    }
}
