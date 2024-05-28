package org.chi.repositories;

import org.chi.models.Wallet;
import org.chi.repositories.db.DatabaseConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@SuppressWarnings(value = {"all"})
public class WalletRepository {

    public Wallet save(Wallet wallet) {
        try (Connection connection = DatabaseConnectionManager.getInstance().connect()){
            var databaseManager = DatabaseConnectionManager.getInstance();
            String sql = "INSERT into wallets (id, balance) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            Long id = databaseManager.generateId("wallets");
            wallet.setId(id);
            statement.setLong(1,wallet.getId());
            statement.setBigDecimal(2,wallet.getBalance());
            statement.executeUpdate();
            return wallet;
        }catch (SQLException e){
            throw  new RuntimeException(e.getMessage());
        }
        return null;
    }

    public Optional<Wallet> findById(Long id) {
        try(Connection connection  = DatabaseConnectionManager.getInstance().getConnection()){

            String sql = "SELECT id, balance from wallets where id =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Long walletId =resultSet.getLong("id");
            BigDecimal balance = resultSet.getBigDecimal("balance");
            Wallet wallet = new Wallet();
            wallet.setId(walletId);
            wallet.setBalance(balance);
            return Optional.of(wallet);
        }catch (SQLException e){
            System.out.println("error: " + e.getMessage());
            return  Optional.empty();

        }
    }
}
