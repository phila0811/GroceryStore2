package com.luebkemann.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//MAPPER FOR THE SHOPPINGCART CLASS
public class ShoppingCartMapper implements RowMapper<ShoppingCart> {

    @Override
    public ShoppingCart mapRow(ResultSet resultSet, int i) throws SQLException {
        ShoppingCart userItem = new ShoppingCart();
        userItem.setUsername(resultSet.getString("username"));
        userItem.setItemName(resultSet.getString("item_name"));
        userItem.setItemPrice(resultSet.getDouble("item_price"));
        userItem.setQuantity(resultSet.getInt("quantity"));

        return userItem;
    }
}
