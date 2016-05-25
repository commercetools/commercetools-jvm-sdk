package io.sphere.sdk.retry;

import java.util.concurrent.CompletionStage;

public interface Service extends AutoCloseable {
    CompletionStage<Integer> apply(final String s);

    //for tests
    boolean isClosed();
}
