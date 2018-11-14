package com.luebkemann.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


//CLASS FOR THE USER_ITEM TABLE
@Entity
@Table(name = "user_item")
public class ShoppingCart {

    @Column(name = "username")
    private String username;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_price")
    private double itemPrice;

    @Column(name = "quantity")
    private int quantity;

    private double total;

    public double getTotal() {
        return round(quantity * itemPrice, 2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    public String getUsername() {
        return username;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }


}
