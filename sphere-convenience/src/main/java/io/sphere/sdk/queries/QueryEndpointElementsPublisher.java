package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.Base;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

final class QueryEndpointElementsPublisher<T, C extends QueryDsl<T, C>> extends Base implements Publisher<T> {
    private final QueryDsl<T, C> seedQuery;
    private final Function<T, String> idExtractor;
    private final SphereClient sphereClient;

    QueryEndpointElementsPublisher(final QueryDsl<T, C> seedQuery, final Function<T, String> idExtractor, final SphereClient sphereClient) {
        this.seedQuery = seedQuery;
        this.idExtractor = idExtractor;
        this.sphereClient = sphereClient;
    }

    @Override
    public void subscribe(final Subscriber<? super T> subscriber) {
        requireNonNull(subscriber);
        subscriber.onSubscribe(new SubscriptionImpl<>(seedQuery, idExtractor, sphereClient, subscriber));
    }
}
