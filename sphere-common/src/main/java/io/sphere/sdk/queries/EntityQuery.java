package io.sphere.sdk.queries;

import java.util.Optional;

import java.util.List;

/**
 * Queries for entities with getters and copy functions for where, sort, limit and offset
 * @param <T> Interface of the entity, i.e., Category interface
 */
public interface EntityQuery<T> extends Query<T> {
    /**
     *
     * @return the predicate used to perform the query
     */
    Optional<Predicate<T>> predicate();

    /**
     * @return the used sort expressions for this query
     */
    List<Sort<T>> sort();

    Optional<Long> limit();

    Optional<Long> offset();

    String endpoint();

    List<ExpansionPath<T>> expansionPaths();

    List<QueryParameter> additionalQueryParameters();
}
