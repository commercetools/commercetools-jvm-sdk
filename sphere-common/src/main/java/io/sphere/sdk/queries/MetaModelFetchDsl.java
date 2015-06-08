package io.sphere.sdk.queries;

import java.util.List;
import java.util.function.Function;

public interface MetaModelFetchDsl<T, C extends MetaModelFetchDsl<T, C, E>, E> extends FetchDsl<T, C>, MetaModelExpansionDsl<T, C, E> {

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
