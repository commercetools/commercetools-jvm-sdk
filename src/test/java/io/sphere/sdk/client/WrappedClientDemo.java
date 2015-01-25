package io.sphere.sdk.client;

import io.sphere.sdk.commands.Command;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * This is just a demo how powerful the decorator pattern for the client can be.
 * Don't use this example in production as it is.
 */
public class WrappedClientDemo implements SphereClient {

    private final SphereClient client;
    private final MetricComponent metricComponent;

    public WrappedClientDemo(final SphereClient client, final MetricComponent metricComponent) {
        this.client = client;
        this.metricComponent = metricComponent;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> CompletableFuture<T> execute(SphereRequest<T> sphereRequest) {
        final CompletableFuture<T> result;
        final CompletableFuture<T> intermediateResult = filtered(client.execute(clientRequest));
        if (clientRequest instanceof Query) {
            final Function<Throwable, T> provideEmptyResultOnException = exception -> (T) PagedQueryResult.empty();
            result = intermediateResult.exceptionally(provideEmptyResultOnException);
        } else if (clientRequest instanceof Command) {
            final Function<Throwable, T> retry = exception -> (T) client.execute(clientRequest);
            result = intermediateResult.exceptionally(retry);
        } else {
            result = intermediateResult;
        }
        return result;
    }

    //this method will be called for every request
    private <T> CompletableFuture<T> filtered(final CompletableFuture<T> promise) {
        promise.whenComplete(Email::writeEmailToDevelopers);
        promise.whenComplete(metricComponent::incrementFailureRequests);
        promise.whenComplete(metricComponent::incrementSuccessfulRequests);
        return promise;
    }

    @Override
    public void close() {
        client.close();
    }
}
