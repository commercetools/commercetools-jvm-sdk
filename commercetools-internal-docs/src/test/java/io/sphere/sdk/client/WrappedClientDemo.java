package io.sphere.sdk.client;

import io.sphere.sdk.commands.Command;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;

import java.util.concurrent.CompletionStage;
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
    public <T> CompletionStage<T> execute(SphereRequest<T> sphereRequest) {
        final CompletionStage<T> result;
        final CompletionStage<T> intermediateResult = filtered(client.execute(sphereRequest));
        if (sphereRequest instanceof Query) {
            final Function<Throwable, T> provideEmptyResultOnException = exception -> (T) PagedQueryResult.empty();
            result = intermediateResult.exceptionally(provideEmptyResultOnException);
        } else if (sphereRequest instanceof Command) {
            final Function<Throwable, T> retry = exception -> (T) client.execute(sphereRequest);
            result = intermediateResult.exceptionally(retry);
        } else {
            result = intermediateResult;
        }
        return result;
    }

    //this method will be called for every request
    private <T> CompletionStage<T> filtered(final CompletionStage<T> future) {
        future.whenComplete(Email::writeEmailToDevelopers);
        future.whenComplete(metricComponent::incrementFailureRequests);
        future.whenComplete(metricComponent::incrementSuccessfulRequests);
        return future;
    }

    @Override
    public void close() {
        client.close();
    }

    @Override
    public SphereApiConfig getConfig() {
        return client.getConfig();
    }
}
