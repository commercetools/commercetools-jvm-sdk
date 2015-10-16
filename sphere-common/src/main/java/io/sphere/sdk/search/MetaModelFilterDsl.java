package io.sphere.sdk.search;

import java.util.List;
import java.util.function.Function;

public interface MetaModelFilterDsl<T, C, L> extends FilterDsl<T, C> {

    /**
     * Creates a new object with the properties of the old object but replaces all filters with a list of filter expressions to the {@code queryFilters} by using meta models.
     * @param m function to use the meta model for filters to create a list of filter expression
     * @return new object
     */
    C withQueryFilters(final Function<L, List<FilterExpression<T>>> m);

    /**
     * Creates a new object with the properties of the old object but adds a new filter list to the {@code queryFilters} by using meta models.
     * @param m function to use the meta model for filters to create a filter expression
     * @return new object
     */
    C plusQueryFilters(final Function<L, List<FilterExpression<T>>> m);

    /**
     * Creates a new object with the properties of the old object but replaces all filters with a list of filter expressions to the {@code resultFilters} by using meta models.
     * @param m function to use the meta model for filters to create a list of filter expression
     * @return new object
     */
    C withResultFilters(final Function<L, List<FilterExpression<T>>> m);

    /**
     * Creates a new object with the properties of the old object but adds a new filter list to the {@code resultFilters} by using meta models.
     * @param m function to use the meta model for filters to create a filter expression
     * @return new object
     */
    C plusResultFilters(final Function<L, List<FilterExpression<T>>> m);

    /**
     * Creates a new object with the properties of the old object but replaces all filters with a list of filter expressions to the {@code facetFilters} by using meta models.
     * @param m function to use the meta model for filters to create a list of filter expression
     * @return new object
     */
    C withFacetFilters(final Function<L, List<FilterExpression<T>>> m);

    /**
     * Creates a new object with the properties of the old object but adds a new filter list to the {@code facetFilters} by using meta models.
     * @param m function to use the meta model for filters to create a filter expression
     * @return new object
     */
    C plusFacetFilters(final Function<L, List<FilterExpression<T>>> m);
}