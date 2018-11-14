package com.luebkemann.controller;

import com.luebkemann.dao.GroceryItemDAO;
import com.luebkemann.dao.ShoppingCartDAO;
import com.luebkemann.dao.UserDAO;
import com.luebkemann.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    GroceryItemDAO groceryItemDAO;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;


    @RequestMapping("/showMyLoginPage")
    public String showMyLoginPage(){
        return "login";
    }

    @GetMapping("/")
    public String showHome(){
        return "index";
    }
    @GetMapping("/showSignUpPage")
    public String showSignUpPage(){
        return "register";
    }

//    @GetMapping("/login")
//    public ModelAndView showLogin(HttpServletResponse response, HttpServletRequest request){
//        ModelAndView mav = new ModelAndView("index");
//        mav.addObject("username", new User());
//        mav.addObject("items", groceryItemDAO.getItems());
//
//        return mav;
//    }

    //THIS WILL RUN WHEN A USER TRIES TO LOGIN
    @PostMapping("/loginProcess")
    public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
                                     @ModelAttribute("login") User user) {

        ModelAndView mav = null;
        System.out.println(user);

        User user1 = userDAO.validateLogin(user);

        //WHEN THE USER EXISTS IT WILL TAKE THEM TO THE INDEX.JSP PAGE AND PASS THE NECESSARY ATTRIBUTES
        if (null != user1){
            mav = new ModelAndView("index");
            mav.addObject("username", user1.getUsername());
            mav.addObject("message", "");
            mav.addObject("items", groceryItemDAO.getItems());
            mav.addObject("numberOfItems", shoppingCartDAO.getNumberOfItemsInCart(user1.getUsername()));
            } else {
            //IF THE USER DOESN'T EXIST IT RETURNS THEM TO THE LOGIN PAGE WITH AN ERROR MESSAGE
            mav = new ModelAndView("login");
            mav.addObject("message", "Username or Password is wrong!");
        }

        return mav;
    }


}
