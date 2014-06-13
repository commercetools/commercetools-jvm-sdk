package io.sphere.sdk.queries;

import com.google.common.base.Optional;

import java.util.List;

/**
 * Queries for entities with getters and copy functions for where, sort, limit and offset
 * @param <I> Interface of the entity, i.e., Category interface
 * @param <R> Result of the query, i.e., CategoryImpl class
 * @param <M> (query) Model
 */
public interface EntityQuery<I, R, M> extends Query<I, R> {
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
}
