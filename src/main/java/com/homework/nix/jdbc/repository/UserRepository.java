package com.homework.nix.jdbc.repository;

import com.homework.nix.jdbc.entity.User;

import java.sql.*;


public class UserRepository {


    public User findUserById(Long userId, Connection connection) {
        User user = new User();

            try (PreparedStatement getUser = connection.prepareStatement(
                    "SELECT * FROM users WHERE id = ?")) {

                getUser.setLong(1, userId);

                ResultSet resultSet = getUser.executeQuery();

                while (resultSet.next()) {

                    String email = resultSet.getString("email");
                    String username = resultSet.getString("username");

                    user.setEmail(email);
                    user.setUsername(username);

                    return user;
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }
}
