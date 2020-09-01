package com.shoppingws.dao;

import com.shoppingws.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class UserDao implements IUser {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean login(User critea) {
        String sql = "select count(*) from schema_medblog.User u where u.userName=? and u.userPassword=?";
        boolean result = false;

        //The method queryForInt(String, Object...) from the type JdbcTemplate is deprecated
        int count = jdbcTemplate.queryForObject(sql, new Object[] { critea.getUserName(),critea.getUserPassword() },Integer.class);

        if (count > 0) {
            result = true;
        }

        return result;
    }

    @Override
    public String getUserRole(int roleId) {

        StringBuilder   sql=null;
        sql = new StringBuilder();
        sql.append(" select  roleName from schema_medblog.UserRole r where r.roleId=? ");

        String role= jdbcTemplate.queryForObject(sql.toString(),new Object[]{roleId}, String.class);

        return role;
    }
}
