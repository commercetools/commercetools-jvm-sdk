package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereClient;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Provides facilities to fetch all elements matching a query predicate.
 *
 * @since 1.0.0-RC1
 */
public final class QueryExecutionUtils {
    public static final int DEFAULT_PAGE_SIZE = 500;

    private QueryExecutionUtils() {
    }

    /**
     * Queries all elements matching a query by using an offset based pagination with page size {@value DEFAULT_PAGE_SIZE}.
     *
     * @param client commercetools client
     * @param query  query containing predicates and expansion paths
     * @param <T>    type of one query result element
     * @param <C>    type of the query
     * @return elements
     */
    public static <T, C extends QueryDsl<T, C>> CompletionStage<List<T>> queryAll(final SphereClient client, final QueryDsl<T, C> query) {
        return queryAll(client, query, DEFAULT_PAGE_SIZE);
    }

    /**
     * Queries all elements matching a query by using an offset based pagination.
     *
     * @param client   commercetools client
     * @param query    query containing predicates and expansion paths
     * @param pageSize size of one batch to fetch
     * @param <T>      type of one query result element
     * @param <C>      type of the query
     * @return elements
     */
    public static <T, C extends QueryDsl<T, C>> CompletionStage<List<T>> queryAll(final SphereClient client, final QueryDsl<T, C> query, final int pageSize) {
        return QueryAllImpl.of(query, pageSize).run(client);
    }

    /**
     * Queries all elements matching a query by using an offset based pagination with page size 500.
     * The method takes a callback {@link Function} that returns a result of type {@code <S>} that is returned on every
     * page of elements queried. Eventually, the method returns a {@link CompletionStage} that contains a list of all
     * the results of the callbacks returned from every page.
     *
     * @param client        commercetools client
     * @param query         query containing predicates and expansion paths
     * @param resultsMapper callback function that is called on every page queried.
     * @param <T>           type of one query result element
     * @param <C>           type of the query
     * @param <S>           type of the returned result of the callback function on every page.
     * @return elements
     */
    @Nonnull
    public static <T, C extends QueryDsl<T, C>, S> CompletionStage<List<S>>
    queryAll(@Nonnull final SphereClient client, @Nonnull final QueryDsl<T, C> query, @Nonnull final Function<T, S> resultsMapper) {
        return queryAll(client, query, resultsMapper, DEFAULT_PAGE_SIZE);
    }

    /**
     * Queries all elements matching a query by using an offset based pagination. The method takes a callback
     * {@link Function} that returns a result of type {@code <S>} that is returned on every page of elements queried.
     * Eventually, the method returns a {@link CompletionStage} that contains a list of all the results of the
     * callbacks returned from every page.
     *
     * @param client        commercetools client
     * @param query         query containing predicates and expansion paths
     * @param resultsMapper callback function that is called on every page queried.
     * @param <T>           type of one query result element
     * @param <C>           type of the query
     * @param <S>           type of the returned result of the callback function on every page.
     * @param pageSize      the page size.
     * @return elements
     */
    @Nonnull
    public static <T, C extends QueryDsl<T, C>, S> CompletionStage<List<S>>
    queryAll(@Nonnull final SphereClient client, @Nonnull final QueryDsl<T, C> query, @Nonnull final Function<T, S> resultsMapper, final int pageSize) {
        return QueryAllImpl.of(query, pageSize).run(client, resultsMapper);
    }

    /**
     * Queries all elements matching a query by using an offset based pagination with page size 500. The method takes a
     * consumer {@link Consumer} that is applied on on every page of elements queried.
     *
     * @param client          commercetools client
     * @param query           query containing predicates and expansion paths
     * @param resultsConsumer that is applied on every page queried.
     * @param <T>             type of one query result element
     * @param <C>             type of the query
     * @return elements
     */
    @Nonnull
    public static <T, C extends QueryDsl<T, C>> CompletionStage<Void>
    queryAll(@Nonnull final SphereClient client, @Nonnull final QueryDsl<T, C> query, @Nonnull final Consumer<T> resultsConsumer) {
        return queryAll(client, query, resultsConsumer, DEFAULT_PAGE_SIZE);
    }

    /**
     * Queries all elements matching a query by using an offset based pagination. The method takes a consumer
     * {@link Consumer} that is applied on on every page of elements queried.
     *
     * @param client          commercetools client
     * @param query           query containing predicates and expansion paths
     * @param resultsConsumer that is applied on every page queried.
     * @param <T>             type of one query result element
     * @param <C>             type of the query
     * @param pageSize        the page size.
     * @return elements
     */
    @Nonnull
    public static <T, C extends QueryDsl<T, C>> CompletionStage<Void>
    queryAll(@Nonnull final SphereClient client, @Nonnull final QueryDsl<T, C> query, @Nonnull final Consumer<T> resultsConsumer, final int pageSize) {
        return QueryAllImpl.of(query, pageSize).run(client, resultsConsumer);
    }
}
