package io.sphere.sdk.queries;

import io.sphere.sdk.http.HttpQueryParameter;

import java.util.Optional;

import java.util.List;

/**
 * Queries for entities with getters and copy functions for where, sort, limit and offset
 * @param <T> Interface of the entity, i.e., Category interface
 */
public interface EntityQuery<T> extends Query<T>, ReferenceExpandeable<T> {
    /**
     *
     * @return the predicate used to perform the query
     */
    Optional<QueryPredicate<T>> predicate();

    /**
     * @return the used sort expressions for this query
     */
    List<QuerySort<T>> sort();

    Optional<Long> limit();

    Optional<Long> offset();

    String endpoint();

    @Override
    List<ExpansionPath<T>> expansionPaths();

    List<HttpQueryParameter> additionalQueryParameters();
}
