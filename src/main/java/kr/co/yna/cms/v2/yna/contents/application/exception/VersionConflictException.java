package kr.co.yna.cms.v2.yna.contents.application.exception;

public class VersionConflictException extends RuntimeException {
    public VersionConflictException(String message) {
        super(message);
    }
}
