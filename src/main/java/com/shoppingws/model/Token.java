package com.shoppingws.model;

import lombok.Data;

public @Data
class Token {
    private String  accessToken;
    private int     tokenExpires;
}
