/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.todolist.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jzuniga
 */
public class ConnectionDB {
    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "t00r";
    private final String DB_NAME = "TODO_APP";
    private final String QUERY_PARAMS = "?serverTimezone=UTC";
    
    private Connection conn;

    public ConnectionDB() {
        this.conn = null;
    }
    
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        this.conn = DriverManager.getConnection(URL + DB_NAME + QUERY_PARAMS, DB_USER, DB_PASSWORD);
        return this.conn;
    }
    
    public void close() throws SQLException {
        this.conn.close();
    }
}
