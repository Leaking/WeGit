package com.quinn.httpknife.github;

/**
 * Created by Quinn on 8/26/15.
 * 账号密码错误次数太多
 * statusCode: 403
 */
public class OverAuthError extends Exception {

    public OverAuthError(String exceptionMessage) {
        super(exceptionMessage);
    }
    public OverAuthError(){super();}
}
