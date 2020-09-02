package com.homework.nix.jdbc.service;

public interface AccountStatementMaker {

    void makeAccountStatement(Long accountId, String from, String to) throws Exception;

    Long getSaldo(Long accountId, String from, String to);

    Long getGenralIncomeSum(Long accountId, String from, String to);
}
