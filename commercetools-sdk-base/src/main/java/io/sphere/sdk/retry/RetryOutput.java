package io.sphere.sdk.retry;

import java.util.concurrent.CompletionStage;

public interface RetryOutput<P, R> {
    CompletionStage<R> getStage();

    P getParameter();
}
