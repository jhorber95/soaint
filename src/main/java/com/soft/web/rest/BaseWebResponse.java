package com.soft.web.rest;

import com.soft.web.rest.errors.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BaseWebResponse<T> {
    private ErrorCode errorCode;
    private T data;

    public static BaseWebResponse successNoData() {
        return BaseWebResponse.builder()
            .build();
    }

    public static <T> BaseWebResponse<T> successWithData(T data) {
        return BaseWebResponse.<T>builder()
            .data(data)
            .build();
    }

    public static BaseWebResponse error(ErrorCode errorCode) {
        return BaseWebResponse.builder()
            .errorCode(errorCode)
            .build();
    }
}
