package io.sphere.sdk.queries;

import java.util.function.Function;

public interface MetaModelFetchDsl<T, C extends MetaModelFetchDsl<T, C, E>, E> extends FetchDsl<T, C> {

    /**
     * Creates a new object with the properties of the old object but adds a new expansion path to it by using meta models.
     * @param m function to use the meta model for expansions to create an expansion path
     * @return new object
     */
    C plusExpansionPath(final Function<E, ExpansionPath<T>> m);

    /**
     * Creates a new object with the properties of the old object but replaces all expansion paths with a single {@code expansionPath} by using meta models.
     * @param m function to use the meta model for expansions to create an expansion path
     * @return new object
     */
    C withExpansionPath(final Function<E, ExpansionPath<T>> m);
}
