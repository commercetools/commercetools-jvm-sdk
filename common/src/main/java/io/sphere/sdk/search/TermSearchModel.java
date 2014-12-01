package io.sphere.sdk.search;

import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public abstract class TermSearchModel<T, V> extends SearchModelImpl<T> {

    public TermSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    /**
     * Adapts values for a search request.
     * @param value to be adapted.
     * @return the generated value.
     */
    protected abstract String render(V value);

    public FacetExpression<T> anyTerm() {
        return isIn(asList());
    }

    public FacetExpression<T> is(final V value) {
        return isIn(asList(value));
    }

    public FacetExpression<T> isIn(final Iterable<V> values) {
        return new TermFacetExpression<>(this, toStringTerms(values));
    }

    private Iterable<String> toStringTerms(final Iterable<V> values) {
        return toStream(values).map(v -> render(v)).filter(v -> !v.isEmpty()).collect(toList());
    }
}
