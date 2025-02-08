package ru.netology.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    static String url = getUrl();
    static String user = "api";
    static String password = "secret";
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    public static String getUrl() {
        return System.getProperty("db.url");
    }

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "api", "secret");
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (var connection = getConn()) {
            QUERY_RUNNER.execute(connection, "DELETE FROM credit_request_entity");
            QUERY_RUNNER.execute(connection, "DELETE FROM order_entity");
            QUERY_RUNNER.execute(connection, "DELETE FROM payment_entity");
        }
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        String statusSQL = "SELECT status FROM payment_entity";
        return getStatus(statusSQL);
    }

    @SneakyThrows
    private static String getStatus(String query) {
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)) {
            String status = runner.query(conn, query, new ScalarHandler<String>());
            return status;
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving payment status", e);
        }
    }
}