package com.luebkemann.dao;

import com.luebkemann.entity.GroceryItem;

import java.util.List;

public interface GroceryItemDAO {

    public List<GroceryItem> getItems();

    public void saveItem(GroceryItem item);

    public GroceryItem getItem(int id);

    public void deleteItem(int id);

    public List<GroceryItem> searchItem(String theSearchName);

    public void clearItems();
}
