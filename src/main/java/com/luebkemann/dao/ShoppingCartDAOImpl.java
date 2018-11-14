package com.luebkemann.dao;

import com.luebkemann.entity.GroceryItem;
import com.luebkemann.entity.ShoppingCart;
import com.luebkemann.entity.ShoppingCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ShoppingCartDAOImpl implements ShoppingCartDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //WILL ADD THE ITEM AND USER TO THE USER_ITEM TABLE
    //UPDATES THE TABLE IF THE USER ALREADY HAS SOME OF THE ITEM IN THEIR CART
    @Override
    public void addItemToCart(GroceryItem item, String username) {
        String sql = "insert into user_item values(?, ?, ?, ?)";
        String checkForItemSql = "select * from user_item where item_name='" + item.getName() + "' and username = '" + username + "'";
        String updateQuantitySql = "update user_item set quantity = ? where item_name='" + item.getName() + "' and username = '" + username + "'";
        String getQuantitySql = "select quantity from user_item where item_name='" + item.getName() + "' and username ='" + username +"'";
        List<ShoppingCart> shoppingCart = jdbcTemplate.query(checkForItemSql, new ShoppingCartMapper());
        if (shoppingCart.size() == 0) {
            jdbcTemplate.update(sql, new Object[]{username, item.getName(), item.getPrice(), 1});
        } else {
            int quantity = jdbcTemplate.queryForObject(getQuantitySql, Integer.class);
            quantity++;
            jdbcTemplate.update(updateQuantitySql, quantity);
        }
    }

    //WILL GET THE NUMBER OF ITEMS IN A PARTICULAR USERS CART
    @Override
    public int getNumberOfItemsInCart(String name) {
        String sql = "select sum(quantity) from user_item where username='" + name + "'";
        System.out.println("here");
        try {
            int sumOfQuantites = jdbcTemplate.queryForObject(sql, Integer.class);
            return sumOfQuantites;
        } catch (NullPointerException exe){
          return 0;
        }
    }

    //CLEARS THE TABLE USER_ITEM
    @Override
    public void clearCart() {
        String sql = "delete from user_item";
        jdbcTemplate.update(sql);

    }

    //WILL RETRIEVE THE SHOPPING CART AS A LIST FOR A PARTICULAR USER
    @Override
    public List<ShoppingCart> getShoppingCart(String username) {
        String sql = "select * from user_item where username='" + username + "' order by item_name";
        List<ShoppingCart> shoppingCart = jdbcTemplate.query(sql, new ShoppingCartMapper());

        return shoppingCart;
    }

    //WILL RETRIEVE THE SHOPPING CART TOTAL PRICE FOR A PARTICULAR USER
    @Override
    public double getShoppingCartTotal(String username) {
        String sql = "select * from user_item where username='" + username + "'";
        List<ShoppingCart> shoppingCarts = jdbcTemplate.query(sql, new ShoppingCartMapper());
        double total = 0;
        for (ShoppingCart cart : shoppingCarts){
            total += cart.getTotal();
        }

        return ShoppingCart.round(total, 2);
    }
}
