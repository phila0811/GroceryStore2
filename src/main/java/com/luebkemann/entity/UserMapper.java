package com.luebkemann.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//MAPPER FOR THE USER CLASS
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user1 = new User();
        user1.setUsername(resultSet.getString("username"));
        user1.setPassword(resultSet.getString("password"));

        return user1;
    }
}
