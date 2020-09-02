package com.homework.nix.hibernate;

import com.homework.nix.hibernate.service.impl.OperationServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateMain {

    public static void main(String[] args) {
        Long userId = Long.parseLong(args[0]);

        String usernameDB = args[1];
        String passwordDB = args[2];

        Configuration configuration = new Configuration();
        configuration.configure();

        configuration.setProperty("hibernate.connection.username", usernameDB);
        configuration.setProperty("hibernate.connection.password", passwordDB);


        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

             OperationServiceImpl operationService = new OperationServiceImpl(session);

             operationService.getDataFromConsole(userId);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
