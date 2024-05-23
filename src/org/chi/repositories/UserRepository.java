package org.chi.repositories;

import org.chi.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserRepository {


    public static Connection connect(){
//        String url = ""; //mysql -> localhost:3306, postqres ->localhost:5432
        String url = "jdbc:postgresql://localhost:5432/chi";
        String username = "postgres";
        String password = "password";
        try {
            return DriverManager.getConnection(url, username, password);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public User saveUser(User user){
        return  null;
    }
}
