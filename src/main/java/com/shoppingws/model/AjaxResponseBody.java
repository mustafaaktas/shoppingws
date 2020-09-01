package com.shoppingws.model;

import lombok.Data;

public @Data
class AjaxResponseBody<T> {
    private String msg;
    private String status;
    private T result;
}
