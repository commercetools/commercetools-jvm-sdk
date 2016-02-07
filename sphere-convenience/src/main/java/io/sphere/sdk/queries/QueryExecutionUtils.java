package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereClient;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Provides facilities to fetch all elements matching a query predicate.
 * @since 1.0.0-RC1
 */
public final class QueryExecutionUtils {
    public static final int DEFAULT_PAGE_SIZE = 500;

    private QueryExecutionUtils() {
    }

    /**
     * Queries all elements matching a query by using an offset based pagination with page size {@value DEFAULT_PAGE_SIZE}.
     * @param client commercetools client
     * @param query query containing predicates and expansion paths
     * @param <T> type of one query result element
     * @param <C> type of the query
     * @return elements
     */
    public static <T, C extends QueryDsl<T, C>> CompletionStage<List<T>> queryAll(final SphereClient client, final QueryDsl<T, C> query) {
        return queryAll(client, query, DEFAULT_PAGE_SIZE);
    }

    /**
     * Queries all elements matching a query by using an offset based pagination.
     * @param client commercetools client
     * @param query query containing predicates and expansion paths
     * @param pageSize size of one batch to fetch
     * @param <T> type of one query result element
     * @param <C> type of the query
     * @return elements
     */
    public static <T, C extends QueryDsl<T, C>> CompletionStage<List<T>> queryAll(final SphereClient client, final QueryDsl<T, C> query, final int pageSize) {
        return QueryAllImpl.of(query, pageSize).run(client);
    }
}
