package com.luebkemann.dao;

import com.luebkemann.entity.User;
import com.luebkemann.entity.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //WILL ADD USER INTO THE USERS TABLE AND ASSIGN THEM THE ROLE OF CUSTOMER IN THE AUTHORITIES TABLE
    @Override
    public void registerUser(User user) {
        String sql = "insert into users values(?, ?, ?)";
        String sql2 = "insert into authorities values(?, ?)";
        jdbcTemplate.update(sql, new Object[] { user.getUsername(), user.getPassword(), 1});
        jdbcTemplate.update(sql2, user.getUsername(), "ROLE_CUSTOMER");

    }

    //CHECKS IF A USER AND THEIR PASSWORD IS IN THE USER TABLE
    @Override
    public User validateLogin(User user) {
        String sql = "select * from users where username='" + user.getUsername() + "' and password='" + user.getPassword() + "'";

        List<User> users = jdbcTemplate.query(sql, new UserMapper());
       return users.size() > 0 ? users.get(0) : null;
    }

    //RETRIEVES A USER FROM THE USER TABLES
    @Override
    public User getUser(String name) {
        String sql = "select * from users where username='" + name + "'";
        List<User>  users = jdbcTemplate.query(sql, new UserMapper());

        return users.get(0);
    }
}
