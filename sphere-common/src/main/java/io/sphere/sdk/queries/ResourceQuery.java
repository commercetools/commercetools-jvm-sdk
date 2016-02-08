package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import javax.annotation.Nullable;

import java.util.List;

/**
 * Queries for resources.
 * @param <T> type of the resource, i.e., Category
 */
public interface ResourceQuery<T> extends Query<T>, ExpansionPathContainer<T> {
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
