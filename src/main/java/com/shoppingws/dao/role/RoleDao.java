package com.shoppingws.dao.role;

import com.shoppingws.model.user.Role;
import com.shoppingws.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDao  implements IRoleDao {

	@Autowired
	ConnectionPool dataSource;

	public List<Role> getUserAuth(String username){
		List<Role> roles = new ArrayList<>();
		Connection conn 	= null;
		ResultSet rs 		= null;
		Statement stm 		= null;
		StringBuilder sql 	= null;
		int personelNo = 0;
		String authStr="";
		try{

			conn=this.dataSource.getConnection();
			sql = new StringBuilder();
			sql.append(" select cc.ws_auth as auth  from atlas.websrv_users cc where  cc.ws_username='"+username.toUpperCase()+"' and cc.ws_status='A' ");
			stm = conn.createStatement();
			rs = (ResultSet) stm.executeQuery(sql.toString());
			//System.out.println("sql01 rol :"+sql.toString());
			if(rs.next()) {
				authStr += Helper.checkNulls( rs.getString("auth"),"")+",";

			}
			sql = new StringBuilder();
			sql.append(" select  case when COUNT(pp.page_id)> 0 then 'DCSMOBILEAPP' else '' end as auth  from  adsdcs.users_pages pp  where pp.user_id =(select user_id from dcs_users where user_name='"+username.toUpperCase()+"') and page_id=342 ");
			stm = conn.createStatement();
			rs = (ResultSet) stm.executeQuery(sql.toString());
			//System.out.println("sql02 rol :"+sql.toString());
			if(rs.next()) {
				authStr += Helper.checkNulls( rs.getString("auth"),"")+",";

			}
			sql = new StringBuilder();
			sql.append(" select pp.usertype_name as auth from adsdcs.dcs_usertype pp where pp.usertype_id=(select user_type from dcs_users where user_name='"+username.toUpperCase()+"') and usertype_status=1 ");
			stm = conn.createStatement();
			rs = (ResultSet) stm.executeQuery(sql.toString());
			//System.out.println("sql03 rol :"+sql.toString());
			if(rs.next()) {
				authStr +=Helper.checkNulls( rs.getString("auth"),"")+",";

			}

			String[] authStrArr=authStr.split(",");
			for (String item:authStrArr) {
				if(item!=null && item.trim().length()>0){
					personelNo++;
					roles.add(new Role(personelNo,item.trim(),item.trim()));
				}
			}

		}catch(Exception e){
			Helper.errorLogger(getClass(), e,"[SQL]..:" + (null!=sql?sql.toString():"") + " [UserID]..:" + personelNo);
		}finally{
			dataSource.closeConnection(conn);
			dataSource.closeResultSet(rs);
			dataSource.closeStatement(stm);
		}
		
		return roles;
	}
	

	
}
