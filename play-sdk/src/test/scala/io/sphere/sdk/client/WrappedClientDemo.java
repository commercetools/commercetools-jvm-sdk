package io.sphere.sdk.client;

import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import play.libs.F;

/**
 * This is just a demo how powerful the decorator pattern for the client can be.
 * Don't use this example in production as it is.
 */
public class WrappedClientDemo implements PlayJavaClient {

    private final PlayJavaClient client;
    private final MetricComponent metricComponent;

    public WrappedClientDemo(PlayJavaClient client, MetricComponent metricComponent) {
        this.client = client;
        this.metricComponent = metricComponent;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> F.Promise<T> execute(ClientRequest<T> clientRequest) {
        final F.Promise<T> result;
        final F.Promise<T> intermediateResult = filtered(client.execute(clientRequest));
        if (clientRequest instanceof Query) {
            final F.Function<Throwable, T> defaultEmpty = exception -> (T) PagedQueryResult.empty();
            result = intermediateResult.recover(defaultEmpty);
        } else if (clientRequest instanceof Command) {
            final F.Function<Throwable, T> retry = exception -> (T) client.execute(clientRequest);
            result = intermediateResult.recover(retry);
        } else {
            result = intermediateResult;
        }
        return result;
    }

    //this method will be called for every request
    private <T> F.Promise<T> filtered(final F.Promise<T> promise) {
        promise.onFailure(Email::writeEmailToDevelopers);
        promise.onFailure(metricComponent::incrementFailureRequests);
        promise.onRedeem(result -> metricComponent.incrementSuccessfulRequests());
        return promise;
    }

    @Override
    public void close() {
        client.close();
    }
}
