package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ReferenceExpandeable;

import javax.annotation.Nullable;

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
    List<QueryPredicate<T>> predicates();

    /**
     * @return the used sort expressions for this query
     */
    List<QuerySort<T>> sort();

    @Nullable
    Boolean fetchTotal();

    @Nullable
    Long limit();

    @Nullable
    Long offset();

    String endpoint();

    @Override
    List<ExpansionPath<T>> expansionPaths();
}
