package io.sphere.sdk.expansion;

import java.util.List;

public interface ReferenceExpansionDsl<T, C> extends ReferenceExpansionSupport<T> {
    /**
     * Creates a new object with the properties of the old object but replaces all expansion paths with {@code expansionPaths}.
     * @param expansionPaths the new expansion paths
     * @return new object
     */
    C withExpansionPaths(final List<ExpansionPath<T>> expansionPaths);

    /**
     * Creates a new object with the properties of the old object but replaces all expansion paths with a single {@code expansionPath}.
     *
     * <p>An example in the product projection context:</p>
     * {@include.example io.sphere.sdk.products.expansion.ProductProjectionExpansionModelTest#withExpansionPathDemo()}
     *
     * <p>This method also can be used to use the same expansions as in another request:</p>
     * {@include.example io.sphere.sdk.products.expansion.ProductProjectionExpansionModelTest#useExpansionPathsOfOtherRequest()}
     *
     * @param expansionPath the new expansion paths
     * @return new object
     */
    C withExpansionPaths(final ExpansionPath<T> expansionPath);

    /**
     * Creates a new object with the properties of the old object but replaces all expansion paths with a single {@code expansionPath}.
     *
     * <p>An example in the product projection context:</p>
     * {@include.example io.sphere.sdk.products.expansion.ProductProjectionExpansionModelTest#withExpansionPathDemo()}
     *
     * <p>This method also can be used to use the same expansions as in another request:</p>
     * {@include.example io.sphere.sdk.products.expansion.ProductProjectionExpansionModelTest#useExpansionPathsOfOtherRequest()}
     *
     * @param expansionPath the new expansion paths
     * @return new object
     */
    C withExpansionPaths(final String expansionPath);

    /**
     * Creates a new object with the properties of the old object but adds {@code expansionPaths} to the expansion paths.
     * @param expansionPaths the new expansion paths to add to the existing ones
     * @return new object
     */
    C plusExpansionPaths(final List<ExpansionPath<T>> expansionPaths);

    /**
     * Creates a new object with the properties of the old object but adds {@code expansionPath} to the expansion paths.
     *
     * <p>An example in the product projection context:</p>
     * {@include.example io.sphere.sdk.products.expansion.ProductProjectionExpansionModelTest#plusExpansionPathDemo()}
     *
     * @param expansionPath the new expansion path to add to the existing ones
     * @return new object
     */
    C plusExpansionPaths(final ExpansionPath<T> expansionPath);

    /**
     * Creates a new object with the properties of the old object but adds {@code expansionPath} to the expansion paths.
     *
     * <p>An example in the product projection context:</p>
     * {@include.example io.sphere.sdk.products.expansion.ProductProjectionExpansionModelTest#plusExpansionPathDemo()}
     *
     * @param expansionPath the new expansion path to add to the existing ones
     * @return new object
     */
    C plusExpansionPaths(final String expansionPath);

    default C withExpansionPaths(ExpansionPathContainer<T> holder) {
        return withExpansionPaths(holder.expansionPaths());
    }

    default C plusExpansionPaths(ExpansionPathContainer<T> holder) {
        return plusExpansionPaths(holder.expansionPaths());
    }
}
