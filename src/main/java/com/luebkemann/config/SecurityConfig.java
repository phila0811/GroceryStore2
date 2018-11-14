package com.luebkemann.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

//SECURITY CONFIG CLASS
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // USES JDBC AUTHENTICATION
        //TELLS SPRING SECURITY TO USE JDBC AUTHENTICATION WITH OUR DATA SOURCE
        auth.jdbcAuthentication().dataSource(dataSource);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //SETS THE PRIVILEGES FOR ADMIN AND CUSTOMER ROLES
        //THIS ALSO WILL SET THE PROJECT TO TAKE US TO THE LOGIN PAGE ON START AND
        //WILL AUTHENTICATE THE USER WHEN THEY TRY TO SIGN IN.
        http.authorizeRequests()
                .antMatchers("/").hasRole("CUSTOMER")
                .antMatchers("/admin/*").hasRole("ADMIN")
                .and()
                .formLogin()
                    .loginPage("/showMyLoginPage")
                    .loginProcessingUrl("/authenticateTheUser").successForwardUrl("/loginProcess")
                    .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

    }

}
