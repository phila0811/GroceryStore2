package com.luebkemann.controller;

import com.luebkemann.dao.GroceryItemDAO;
import com.luebkemann.dao.ShoppingCartDAO;
import com.luebkemann.entity.GroceryItem;
import com.luebkemann.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

//CONTROLLER CLASS FOR USERS WITH ADMIN PRIVILEGES
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    GroceryItemDAO groceryItemDAO;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;

    //THIS WILL TAKE US TO THE ADD ITEM PAGE. WILL ADD A NEW ITEM TO THE MODEL
    @RequestMapping("/showAddItemPage")
    public String showAddItemPage(Model model){
        GroceryItem item = new GroceryItem();

        model.addAttribute("item", item);
        return "add-item";
    }


    //THIS WILL RUN WHEN THE USER PRESSES THE ADD ITEM BUTTON. SAVES THE ITEM TO OUR DATABASE
    //RETURNS BACK TO OUR INDEX.JSP PAGE WITH A LIST OF ITEMS AND THE NUMBER OF ITEMS IN THE CART FOR THE CURRENT USER.
    @PostMapping("/addItem")
    public ModelAndView addItem(@ModelAttribute("item") GroceryItem groceryItem){
        groceryItemDAO.saveItem(groceryItem);
        ModelAndView model = new ModelAndView("index");
        model.addObject("items", groceryItemDAO.getItems());
        model.addObject("numberOfItems", shoppingCartDAO.getNumberOfItemsInCart(getSignedInName()));
//        ModelAndView model = new ModelAndView("index");
//        model.addObject("items", groceryItemDAO.getItems());

        return model;

    }

    //HELPER METHOD TO GET THE NAME OF THE CURRENTLY SIGNED IN USER
    private static String getSignedInName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    //RETURNS BACK OUT INDEX.JSP PAGE BUT WILL DELETE THE ITEM THAT WAS CLICKED ON.
    //THE ITEM ID IS PASSED THROUGH WHEN CLICKED AND WE DELETE IT USING OUR DAO
    @RequestMapping("/deleteItem")
    public ModelAndView deleteItem(@RequestParam("itemId") int theId){
        groceryItemDAO.deleteItem(theId);
        ModelAndView model = new ModelAndView("index");
        model.addObject("items", groceryItemDAO.getItems());
        model.addObject("numberOfItems", shoppingCartDAO.getNumberOfItemsInCart(getSignedInName()));


        return model;
    }

    //THIS WILL TAKE US TO THE ADD ITEM FORM. WE ADD AN ITEM TO THE MODEL TO FILL IN THE FORM WHEN THE PAGE IS LOADED
    //THIS IS BC WE ARE UPDATING THAT ITEM
    @GetMapping("/showUpdateForm")
    public String showUpdateForm(@RequestParam("itemId") int theId, Model model){

        GroceryItem item = groceryItemDAO.getItem(theId);

        model.addAttribute("item", item);

        return "add-item";
    }

}
