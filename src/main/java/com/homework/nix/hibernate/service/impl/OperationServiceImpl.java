package com.homework.nix.hibernate.service.impl;

import com.homework.nix.hibernate.entity.Account;
import com.homework.nix.hibernate.entity.Operation;
import com.homework.nix.hibernate.entity.User;
import com.homework.nix.hibernate.service.OperationService;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class OperationServiceImpl implements OperationService {

    private static Logger log = LoggerFactory.getLogger("userOperations");

    private Session session;

    public OperationServiceImpl(Session session){
        this.session = session;
    }


    private void makeOperation(Account account, long amount){

            if(amount > 0){
                incomeOperation(account, amount);
                log.info("User " + account.getUser().getUsername() + " deposited on account id " + account.getId().toString() + " with amount " + amount);
            }
            else if(amount < 0 & amount < account.getBalance() & amount != 0){
                expenseOperation(account, amount);
                log.info("User " + account.getUser().getUsername() + " withdrew the amount of " + Math.abs(amount) + " from account id " + account.getId().toString());
            }else {
                log.info("Denied operation. Withdrawal amount exceeds balance.");
            }
    }

    @Override
    public void getDataFromConsole(Long userId) throws Exception {
        Scanner in = new Scanner(System.in);

        User user = getUserById(session, userId);


        while (true) {

            System.out.println("1 - Exit\n2 - Make operation");

            int oneOrTwo = in.nextInt();

            if(oneOrTwo == 1){
                break;
            }

            System.out.println("Please enter account id");

            long accountId = in.nextLong();

            List<Account> accountList = user.getAccounts();

            if (getAccountById(accountId, accountList) == null) {
                throw new Exception("Account with " + accountId + " id not found!");
            }

            Account account = getAccountById(accountId, accountList);

            System.out.println("Please enter the amount");

            long amount = in.nextLong();

            makeOperation(account, amount);
        }
    }


    private void incomeOperation(Account account, long amount){
        try {
            session.getTransaction().begin();
            Operation operation = new Operation();
            operation.setAccount(account);
            operation.setAmount(amount);
            operation.setTimestamp(Instant.now(Clock.systemUTC()));

            account.setBalance(account.getBalance() + operation.getAmount());

            System.out.println(account.getBalance());

            session.persist(operation);

            session.getTransaction().commit();
        }catch (Exception e) {
            session.getTransaction().rollback();
        }

    }


    private void expenseOperation(Account account, long amount){
        try {
            session.getTransaction().begin();
            Operation operation = new Operation();
            operation.setAccount(account);
            operation.setAmount(amount);
            operation.setTimestamp(Instant.now(Clock.systemUTC()));

            account.setBalance(account.getBalance() + operation.getAmount());

            session.saveOrUpdate(operation);

            session.getTransaction().commit();
        }catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    User getUserById(Session session, Long userId){
       return session.get(User.class, userId);
    }

    Account getAccountById(Long accountId, List<Account> accountList){

        return accountList
                .stream()
                .filter(a -> a.getId().equals(accountId))
                .findFirst()
                .orElse(null);
    }
}






















