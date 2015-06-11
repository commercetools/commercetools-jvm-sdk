package io.sphere.sdk.search;

import io.sphere.sdk.http.HttpQueryParameter;

import java.util.List;
import java.util.Optional;

public interface EntitySearch<T> extends Search<T> {

    Optional<SearchText> text();

    List<FacetExpression<T>> facets();

    List<FilterExpression<T>> filterResults();

    List<FilterExpression<T>> filterQueries();

    List<FilterExpression<T>> filterFacets();

    List<SearchSort<T>> sort();

    Optional<Long> limit();

    Optional<Long> offset();

    List<HttpQueryParameter> additionalQueryParameters();
}
