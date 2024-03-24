package org.project.travel_agency.Utility;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB_Utils {

    static private Properties jdbcProps;

    private static final Logger logger = LoggerFactory.getLogger(DB_Utils.class);

    public DB_Utils(String confFile) {
        if (jdbcProps == null)
            jdbcProps = new Properties();
        try {
            if (confFile == null)
                throw new RuntimeException("Error: no configuration file");
            jdbcProps.load(new FileReader(confFile));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
    }

    private Connection instance = null;

    private Connection getNewConnection() {


        String url = jdbcProps.getProperty("jdbc.url");
        String user = jdbcProps.getProperty("jdbc.user");
        String pass = jdbcProps.getProperty("jdbc.pass");
        logger.info("trying to connect to database ... {}", url);
        logger.info("user: {}", user);
        logger.info("pass: {}", pass);
        Connection con = null;
        try {

            if (user != null && pass != null)
                con = DriverManager.getConnection(url, user, pass);
            else
                con = DriverManager.getConnection(url);
        } catch (SQLException e) {

            System.out.println("Error getting connection " + e);
        }
        return con;
    }

    public Connection getConnection() {
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();

        } catch (SQLException e) {

            System.out.println("Error DB " + e);
        }
        return instance;
    }
}
