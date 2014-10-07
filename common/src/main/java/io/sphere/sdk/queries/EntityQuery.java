package io.sphere.sdk.queries;

import java.util.Optional;

import java.util.List;

/**
 * Queries for entities with getters and copy functions for where, sort, limit and offset
 * @param <I> Interface of the entity, i.e., Category interface
 */
public interface EntityQuery<I> extends Query<I> {
    /**
     *
     * @return the predicate used to perform the query
     */
    Optional<Predicate<I>> predicate();

    /**
     * @return the used sort expressions for this query
     */
    List<Sort<I>> sort();

    Optional<Long> limit();

    Optional<Long> offset();

    String endpoint();

    List<ExpansionPath<I>> expansionPaths();

    List<QueryParameter> additionalQueryParameters();
}
