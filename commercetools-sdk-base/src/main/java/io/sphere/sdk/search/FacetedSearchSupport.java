package io.sphere.sdk.search;

import java.util.List;

public interface FacetedSearchSupport<T> {

    List<FacetedSearchExpression<T>> facetedSearch();
}
