package io.sphere.sdk.queries;

import java.util.Optional;

import java.util.List;

/**
 * Queries for entities with getters and copy functions for where, sort, limit and offset
 * @param <I> Interface of the entity, i.e., Category interface
 * @param <M> (query) Model
 */
public interface EntityQuery<I, M> extends Query<I> {
    /**
     *
     * @return the predicate used to perform the query
     */
    Optional<Predicate<M>> predicate();

    /**
     * @return the used sort expressions for this query
     */
    List<Sort> sort();

    Optional<Long> limit();

    Optional<Long> offset();

    String endpoint();

    List<ExpansionPath<I>> expansionPaths();
}
