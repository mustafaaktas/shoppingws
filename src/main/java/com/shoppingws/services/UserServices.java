package com.shoppingws.services;

import com.shoppingws.dao.IUser;
import com.shoppingws.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {
    @Autowired
    private IUser iUser;

    boolean login(User critea){
        return iUser.login(critea);
    }
    String getUserRole(int roleId){
        return iUser.getUserRole(roleId);
    }

}
