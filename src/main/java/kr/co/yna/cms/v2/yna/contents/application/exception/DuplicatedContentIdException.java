package kr.co.yna.cms.v2.yna.contents.application.exception;

public class DuplicatedContentIdException extends RuntimeException {
    public DuplicatedContentIdException(String message) {
        super(message);
    }
}
