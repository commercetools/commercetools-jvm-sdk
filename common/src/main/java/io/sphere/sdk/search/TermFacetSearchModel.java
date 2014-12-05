package io.sphere.sdk.search;

import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class TermFacetSearchModel<T, V> extends SearchModelImpl<T> {
    protected Function<V, String> renderer;

    TermFacetSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final Function<V, String> renderer) {
        super(parent, pathSegment);
        this.renderer = renderer;
    }

    public FacetExpression<T> allTerms() {
        return only(asList());
    }

    public FacetExpression<T> only(final V value) {
        return only(asList(value));
    }

    public FacetExpression<T> only(final Iterable<V> values) {
        return new TermFacetExpression<>(this, toStringTerms(values));
    }

    private Iterable<String> toStringTerms(final Iterable<V> values) {
        return toStream(values).map(v -> renderer.apply(v)).filter(v -> !v.isEmpty()).collect(toList());
    }
}
