package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.Identifiable;
import org.reactivestreams.Publisher;

public class ExperimentalForAll<T extends Identifiable<T>, C extends QueryDsl<T, C>> {
    private final QueryDsl<T, C> seedQuery;

    private ExperimentalForAll(final QueryDsl<T, C> seedQuery) {
        this.seedQuery = seedQuery;
    }

    public static <T extends Identifiable<T>, C extends QueryDsl<T, C>> ExperimentalForAll<T, C> of(final QueryDsl<T, C> seedQuery) {
        return new ExperimentalForAll<>(seedQuery);
    }

    public Publisher<T> publisher(final SphereClient sphereClient) {
        return new ForAllPublisher<>(seedQuery, sphereClient);
    }
}

