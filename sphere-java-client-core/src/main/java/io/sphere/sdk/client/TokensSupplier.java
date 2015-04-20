package io.sphere.sdk.client;

import java.io.Closeable;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

interface TokensSupplier extends Closeable, Supplier<CompletionStage<Tokens>> {
    @Override
    CompletionStage<Tokens> get();

    @Override
    void close();
}
