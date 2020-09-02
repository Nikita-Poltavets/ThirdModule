package com.homework.nix.jdbc.service.impl;

import au.com.bytecode.opencsv.CSVWriter;
import com.homework.nix.jdbc.repository.AccountRepository;
import com.homework.nix.jdbc.repository.OperationRepository;
import com.homework.nix.jdbc.entity.Operation;
import com.homework.nix.jdbc.service.AccountStatementMaker;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;


public class AccountStatementMakerImpl implements AccountStatementMaker {

    AccountRepository accountRepository;
    Connection connection;
    OperationRepository operationRepository;

    public AccountStatementMakerImpl(AccountRepository accountRepository, Connection connection, OperationRepository operationRepository){
        this.accountRepository = accountRepository;
        this.connection = connection;
        this.operationRepository = operationRepository;
    }

    @Override
    public void makeAccountStatement(Long accountId, String from, String to) throws Exception {
        if(accountRepository.findAccountById(accountId, connection) == null){
            throw new Exception("Account with " + accountId + " id not found!");
        }

        List<Operation> operationList = operationRepository.findOperationBetweenDates(accountId, from, to, connection);

        long saldo = getSaldo(accountId, from, to);
        long sumOfIncomesOperation = getGenralIncomeSum(accountId, from, to);
        String csv = "accountStatement.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csv))) {

            String[] header = "Account_Id,Amount,Description,Timestamp,SumOfIncome,Saldo".split(",");

            writer.writeNext(header);

            for (int i = 0; i < operationList.size(); i++) {
                String[] data  = (accountId.toString() + "," + operationList.get(i).getAmount() + "," + operationList.get(i).getDescription() + "," + operationList.get(i).getTimestamp() + "," + sumOfIncomesOperation + "," + saldo).split(",");
                writer.writeNext(data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Long getSaldo(Long accountId, String from, String to) {
        List<Operation> operationList = operationRepository.findOperationBetweenDates(accountId, from, to, connection);

        return operationList
                .stream()
                .map(Operation::getAmount)
                .mapToLong(Long::longValue)
                .sum();
    }

    @Override
    public Long getGenralIncomeSum(Long accountId, String from, String to) {
        List<Operation> operationList = operationRepository.findOperationBetweenDates(accountId, from, to, connection);

        return operationList
                .stream()
                .filter(operation -> operation.getAmount() > 0)
                .mapToLong(Operation::getAmount)
                .boxed()
                .mapToLong(Long::longValue)
                .sum();
    }

}
