package com.shoppingws.dao;

import com.shoppingws.model.user.User;

public interface IUser {
    boolean login(User critea);
    String getUserRole(int roleId);
}
