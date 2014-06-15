package io.sphere.sdk.client;

import com.google.common.base.Optional;
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

    @Override
    public <I, R> F.Promise<Optional<I>> execute(Fetch<I, R> fetch) {
        return filtered(client.execute(fetch));
    }

    @Override
    public <I, R> F.Promise<PagedQueryResult<I>> execute(Query<I, R> query) {
        //returns empty result on error
        return filtered(client.execute(query)).recover(exception -> PagedQueryResult.<I>empty());
    }

    @Override
    public <T, V> F.Promise<T> execute(Command<T, V> command) {
        //retries command, this is a demo, this does not make sense every time!
        return filtered(client.execute(command)).recoverWith(exception -> client.execute(command));
    }

    //this method will be called for every request
    private <T> F.Promise<T> filtered(final F.Promise<T> promise) {
        promise.onFailure(exception -> Email.writeEmailToDevelopers(exception));
        promise.onFailure(exception -> metricComponent.incrementFailureRequests(exception));
        promise.onRedeem(result -> metricComponent.incrementSuccessfulRequests());
        return promise;
    }

    @Override
    public void close() {
        client.close();
    }
}
