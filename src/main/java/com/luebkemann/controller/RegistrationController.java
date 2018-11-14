package com.luebkemann.controller;

import com.luebkemann.dao.UserDAO;
import com.luebkemann.entity.User;
import com.luebkemann.exceptions.PasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Controller
public class RegistrationController {

    @Autowired
    public UserDAO userDAO;

    //TAKES USER TO THE REGISTER PAGE. ADDS A NEW USER AND AN EXCEPTION TO THE MODELANDVIEW
    @GetMapping("/register")
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("user", new User());
        mav.addObject("exception", "");

        return mav;
    }

    //TAKES THE USER FORM THE MODEL AND REGISTERS THE USER. WILL PASS AN EXCEPTION IF THE PASSWORD IS JUST SPACES
    @PostMapping("/registerProcess")
    public String addUser(@ModelAttribute("user") User user){
        if (user.getPassword().trim().length() == 0){
            throw new PasswordException("Password needs at least one character");
        }


            userDAO.registerUser(user);

            return "redirect:/";

    }

    //HANDLES SQLEXCEPTIONS. IF A USERNAME IS ALREADY TAKEN THIS WILL HANDLE THAT ERROR
    @ExceptionHandler
    public ModelAndView handleException(SQLException exe){
        ModelAndView model = new ModelAndView("register");
        model.addObject("exception", "Sorry, that username is already taken.");
        model.addObject("user", new User());

        return model;

    }

    //WILL HANDLE ERROR IF PASSWORD IS EMPTY
    @ExceptionHandler
    public ModelAndView handlePasswordException(PasswordException exe){
        ModelAndView model = new ModelAndView("register");
        model.addObject("exception", exe.getMessage());
        model.addObject("user", new User());

        return model;
    }




}
