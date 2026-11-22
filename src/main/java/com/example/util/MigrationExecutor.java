package com.example.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Logger;

public class MigrationExecutor {
    private static final Logger LOGGER =
            Logger.getLogger(MigrationExecutor.class.getName());
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public MigrationExecutor(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public void executeMigration(String scriptPath) throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(scriptPath)) {
            if (is == null) throw new IllegalArgumentException("Script introuvable: " + scriptPath);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                 Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
                 Statement stmt = conn.createStatement()) {
                StringBuilder sql = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    String trimmed = line.trim();
                    if (!trimmed.startsWith("--") && !trimmed.isEmpty()) {
                        sql.append(line).append('\n');
                    }
                }
                for (String statement : sql.toString().split(";")) {
                    if (!statement.trim().isEmpty()) stmt.execute(statement);
                }
                LOGGER.info("Migration appliquee: " + scriptPath);
            }
        }
    }
}
