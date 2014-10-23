package io.sphere.sdk.search;

import io.sphere.sdk.queries.QueryParameter;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface EntitySearch<T> extends Search<T> {

    Locale lang();

    Optional<String> text();

    List<FacetExpression<T>> facets();

    List<FilterExpression<T>> filterResults();

    List<FilterExpression<T>> filterQueries();

    List<FilterExpression<T>> filterFacets();

    List<SearchSort<T>> sort();

    Optional<Long> limit();

    Optional<Long> offset();

    List<QueryParameter> additionalQueryParameters();
}
