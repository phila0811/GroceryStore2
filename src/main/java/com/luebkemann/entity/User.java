package com.luebkemann.entity;

import javax.persistence.*;
import java.util.List;

//CLASS FOR THE USERS TABLE
@Entity
@Table(name = "users")
public class User {


    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    private List<GroceryItem> shoppingCart;

    public List<GroceryItem> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<GroceryItem> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void addToCart(GroceryItem item){
        shoppingCart.add(item);
    }

    public String getUsername() {
        return username;
    }



    public void setUsername(String username) {
        this.username = username;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = "{noop}" + password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
