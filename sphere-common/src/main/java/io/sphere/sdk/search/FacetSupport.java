package io.sphere.sdk.search;

import java.util.List;

public interface FacetSupport<T> {

    List<FacetExpression<T>> facets();
}
