package io.sphere.sdk.client;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface TokensSupplier extends Closeable, Supplier<CompletableFuture<Tokens>> {
    @Override
    CompletableFuture<Tokens> get();

    @Override
    void close();
}
