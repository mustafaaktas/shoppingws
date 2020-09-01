package com.shoppingws.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.shoppingws.model.base.AbstractBaseObject;
import com.shoppingws.util.Helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class User extends AbstractBaseObject<User>  implements Serializable {
	private static final long serialVersionUID = 4313648554965359458L;
	private int userNo;
	private String name;
	private String lastName;
	private String email;
	private String userName;
	private List<Role> roles = new ArrayList<>();
	private String loginTime;
	private Boolean loggedIn;
	private String lang;

	public User() {
		super();
	}

	public User(int userNo, String name, String lastName) {
		super();
		this.userNo = userNo;
		this.name = Helper.checkNulls(name,"");
		this.lastName = Helper.checkNulls(lastName,"");
		this.userName = null;
		this.roles = null;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userNo != other.userNo)
			return false;
		return true;
	}


	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	public String getRolesAsString() {		
		StringBuilder userRoles = new StringBuilder();
		if(getRoles()!=null && !getRoles().isEmpty()) {
			for (Role role : getRoles()) {
				if(role!=null) {
					userRoles.append("'" + role.getRoleCode() + "',");
				}
			}
		}
		return userRoles.toString();
	}


	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
}
