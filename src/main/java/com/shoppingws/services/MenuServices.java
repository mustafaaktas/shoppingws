package com.shoppingws.services;

import com.shoppingws.dao.IMenu;
import com.shoppingws.model.menu.Menu;
import com.shoppingws.model.menu.MenuCritea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServices {
    @Autowired
    IMenu iMenu;

    public List<Menu> getMenuList(){
        return iMenu.getMenuList();
    }
    public int addCategory(MenuCritea critea){
        return iMenu.addMenu(critea);
    }
    public int removeMenu(int categoryId){
        return iMenu.removeMenu(categoryId);
    }
}
