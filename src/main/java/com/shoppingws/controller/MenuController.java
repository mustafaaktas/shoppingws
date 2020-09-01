package com.shoppingws.controller;

import com.shoppingws.model.AjaxResponseBody;
import com.shoppingws.model.menu.Menu;
import com.shoppingws.model.menu.MenuCritea;
import com.shoppingws.services.MenuServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class MenuController {

    private final MenuServices menuServices;

    @Autowired
    public MenuController(MenuServices menuServices) {
        this.menuServices = menuServices;
    }

    AjaxResponseBody<String> resultDel=null;
    List<Menu> userList=null;
    AjaxResponseBody<MenuCritea> addCatResult=null;


    @RequestMapping("/menu")
    public String menuAdminList(Map<String, Object> model, HttpServletRequest request)
    {
        model.put("critea",new MenuCritea());
        return "admin/menu-index";
    }

    @RequestMapping("/menu/list")
    public String userList(Map<String, Object> model, HttpServletRequest request) {
       userList=new ArrayList<>();
        try{
            model.put("menuList",menuServices.getMenuList());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "admin/ajax/menu/ajax-menu-list";
    }
    @RequestMapping("/category/insert")
    public ResponseEntity<?> addUser(@ModelAttribute MenuCritea critea, Map<String, Object> model, HttpServletRequest request) {
        addCatResult=new AjaxResponseBody<>();
        try{
            if(menuServices.addCategory(critea)>0){
                addCatResult.setStatus("OK");
                addCatResult.setResult(critea);
            }else {
                addCatResult.setStatus("NOK");
                addCatResult.setResult(critea);
            }


        }catch (Exception e){
            e.printStackTrace();
            addCatResult.setResult(null);
            addCatResult.setStatus("NOK");
            addCatResult.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(addCatResult);
    }

    @RequestMapping("/category/del")
    public ResponseEntity<?> companyDel(@RequestParam("categoryId") int categoryId, Map<String, Object> model, HttpServletRequest request) {
        resultDel=new AjaxResponseBody<>();
        try {

            if(menuServices.removeMenu(categoryId)>0){
                resultDel.setStatus("OK");
                resultDel.setMsg("");
            }else{
                resultDel.setStatus("NOK");
                resultDel.setMsg("");
            }

        }catch (Exception e){
            resultDel.setStatus("NOK");
            resultDel.setMsg(e.getMessage());

        }
        return ResponseEntity.ok(resultDel);
    }

}
