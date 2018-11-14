package com.luebkemann.config;

import com.luebkemann.dao.UserDAO;
import com.luebkemann.dao.UserDAOImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

//config class for the application. Use this Java Configuration page instead of using an xml config page
//Need the property source annotation for the properties file used in my datasource bean.
@Configuration
@EnableWebMvc
@ComponentScan("com.luebkemann")
@PropertySource("classpath:persistence-mysql.properties")
public class AppConfig {

    //Use this to access the properties file
    @Autowired
    Environment environment;

    //All of my views are located in the /WEB-INF/view directory
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    //Datasource bean configured to use a mysql db. All info for the database comes from the presistence-mysql.properties file
    @Bean
    DataSource dataSource(){

        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        //Use our environment variable to set all of our datasource properties.
        try{
            dataSource.setDriverClass(environment.getProperty("jdbc.driver"));
        } catch (PropertyVetoException exe){
            throw new RuntimeException(exe);
        }
        dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        dataSource.setUser(environment.getProperty("jdbc.user"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));

        dataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        dataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        dataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        dataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        return dataSource;

    }

    //Helper method to get the integer value of a property in our properties file.
    private int getIntProperty(String propName){
        String propVal = environment.getProperty(propName);
        //now convert to int
        int intProp = Integer.parseInt(propVal);

        return intProp;
    }

    //Bean for JdbcTemplate class. We use this in all of our DAO classes.
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }



}
