package kr.co.yna.cms.v2.yna.authority.domain.service.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException(String message) {
        super(message);
    }
}
