package com.homework.nix.jdbc.util;

import com.homework.nix.jdbc.JDBCMain;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class GetProperties {

    public static Properties loadProperties() {

        Properties props = new Properties();

        try(InputStream input = JDBCMain.class.getResourceAsStream("/JdbcBox.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return props;
    }
}
