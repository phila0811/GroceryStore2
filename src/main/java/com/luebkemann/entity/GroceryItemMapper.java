package com.luebkemann.entity;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//MAPPER FOR GROCERY ITEMS
public class GroceryItemMapper implements RowMapper<GroceryItem> {
    @Override
    public GroceryItem mapRow(ResultSet resultSet, int i) throws SQLException {
        GroceryItem groceryItem = new GroceryItem();

        groceryItem.setId(resultSet.getInt("id"));
        groceryItem.setName(resultSet.getString("name"));
        groceryItem.setPrice(resultSet.getDouble("price"));

        return groceryItem;
    }
}
