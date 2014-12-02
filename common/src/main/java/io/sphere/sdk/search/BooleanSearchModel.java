package io.sphere.sdk.search;

import java.util.Optional;

public class BooleanSearchModel<T> extends TermSearchModel<T, Boolean> {

    public BooleanSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    protected String render(final Boolean value) {
        return value ? "true" : "false";
    }
}
