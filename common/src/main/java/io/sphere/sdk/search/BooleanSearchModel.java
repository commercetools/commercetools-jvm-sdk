package io.sphere.sdk.search;

import java.util.Optional;

public class BooleanSearchModel<T> extends SearchModelImpl<T> {

    public BooleanSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<T, Boolean> filter() {
        return new TermFilterSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    public TermFacetSearchModel<T, Boolean> facet() {
        return new TermFacetSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    private String render(final boolean value) {
        return value ? "true" : "false";
    }
}
