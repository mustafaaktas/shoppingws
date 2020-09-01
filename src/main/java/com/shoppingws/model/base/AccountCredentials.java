package com.shoppingws.model.base;

import lombok.Data;

public @Data
class AccountCredentials {
	private String username;
	private String password;
	private String appName;
	private String lang;

}
