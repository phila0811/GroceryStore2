package com.luebkemann.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//ENTITY CLASS FOR THE AUTHORITIES TABLE
@Entity
@Table(name = "authorities")
public class Authorites {

    @Column(name = "username")
    private String username;

    private String authority;
}
