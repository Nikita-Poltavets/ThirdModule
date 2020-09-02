package com.homework.nix;

import com.homework.nix.hibernate.entity.Account;
import com.homework.nix.hibernate.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MockDataLoader {

    public static void main(String[] args) {

        User user = new User();
        user.setUsername("Proselyte");
        user.setEmail("proselyte@gmail.com");


        Account account = new Account();
        account.setBalance(1000);
        account.setUser(user);


        Configuration cfg = new Configuration().configure();

        cfg.setProperty("hibernate.connection.username", "postgres");
        cfg.setProperty("hibernate.connection.password", "postgres123");



        try (SessionFactory sessionFactory = cfg.buildSessionFactory();
             Session session = sessionFactory.openSession()
        ) {
            try {
                session.beginTransaction();

                session.save(user);
                session.save(account);

                session.getTransaction().commit();
            } catch (Exception e) {

                session.getTransaction().rollback();
            }
        }
    }
}
