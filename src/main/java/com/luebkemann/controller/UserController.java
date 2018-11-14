package com.luebkemann.controller;

import com.luebkemann.dao.GroceryItemDAO;
import com.luebkemann.dao.UserDAO;
import com.luebkemann.dao.ShoppingCartDAO;
import com.luebkemann.entity.GroceryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


//CONTROLLER CLASS FOR USERS WITH BASE PRIVILEGES
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    GroceryItemDAO groceryItemDAO;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;

    public UserController(UserDAO userDAO, GroceryItemDAO groceryItemDAO, ShoppingCartDAO shoppingCartDAO) {
        this.userDAO = userDAO;
        this.groceryItemDAO = groceryItemDAO;
        this.shoppingCartDAO = shoppingCartDAO;
    }

    //WILL RUN WHEN USERS PRESS ADD TO CART BUTTON
    //WILL UPDATE THE NUMBER OF ITEMS IN THE CART, AND THE LIST OF USER_IETM IN OUR MYSQL DB
    //RETURNS US TO THE SAME PAGE (INDEX.JSP)
    @RequestMapping("/addItemsToCart")
    public ModelAndView addItemsToCart(@RequestParam("numberOfItems") String number, @RequestParam("itemId") int itemId){
        int numberOfItems = Integer.parseInt(number);
        GroceryItem item = groceryItemDAO.getItem(itemId);
        ModelAndView model = new ModelAndView("index");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();


        for (int i = 0; i < numberOfItems; i++) {
            shoppingCartDAO.addItemToCart(item, name);
        }
        model.addObject("items", groceryItemDAO.getItems());
        model.addObject("numberOfItems", shoppingCartDAO.getNumberOfItemsInCart(name));


        return model;
    }

    //THIS WILL TAKE US TO THE SHOPPING-CART.JSP PAGE.
    //PASSES THE ITEMS/NUMBER OF ITEMS FOR THAT USER TO THE MODEL
    @RequestMapping("/showShoppingCart")
    public ModelAndView showShoppingCart(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        ModelAndView model = new ModelAndView("shopping-cart");
        model.addObject("shoppingCartItems", shoppingCartDAO.getShoppingCart(name));
        model.addObject("total", shoppingCartDAO.getShoppingCartTotal(name));


        return model;
    }

    //TAKES USER BACK TO INDEX.JSP FROM THE SHOPPING-CART PAGE.
    //ADDS THE LIST OF ITEMS AND THE NUMBER OF ITEMS IN THE USERS CART TO THE MODEL
    @GetMapping("/showItemPage")
    public ModelAndView showItemPage(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username= auth.getName();
        ModelAndView model = new ModelAndView("index");
        model.addObject("items", groceryItemDAO.getItems());
        model.addObject("numberOfItems", shoppingCartDAO.getNumberOfItemsInCart(username));

        return model;
    }

}
