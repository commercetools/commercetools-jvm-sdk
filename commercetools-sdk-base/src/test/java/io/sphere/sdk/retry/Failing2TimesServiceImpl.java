package io.sphere.sdk.retry;

import io.sphere.sdk.utils.CompletableFutureUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;

public class Failing2TimesServiceImpl extends ServiceImpl implements Service {
    public static final String ERROR_MESSAGE = "foo";
    final AtomicInteger counter = new AtomicInteger(0);
    @Override
    public CompletionStage<Integer> apply(final String s) {
        return counter.incrementAndGet() <= 2 ? CompletableFutureUtils.failed(new ServiceException(ERROR_MESSAGE)) : CompletableFuture.completedFuture(s.length());
    }
}
