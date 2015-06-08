package io.sphere.sdk.queries;

import java.util.List;
import java.util.function.Function;

public interface MetaModelFetchDsl<T, C extends MetaModelFetchDsl<T, C, E>, E> extends FetchDsl<T, C> {

    /**
     * Creates a new object with the properties of the old object but adds a new expansion path to it by using meta models.
     *
     * <p>An example in the product projection context:</p>
     * {@include.example io.sphere.sdk.products.expansion.ProductProjectionExpansionModelTest#expandCategoriesDemo()}
     *
     * @param m function to use the meta model for expansions to create an expansion path
     * @return new object
     */
    C plusExpansionPath(final Function<E, ExpansionPath<T>> m);

    /**
     * Creates a new object with the properties of the old object but replaces all expansion paths with a single {@code expansionPath} by using meta models.
     *
     * <p>An example in the product projection context:</p>
     * {@include.example io.sphere.sdk.products.expansion.ProductProjectionExpansionModelTest#withExpansionPathDslDemo()}
     *
     * @param m function to use the meta model for expansions to create an expansion path
     * @return new object
     */
    C withExpansionPath(final Function<E, ExpansionPath<T>> m);

    @Override
    List<ExpansionPath<T>> expansionPaths();

    @Override
    C plusExpansionPath(final ExpansionPath<T> expansionPath);

    @Override
    C withExpansionPath(final ExpansionPath<T> expansionPath);

    @Override
    C withExpansionPath(final List<ExpansionPath<T>> expansionPaths);
}
