package io.sphere.sdk.products.queries.search;

import io.sphere.sdk.queries.QueryParameter;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface EntitySearch<T> extends Search<T> {

    Locale lang();

    Optional<String> text();

    List<Facet<T>> facets();

    List<Filter<T>> filters();

    List<Filter<T>> filterQueries();

    List<Filter<T>> filterFacets();

    List<SearchSort<T>> sort();

    Optional<Long> limit();

    Optional<Long> offset();

    List<QueryParameter> additionalQueryParameters();
}
