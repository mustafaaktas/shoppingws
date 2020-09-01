package com.shoppingws.dao;

import com.shoppingws.model.menu.Menu;
import com.shoppingws.model.menu.MenuCritea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuDao implements IMenu {
    private static final Logger log = LoggerFactory.getLogger(MenuDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public int addMenu(MenuCritea critea) {
        critea.setSubid(0);
       return jdbcTemplate.update(
                "INSERT INTO med_schema.Menu (categoryName, subId,status) VALUES (?, ?,?)",
                new Object[]{critea.getCategoryName(), critea.getSubid(),1}

        );

    }

    @Override
    public List<Menu> getMenuList() {
        List<Menu>      menuList=null;
        StringBuilder   sql=null;
        sql = new StringBuilder();
        sql.append(" select * from schema_medblog.Menu a where a.status=1");
        menuList=new ArrayList<>();
        menuList= jdbcTemplate.query(sql.toString(),new BeanPropertyRowMapper(Menu.class));
        return menuList;
    }

    @Override
    public int removeMenu(int categoryId) {
        return jdbcTemplate.update(" update schema_medblog.Menu set status=0 where  id=?",new Object[]{categoryId});
    }
}
