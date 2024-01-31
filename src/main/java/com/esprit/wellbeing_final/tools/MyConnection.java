package com.esprit.wellbeing_final.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    private String url = "jdbc:mysql://localhost:3306/wellbeingvf";
    private String login = "root";
    private String pwd = "";
    public static MyConnection instance;
    Connection cnx;

    public MyConnection() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("connection established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static MyConnection getInstance() {
        if (instance == null)
            instance = new MyConnection();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }


}
