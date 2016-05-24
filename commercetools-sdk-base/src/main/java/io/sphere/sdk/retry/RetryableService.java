package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.util.concurrent.CompletionStage;

public interface RetryableService extends AutoCloseable{
    <R, P> CompletionStage<R> execute(@Nullable P parameterObject);

    @Override
    void close();

    void restart();
}
