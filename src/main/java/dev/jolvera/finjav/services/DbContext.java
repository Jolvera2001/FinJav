package dev.jolvera.finjav.services;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jdbi.v3.core.HandleCallback;
import org.jdbi.v3.core.Jdbi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Singleton
public class DbContext {
    private final Jdbi jdbi;
    private final Executor virtualExectutor;

    @Inject
    public DbContext(Jdbi jdbi) {
        this.jdbi = jdbi;
        this.virtualExectutor = Executors.newVirtualThreadPerTaskExecutor();

        InitSchema();
    }

    public <T> T withHandle(HandleCallback<T, RuntimeException> callback) {
                return jdbi.withHandle(callback);
    }

    private void InitSchema() {
        this.jdbi.withHandle(handle -> {
           handle.execute("""
            CREATE TABLE IF NOT EXISTS users (
                id TEXT NOT NULL PRIMARY KEY,
                date_created DATETIME NOT NULL,
                date_Modified DATETIME NOT NULL,
                name TEXT NOT NULL,
                email TEXT NOT NULL,
                password TEXT NOT NULL
                )
            """);

           handle.execute("""
            CREATE TABLE IF NOT EXISTS recurrences (
                id TEXT NOT NULL PRIMARY KEY,
                user_id TEXT NOT NULL,
                date_created DATETIME NOT NULL,
                date_Modified DATETIME NOT NULL,
                name TEXT NOT NULL,
                is_income BOOLEAN NOT NULL,
                recurring_date DATETIME NOT NULL,
                FOREIGN KEY (user_id) REFERENCES users(id)
                )
            """);

            return null;
        });
    }
}
