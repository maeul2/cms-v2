package kr.co.yna.cms.v2.yna.authority.domain.service.exception;

public class UnAuthenticatedException extends RuntimeException {
    public UnAuthenticatedException(String message) {
        super(message);
    }
}
