package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.Identifiable;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public final class ExperimentalReactiveStreamUtils {
    private ExperimentalReactiveStreamUtils() {
    }

    public static <T extends Identifiable<T>, C extends QueryDsl<T, C>> Publisher<T> publisherOf(final QueryDsl<T, C> seedQuery, final SphereClient sphereClient) {
        return publisherOf(seedQuery, identifiable -> identifiable.getId(),sphereClient);
    }

    public static <T, C extends QueryDsl<T, C>> Publisher<T> publisherOf(final QueryDsl<T, C> seedQuery, final Function<T, String> idExtractor, final SphereClient sphereClient) {
        return new QueryEndpointElementsPublisher<>(seedQuery, idExtractor, sphereClient);
    }

    public static <T> CompletionStage<List<T>> collectAll(final Publisher<T> publisher) {
        final CollectAllSubscriber<T> collectAllSubscriber = new CollectAllSubscriber<>();
        publisher.subscribe(collectAllSubscriber);
        return collectAllSubscriber.getCompletionStage();
    }

}

