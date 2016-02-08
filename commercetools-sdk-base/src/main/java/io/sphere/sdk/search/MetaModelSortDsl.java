package io.sphere.sdk.search;

import java.util.function.Function;

public interface MetaModelSortDsl<T, C, S> extends SortDsl<T, C> {

    /**
     * Creates a new object with the properties of the old object but replaces all sort expressions with a single sort expression to it by using meta models.
     * @param m function to use the meta model for sort to create a sort expression
     * @return new object
     */
    C withSort(final Function<S, SortExpression<T>> m);

    /**
     * Creates a new object with the properties of the old object but adds a new sort expression to it by using meta models.
     * @param m function to use the meta model for sort to create a sort expression
     * @return new object
     */
    C plusSort(final Function<S, SortExpression<T>> m);
}