package com.luebkemann.dao;

import com.luebkemann.entity.GroceryItem;
import com.luebkemann.entity.GroceryItemMapper;
import com.luebkemann.entity.ShoppingCart;
import com.luebkemann.entity.ShoppingCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class GroceryItemDAOImpl implements GroceryItemDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    //RETRIEVES ALL ITEMS FROM THE ITEMS TABLE AND ORDERS IT BY THE NAME.
    //RETURNS IT AS A LIST OF GROCERYITEMS
    @Override
    public List<GroceryItem> getItems() {
        String someSql = "SELECT * FROM items order by name";
        List<GroceryItem> groceryItems = jdbcTemplate.query(someSql, new GroceryItemMapper());
        return groceryItems;
    }

    //WILL SAVE THE ITEM TO THE DB.
    //IT WILL UPDATE THE ITEM IF IT ALREADY EXISTS.
    //ALSO UPDATES THE USER_ITEM TABLE FOR THE SHOPPING CART
    @Override
    public void saveItem(GroceryItem item) {
        int itemId = item.getId();

        String sql = "insert into items values(?, ?,?)";
        String updateSql = "update items set name = ?, price = ? where id=" + item.getId();
        String oldItemName = getOldItemName(itemId);
        String updateShoppingCartSql = "update user_item set item_name = ?, item_price = ? where item_name='" + oldItemName + "'";
        String checkForOldItemSql = "select * from user_item where item_name='" + oldItemName + "'";


        List<ShoppingCart> shoppingCarts = jdbcTemplate.query(checkForOldItemSql, new ShoppingCartMapper());
    //    String updateUserItemTable = "update user_items set item_name = ?, price = ? where item_name='" + item.getName() + "'";
        Object[] params = new Object[]{item.getId(), item.getName(), item.getPrice()};
        Object[] updateParams = new Object[]{item.getName(), item.getPrice()};
        Object[] updateShoppingCartParams = new Object[] {item.getName(), item.getPrice()};
        try {
            jdbcTemplate.update(sql, params);
        } catch (DuplicateKeyException exe){
            jdbcTemplate.update(updateSql, updateParams);
        } finally {
          if (shoppingCarts.size() > 0){
              jdbcTemplate.update(updateShoppingCartSql, updateShoppingCartParams);
          }
        }

    }

    //HELPER METHOD TO GET THE NAME OF AN ITEM BASED ON THE ID
    private String getOldItemName(int itemId) {
        String oldItemName;
        try {
             oldItemName = jdbcTemplate.queryForObject("select name from items where id=" + itemId, String.class);
        } catch (EmptyResultDataAccessException exe){
            oldItemName = null;
        }
        return oldItemName;
    }

    //RETRIEVES AND ITEM BASED ON THE ID
    @Override
    public GroceryItem getItem(int id) {
        String sql = "select * from items where id=" + id;
        List<GroceryItem> items = jdbcTemplate.query(sql, new GroceryItemMapper());

        return items.get(0);

    }

    //WILL DELETE AN ITEM FROM THE DB
    //ALSO DELETES IT FORM THE USER_ITEM TABLE EFFECTIVELY DELETING IT FROM ANY USERS CART
    @Override
    public void deleteItem(int id) {
        String sql = "delete from items where id=" + id;
        String itemName = jdbcTemplate.queryForObject("select name from items where id=" + id, String.class);
        String updateUserItemTableSql = "delete from user_item where item_name='" + itemName + "'";
        jdbcTemplate.update(sql);
        jdbcTemplate.update(updateUserItemTableSql);
    }

    @Override
    public List<GroceryItem> searchItem(String theSearchName) {
        return null;
    }

    //CLEARS ALL ITEMS FROM THE DB
    @Override
    public void clearItems() {
        String sql = "delete from items";
        jdbcTemplate.update(sql);

    }
}
