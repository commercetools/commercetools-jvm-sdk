package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.util.List;

public interface EntitySearch<T> extends Search<T> {

    @Nullable
    SearchText text();

    List<FacetExpression<T>> facets();

    List<FilterExpression<T>> filterResults();

    List<FilterExpression<T>> filterQueries();

    List<FilterExpression<T>> filterFacets();

    List<SearchSort<T>> sort();

    @Nullable
    Long limit();

    @Nullable
    Long offset();
}
