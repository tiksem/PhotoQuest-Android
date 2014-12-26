package com.utilsframework.android.json;

import java.io.IOException;

/**
 * Created by CM on 12/26/2014.
 */
public class RequestException extends IOException {
    private ExceptionInfo exceptionInfo;

    public RequestException(ExceptionInfo exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public ExceptionInfo getExceptionInfo() {
        return exceptionInfo;
    }
}
