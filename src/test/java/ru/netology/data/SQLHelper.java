package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {

    private static final QueryRunner runner = new QueryRunner();
    private static final String DB_URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/app");
    private static final String DB_USER = System.getProperty("db.user", "app");
    private static final String DB_PASS = System.getProperty("db.password", "pass");

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    @SneakyThrows
    public static String getVerificationCode(String login) {
        var sql = "SELECT code FROM auth_codes " +
                "JOIN users ON auth_codes.user_id = users.id " +
                "WHERE users.login = ? " +
                "ORDER BY auth_codes.created DESC LIMIT 1";
        try (var conn = getConnection()) {
            return runner.query(conn, sql, new ScalarHandler<>(), login);
        }
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        try (var conn = getConnection()) {
            runner.execute(conn, "DELETE FROM auth_codes");
        }
    }

    @SneakyThrows
    public static void resetUsersStatus() {
        try (var conn = getConnection()) {
            runner.execute(conn, "UPDATE users SET status = 'active'");
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (var conn = getConnection()) {
            runner.execute(conn, "DELETE FROM auth_codes");
            runner.execute(conn, "UPDATE users SET status = 'active'");
        }
    }
}