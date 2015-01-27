package io.sphere.sdk.search;

import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class TermFacetSearchModel<T, V> extends SearchModelImpl<T> {
    protected TypeParser<V> typeParser;

    TermFacetSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final TypeParser<V> typeParser) {
        super(parent, pathSegment);
        this.typeParser = typeParser;
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
        return toStream(values).map(v -> typeParser.renderer().apply(v)).filter(v -> !v.isEmpty()).collect(toList());
    }
}
