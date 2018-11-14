package com.luebkemann.dao;

import com.luebkemann.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface UserDAO {

    public void registerUser(User user);

    public User validateLogin(User user);

    public User getUser(String name);


}
