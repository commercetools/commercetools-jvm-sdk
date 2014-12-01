package io.sphere.sdk.search;

import java.util.Optional;

import static java.util.Arrays.asList;

public class BooleanSearchModel<T> extends SearchModelImpl<T> {

    public BooleanSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public BooleanSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public FacetExpression<T> is(final boolean value) {
        String boolAsString = value ? "true" : "false";
        return new TermFacetExpression<>(this, asList(boolAsString));
    }
}
