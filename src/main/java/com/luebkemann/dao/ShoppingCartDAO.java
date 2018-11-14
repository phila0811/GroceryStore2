package com.luebkemann.dao;

import com.luebkemann.entity.GroceryItem;
import com.luebkemann.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartDAO {

    public void addItemToCart(GroceryItem item, String username);

    public int getNumberOfItemsInCart(String name);

    public void clearCart();

    public List<ShoppingCart> getShoppingCart(String username);

    public double getShoppingCartTotal(String username);
}
