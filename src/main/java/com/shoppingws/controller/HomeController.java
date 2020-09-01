package com.shoppingws.controller;

import com.shoppingws.services.MenuServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {


    @Autowired
    private MenuServices menuServices;

    @RequestMapping("")
    public String homePage(Map<String, Object> model, HttpServletRequest request, HttpSession session) {
        if(session.getAttribute("menus")==null){
            session.setAttribute("menus",menuServices.getMenuList());
        }
        return "index";
    }
    @RequestMapping("login")
    public String login( Map<String, Object> model, HttpServletRequest request) {
        return "login";

    }

}
