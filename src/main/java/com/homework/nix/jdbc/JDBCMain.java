package com.homework.nix.jdbc;

import com.homework.nix.jdbc.repository.AccountRepository;
import com.homework.nix.jdbc.repository.OperationRepository;
import com.homework.nix.jdbc.entity.Account;
import com.homework.nix.jdbc.service.impl.AccountStatementMakerImpl;
import com.homework.nix.jdbc.util.GetProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Scanner;

public class JDBCMain {
    public static void main(String[] args) {
        long userId = Long.parseLong(args[0]);

        String usernameDB = args[1];
        String passwordDB = args[2];
        String from = "2020-08-31 16:28:09";
        String to = "2020-09-01 16:26:17";

        Properties properties = GetProperties.loadProperties();
        String url = properties.getProperty("url");
        properties.setProperty("user", usernameDB);
        properties.setProperty("password", passwordDB);

        try (Connection connection = DriverManager.getConnection(url, properties)) {
            Scanner in = new Scanner(System.in);
            AccountRepository accountRepository = new AccountRepository();
            OperationRepository operationRepository = new OperationRepository();


            System.out.println("Please enter account id");

            Long accountId = in.nextLong();

            Account account = accountRepository.findAccountById(accountId, connection);
            if(account.getUserId() != userId){
                throw new Exception("Account with " + accountId + " id not found!");
            }

            AccountStatementMakerImpl accountStatementMaker = new AccountStatementMakerImpl(accountRepository, connection, operationRepository);


            accountStatementMaker.makeAccountStatement(accountId, from, to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
