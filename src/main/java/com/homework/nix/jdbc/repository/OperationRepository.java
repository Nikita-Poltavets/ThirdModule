package com.homework.nix.jdbc.repository;

import com.homework.nix.jdbc.entity.Income;
import com.homework.nix.jdbc.entity.Operation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationRepository {


    public List<Operation> findOperationBetweenDates(Long accountId, String from, String to, Connection connection){

        List<Operation> incomeList = new ArrayList<>();
        Operation operation;

        try (PreparedStatement getListOperations = connection.prepareStatement(
                "SELECT * FROM operations WHERE account_id = ? AND timestamp > ? AND timestamp < ?")) {

            getListOperations.setLong(1, accountId);
            getListOperations.setTimestamp(2, Timestamp.valueOf(from));
            getListOperations.setTimestamp(3, Timestamp.valueOf(to));


            ResultSet resultSet = getListOperations.executeQuery();

            while (resultSet.next()) {
                operation = new Income();

                Long amount = resultSet.getLong("amount");
                String description = resultSet.getString("description");
                Timestamp timestamp = resultSet.getTimestamp("timestamp");

                operation.setAmount(amount);
                operation.setAccountId(accountId);
                operation.setDescription(description);
                operation.setTimestamp(timestamp.toInstant());

                incomeList.add(operation);
            }

            return incomeList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }


}
