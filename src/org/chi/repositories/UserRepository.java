package org.chi.repositories;

import org.chi.exceptions.UserUpdateFailedException;
import org.chi.models.User;
import org.chi.repositories.db.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.chrono.JapaneseEra.values;

@SuppressWarnings(value = {"all"})
public class UserRepository {




    public User saveUser(User user) {
//        String getIdSqlStatement = "select count(*) from users";
        String sql = "insert into users (id, wallet_id)values(?,?)";
        DatabaseConnectionManager databaseConnectionManager = DatabaseConnectionManager.getInstance();

        try(Connection connection = databaseConnectionManager.getConnection()){
            var preparedStatement = connection.prepareStatement(sql);
            Long id = databaseConnectionManager.generateId("users");
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
           return extractUsersFrom(resultSet);
        }catch (SQLException e){
            return null;
        }

    }

    private List<User> extractUsersFrom(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while(resultSet.next()){
            Long id = resultSet.getLong("id");
            Long walletId = resultSet.getLong("wallet_id");
            User user = new User();
            user.setId(id);
            user.setWalletId(walletId);
            users.add(user);
        }
        return users;
    }
}