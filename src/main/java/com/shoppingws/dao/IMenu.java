package com.shoppingws.dao;

import com.shoppingws.model.menu.Menu;
import com.shoppingws.model.menu.MenuCritea;

import java.util.List;

public interface IMenu {
    int addMenu(MenuCritea critea);
    List<Menu> getMenuList();
    int removeMenu(int categoryId);
}
