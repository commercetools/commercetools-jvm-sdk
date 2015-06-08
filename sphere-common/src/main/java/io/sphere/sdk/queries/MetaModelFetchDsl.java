package io.sphere.sdk.queries;

import java.util.function.Function;

public interface MetaModelFetchDsl<T, C extends MetaModelFetchDsl<T, C, E>, E> extends FetchDsl<T, C> {
    C plusExpansionPath(final Function<E, ExpansionPath<T>> m);

    C withExpansionPath(final Function<E, ExpansionPath<T>> m);
}
