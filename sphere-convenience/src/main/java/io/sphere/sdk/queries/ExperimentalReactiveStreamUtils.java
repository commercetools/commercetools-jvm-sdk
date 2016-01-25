package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.models.Identifiable;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * Utils to work with <a href="https://github.com/reactive-streams/reactive-streams-jvm" target="_blank">Reactive Streams</a>.
 *
 * <p>It is called for now experimental since we did not yet validated the implementation against the Technology Compatibility Kit for Reactive Streams.
 * It is not the case that we want to remove this API.</p>
 */
public final class ExperimentalReactiveStreamUtils {
    private ExperimentalReactiveStreamUtils() {
    }

    /**
     * Creates a {@link Publisher} for fetching all resources (except {@link io.sphere.sdk.products.ProductProjection} and {@link com.fasterxml.jackson.databind.JsonNode}, see {@link #publisherOf(QueryDsl, Function, SphereClient)}) matching a predicate. The order should not be of importance.
     * @param seedQuery the query containing a predicate which resources should be fetched, reference expansion can also be used but the sort expressions are ignored
     * @param sphereClient the client performing the requests
     * @param <T> the type of the resources to fetch (e.g. {@link io.sphere.sdk.categories.Category})
     * @param <C> the {@link QueryDsl} container
     * @return publisher
     * @see #publisherOf(QueryDsl, Function, SphereClient)
     */
    public static <T extends Identifiable<T>, C extends QueryDsl<T, C>> Publisher<T> publisherOf(final QueryDsl<T, C> seedQuery, final SphereClient sphereClient) {
        return publisherOf(seedQuery, identifiable -> identifiable.getId(),sphereClient);
    }

    /**
     * Creates a {@link Publisher} for fetching all resources matching a predicate including {@link io.sphere.sdk.products.ProductProjection} and {@link com.fasterxml.jackson.databind.JsonNode}. The order should not be of importance.
     * <p>Exmaple for {@link io.sphere.sdk.products.ProductProjection}:</p>
     * {@include.example io.sphere.sdk.queries.ExperimentalReactiveStreamUtilsDemoTest#productProjectionDemo()}
     *
     * <p>Example for {@link com.fasterxml.jackson.databind.JsonNode}:</p>
     *
     * {@include.example io.sphere.sdk.meta.CategoryDocumentationTest#fetchAllAsJson()}
     *
     * @param seedQuery the query containing a predicate which resources should be fetched, reference expansion can also be used but the sort expressions are ignored
     * @param sphereClient the client performing the requests
     * @param idExtractor function which takes as argument a resource and returns the ID of the resource
     * @param <T> the type of the resources to fetch (e.g. {@link io.sphere.sdk.products.ProductProjection})
     * @param <C> the {@link QueryDsl} container
     * @return publisher
     */
    public static <T, C extends QueryDsl<T, C>> Publisher<T> publisherOf(final QueryDsl<T, C> seedQuery, final Function<T, String> idExtractor, final SphereClient sphereClient) {
        return new QueryEndpointElementsPublisher<>(seedQuery, idExtractor, sphereClient);
    }

    public static <T> CompletionStage<List<T>> collectAll(final Publisher<T> publisher) {
        final CollectAllSubscriber<T> collectAllSubscriber = new CollectAllSubscriber<>();
        publisher.subscribe(collectAllSubscriber);
        return collectAllSubscriber.getCompletionStage();
    }

}

