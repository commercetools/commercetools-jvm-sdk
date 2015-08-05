package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;

import java.util.List;
import java.util.function.Function;

/**
 *
 * @param <R> type of the query result
 * @param <T> unit of the query result
 * @param <C> type of the class implementing this class
 * @param <E> type of the expansion model
 */
public interface MetaModelGetDsl<R, T, C extends MetaModelGetDsl<R, T, C, E>, E> extends GetDsl<R, T, C>, MetaModelExpansionDsl<T, C, E> {

    @Override
    C plusExpansionPaths(final Function<E, ExpansionPath<T>> m);

    @Override
    C withExpansionPaths(final Function<E, ExpansionPath<T>> m);

    @Override
    List<ExpansionPath<T>> expansionPaths();

    @Override
    C plusExpansionPaths(final ExpansionPath<T> expansionPath);

    @Override
    C withExpansionPaths(final ExpansionPath<T> expansionPath);

    @Override
    C withExpansionPaths(final List<ExpansionPath<T>> expansionPaths);
}
