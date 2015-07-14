package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Identifiable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import static java.util.Objects.requireNonNull;

final class QueryEndpointElementsPublisher<T extends Identifiable<T>, C extends QueryDsl<T, C>> extends Base implements Publisher<T> {
    private final QueryDsl<T, C> seedQuery;
    private final SphereClient sphereClient;

    QueryEndpointElementsPublisher(final QueryDsl<T, C> seedQuery, final SphereClient sphereClient) {
        this.seedQuery = seedQuery;
        this.sphereClient = sphereClient;
    }

    @Override
    public void subscribe(final Subscriber<? super T> subscriber) {
        requireNonNull(subscriber);
        subscriber.onSubscribe(new ForAllSubscription<>(seedQuery, sphereClient, subscriber));
    }
}
