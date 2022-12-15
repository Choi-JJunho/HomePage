package com.junho.config.support.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 시스템 오류 -9900 ~ -9999 */
    INTERNAL_SERVER_ERROR(-9900, HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 처리중 오류가 발생했습니다."),
    PARAMETER_INVALID(-9901, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /* 인증 오류 -100 ~ -199 */
    AUTHENTICATION_INVALID(-100, HttpStatus.FORBIDDEN, "인증정보가 올바르지 않습니다."),
    AUTHORIZATION_INVALID(-101, HttpStatus.UNAUTHORIZED, "해당 서비스에 대한 권한이 존재하지 않습니다."),
    TOKEN_INVALID(-102, HttpStatus.FORBIDDEN, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(-103, HttpStatus.FORBIDDEN, "만료된 토큰입니다."),
    INVALID_LOGIN_INFO(-104, HttpStatus.FORBIDDEN, "잘못된 로그인정보입니다."),
    USER_NOT_FOUND(-105, HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    ALREADY_EXIST_ACCOUNT(-106, HttpStatus.CONFLICT, "이미 가입한 계정입니다."),

    /* 게시물 관련 -200 ~ -299  */
    ARTICLE_NOT_EXIST(-200, HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다.");

    /* 00 서비스 관련 오류  -200 ~ -299*/


    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String toString() {
        return "ErrorCode{" +
                "ErrorCodeName=" + name() +
                ", code=" + code +
                ", httpStatus=" + httpStatus +
                ", message='" + message + '\'' +
                '}';
    }
}
