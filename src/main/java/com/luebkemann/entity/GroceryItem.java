package com.luebkemann.entity;

import javax.persistence.*;

//CLASS FOR THE ITEMS TABLE
@Entity
@Table(name = "items")
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    public GroceryItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public GroceryItem(){ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
