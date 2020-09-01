package com.shoppingws.dao.role;
import com.shoppingws.model.user.Role;

import java.util.List;

public interface IRoleDao {
	
	public List<Role> getUserAuth(String username);

}
