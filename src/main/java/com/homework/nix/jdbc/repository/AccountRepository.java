package com.homework.nix.jdbc.repository;

import com.homework.nix.jdbc.entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {

    public Account findAccountById(Long accountId, Connection connection){
        Account account = new Account();

        try (PreparedStatement getAccount = connection.prepareStatement(
                "SELECT * FROM accounts WHERE id = ?")) {

            getAccount.setLong(1, accountId);

            ResultSet resultSet = getAccount.executeQuery();

            while (resultSet.next()) {

                String balance = resultSet.getString("balance");
                long userId = resultSet.getLong("user_id");

                account.setBalance(balance);
                account.setUserId(userId);

                return account;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
