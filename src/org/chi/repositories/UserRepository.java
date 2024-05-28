package org.chi.repositories;

import org.chi.exceptions.UserUpdateFailedException;
import org.chi.models.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static java.time.chrono.JapaneseEra.values;

@SuppressWarnings(value = {"all"})
public class UserRepository {


    public static Connection connect(){
//        String url = ""; //mysql -> localhost:3306, postqres ->localhost:5432
        //String url ="//localhost:5432/chi?createDatabaseIfNotExist=true";

        String url = "jdbc:postgresql://localhost:5432/chi";
        String username = "postgres";
        String password = "password";
        try {
            return DriverManager.getConnection(url, username, password);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public User saveUser(User user) {
//        String getIdSqlStatement = "select count(*) from users";
        String sql = "insert into users (id, wallet_id)values(?,?)";
        
        try(Connection connection =connect()){
            var preparedStatement = connection.prepareStatement(sql);
            Long id = generateId();
            preparedStatement.setLong(1,id);
            preparedStatement.setLong(2,user.getWalletId());
//            if(user.getWalletId() != null)
//                preparedStatement.setLong(2,user.getWalletId());
            preparedStatement.execute();
            return  getUserBy(id);
        }catch (SQLException exception){
            System.err.println(exception.getMessage());
            throw new RuntimeException("failed to connect to database");
        }

    }

    private Long generateId(){
        try (Connection connection = connect()){
            String sql = "SELECT max(id) FROM users";
            var statememt = connection.prepareStatement(sql);
            ResultSet resultSet = statememt.executeQuery();
            resultSet.next();
            Long lastIdGenerated = resultSet.getLong(1);
            return lastIdGenerated +1;

        }catch (SQLException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    private User getUserBy(Long id){
        String sql = "select * from users where id =?";
        try(Connection connection =connect()){
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Long userId = resultSet.getLong(1);
            Long walletId = resultSet.getLong(2);
            User user = new User();
            user.setId(userId);
            user.setWalletId(walletId);

            return user;
        }catch (SQLException exception){
            return null;
//            throw new RuntimeException("failed to connect to database");
        }
    }

    public User updateUser(Long userId, Long walletId){
        try(Connection connection = connect()){
            String sql = "UPDATE users SET wallet_Id = ? WHERE id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,walletId);
            statement.setLong(2,userId);
            statement.executeUpdate();
            return getUserBy(userId);

        }catch (SQLException exception){
            System.err.println(exception.getMessage());
            throw new UserUpdateFailedException(exception.getMessage());
        }
    }

    public Optional<User> findById(Long id){
        User user = getUserBy(id);
        if(user != null) return Optional.of(user);
        return Optional.empty();
    }

    public void deleteById(Long id){
        try(Connection connection = connect()){
            String sql = "DELETE FROM users WHERE id =?";
            var statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        }catch (SQLException exception){
            throw new RuntimeException("Failed to delete user");
        }
    }


    public List<User> findAll() {
        try(Connection connection = connect()){
            String sql = "SELECT * FROM users";
           PreparedStatement statement = connection.prepareStatement(sql);
           ResultSet resultSet = statement.executeQuery();
        }catch (SQLException e){

        }
        return null;
    }
}