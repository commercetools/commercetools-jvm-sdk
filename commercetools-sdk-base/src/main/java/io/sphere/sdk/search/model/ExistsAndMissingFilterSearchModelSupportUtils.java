package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

final class ExistsAndMissingFilterSearchModelSupportUtils {
    public ExistsAndMissingFilterSearchModelSupportUtils() {
    }

    static <T> List<FilterExpression<T>> exists(final SearchModel<T> model) {
        return verbFilter(model, "exists");
    }

    static <T> List<FilterExpression<T>> missing(final SearchModel<T> model) {
        return verbFilter(model, "missing");
    }

    private static <T> List<FilterExpression<T>> verbFilter(final SearchModel<T> model, final String verb) {
        final String path = model.buildPath().stream().collect(joining("."));
        return singletonList(FilterExpression.of(path + ":" + verb));
    }
}
