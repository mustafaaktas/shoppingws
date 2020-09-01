package com.shoppingws.model.base;

import lombok.Getter;
import lombok.Setter;

public  class BaseCritea {
    private @Getter
    @Setter
    String userName;
    private @Setter
    String lang;

    public String getLang() {
        return  lang==null?"tr":lang;
    }
}
