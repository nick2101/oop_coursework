package com.mandziak.n;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHandler {
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String DB_NAME = "course_oop";
    static final String DB_PAR = "?characterEncoding=UTF-8";
    static final String USER = "root";
    static final String PASS = "12345678";

    DBHandler()
    {
        connectToDB();
    }

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet;

    private int connectToDB()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            return 2;
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            connection = DriverManager.getConnection(DB_URL + DB_NAME + DB_PAR, USER, PASS);

            String users =
                    "CREATE TABLE IF NOT EXISTS course_oop.users (" +
                            "user_id INT AUTO_INCREMENT," +
                            "last_name  VARCHAR(16) NOT NULL," +
                            "first_name VARCHAR(16) NOT NULL," +
                            "middle_name VARCHAR(16)," +
                            "password VARCHAR(16) NOT NULL," +
                            "group_name VARCHAR(8) NOT NULL," +
                            "PRIMARY KEY(user_id) );" ;

            String lectures =
                    "CREATE TABLE IF NOT EXISTS course_oop.lectures(" +
                            "lecture_id INT AUTO_INCREMENT," +
                            "title  VARCHAR(32) NOT NULL UNIQUE," +
                            "text VARCHAR(4096) DEFAULT ''," +
                            "priority INT NOT NULL UNIQUE," +
                            "enable BOOLEAN DEFAULT(false)," +
                            "PRIMARY KEY(lecture_id) );" ;

            String questions =
                    "CREATE TABLE IF NOT EXISTS course_oop.questions (" +
                            "question_id INT AUTO_INCREMENT," +
                            "lecture_id INT NOT NULL REFERENCES course_oop.lectures," +
                            "text VARCHAR(256) NOT NULL," +
                            "answer1 VARCHAR(64) NOT NULL," +
                            "answer2 VARCHAR(64) NOT NULL," +
                            "answer3 VARCHAR(64)," +
                            "answer4 VARCHAR(64)," +
                            "PRIMARY KEY(question_id) );" ;
            String marks =
                    "CREATE TABLE IF NOT EXISTS course_oop.marks (" +
                            "mark_id INT AUTO_INCREMENT," +
                            "user_id INT NOT NULL REFERENCES course_oop.users," +
                            "lecture_id INT NOT NULL REFERENCES course_oop.lectures," +
                            "mark INT," +
                            "PRIMARY KEY(mark_id) );" ;

            statement = connection.createStatement();
            statement.executeUpdate(users);
            statement.executeUpdate(lectures);
            statement.executeUpdate(questions);
            statement.executeUpdate(marks);
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    public String getMaterial(String title)
    {
        String material = null;
        try {
            String select = "SELECT text FROM course_oop.lectures" +
                    "WHERE title = ?;";
            preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                material = resultSet.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return material;
    }
}