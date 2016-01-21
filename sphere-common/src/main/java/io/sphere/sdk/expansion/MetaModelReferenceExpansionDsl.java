package io.sphere.sdk.expansion;

import java.util.function.Function;

public interface MetaModelReferenceExpansionDsl<T, C, E> extends ReferenceExpansionDsl<T, C> {
    /**
     * Creates a new object with the properties of the old object but adds a new expansion path to it by using meta models.
     *
     * <p>An example in the product projection context:</p>
     * {@include.example io.sphere.sdk.products.expansion.ProductProjectionExpansionModelTest#expandCategoriesDemo()}
     *
     * @param m function to use the meta model for expansions to create an expansion path
     * @return new object
     */
    C plusExpansionPaths(final Function<E, ExpansionPathContainer<T>> m);

    /**
     * Creates a new object with the properties of the old object but replaces all expansion paths with a single {@code expansionPath} by using meta models.
     *
     * <p>An example in the product projection context:</p>
     * {@include.example io.sphere.sdk.products.expansion.ProductProjectionExpansionModelTest#withExpansionPathDslDemo()}
     *
     * @param m function to use the meta model for expansions to create an expansion path
     * @return new object
     */
    C withExpansionPaths(final Function<E, ExpansionPathContainer<T>> m);
}
