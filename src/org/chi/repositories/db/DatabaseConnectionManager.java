package org.chi.repositories.db;

import org.chi.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectionManager {


    private Connection connection;
    private DatabaseConnectionManager(){}
    private  static final class SINGLETON{
        private static final DatabaseConnectionManager databaseConnectionManager =
                new DatabaseConnectionManager();

//        public static DatabaseConnectionManager getInstance() {
//            return SINGLETON.databaseConnectionManager;
//        }
    }

    public static DatabaseConnectionManager getInstance(){
        return SINGLETON.databaseConnectionManager;
    }


    public Long generateId(String tableName){
        try (Connection connection = DatabaseConnectionManager.getInstance().getConnection()){
            String sql = "SELECT max(id) FROM tableName";
            var statememt = connection.prepareStatement(sql);
            ResultSet resultSet = statememt.executeQuery();
            resultSet.next();
            Long lastIdGenerated = resultSet.getLong(1);
            return lastIdGenerated +1;

        }catch (SQLException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

        public  Connection getConnection(){
            if (connection != null)return connection;
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
            String sql = "SELECT max(id) FROM ";
            var statememt = connection.prepareStatement(sql);
            ResultSet resultSet = statememt.executeQuery();
            resultSet.next();
            Long lastIdGenerated = resultSet.getLong(1);
            return lastIdGenerated +1;

        }catch (SQLException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
}
