package com.company.common.login.sms.client;

import com.company.service.support.rest.api.v2.ApiResponse;

public class ResponseData<T> implements ApiResponse {

    private T response;

    private ResponseData() {}

    public ResponseData(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
