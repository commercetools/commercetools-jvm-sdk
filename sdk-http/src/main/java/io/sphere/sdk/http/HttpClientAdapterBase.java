package io.sphere.sdk.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ForkJoinPool;

/**
 * Base class for implementing {@link HttpClient}s.
 *
 */
public abstract class HttpClientAdapterBase extends Base implements HttpClient {
    protected static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private final ForkJoinPool threadPool = new ForkJoinPool();

    @Override
    public final void close() {
        try {
            threadPool.shutdown();
            closeDelegate();
        } catch (final Throwable e) {
            throw new HttpException(e);
        }
    }

    @Override
    public final CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
        try {
            if (logger.isTraceEnabled()) {
                logger.debug("executing " + httpRequest);
            } else if (logger.isDebugEnabled()) {
                logger.debug("{} {}", httpRequest.getHttpMethod(), httpRequest.getUrl());
            }
            final CompletableFuture<HttpResponse> result = new CompletableFuture<>();
            executeDelegate(httpRequest)
                    .thenApply(httpResponse -> {
                        if (logger.isTraceEnabled()) {
                            logger.debug("response " + httpResponse);
                        }
                        return httpResponse;
                    }).whenComplete((nullableHttpResponse, nullableThrowable) -> {
                if (nullableThrowable != null) {
                    //nginx does not send status code so this http client explodes
                    final boolean maybeUriTooLongErrorFromNgingx = nullableThrowable.getMessage().contains("invalid version format: <HTML>");
                    final String message = maybeUriTooLongErrorFromNgingx
                            ? "There is a problem, maybe the request URI was too long due to an inefficient query."
                            : "The underlying HTTP client detected a problem.";
                    Throwable throwable = nullableThrowable instanceof CompletionException ? nullableThrowable.getCause() : nullableThrowable;
                    throwable = throwable instanceof HttpException
                            ? throwable
                            : new HttpException(message, throwable);
                    result.completeExceptionally(throwable);
                } else {
                    result.complete(nullableHttpResponse);
                }
            });

            return result;
        } catch (final Throwable e) {//exceptions should be wrapped into the CompletionStage
            final CompletableFuture<HttpResponse> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(e);
            return failedFuture;
        }
    }

    protected abstract CompletionStage<HttpResponse> executeDelegate(final HttpRequest httpRequest) throws Throwable;

    protected abstract void closeDelegate() throws Throwable;

    protected final ForkJoinPool threadPool() {
        return threadPool;
    }

    @Nullable
    @Override
    public abstract String getUserAgent();
}
