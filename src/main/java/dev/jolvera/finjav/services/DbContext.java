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
    }

    // async handle
    public <T> CompletableFuture<T> withHandleAsync(HandleCallback<T, RuntimeException> callback) {
        return CompletableFuture.supplyAsync(() ->
                jdbi.withHandle(callback), virtualExectutor);
    }
}
