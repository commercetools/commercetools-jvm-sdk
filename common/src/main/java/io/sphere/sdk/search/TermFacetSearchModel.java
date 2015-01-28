package io.sphere.sdk.search;

import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class TermFacetSearchModel<T, V> extends SearchModelImpl<T> {
    protected TypeSerializer<V> typeSerializer;

    TermFacetSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment);
        this.typeSerializer = typeSerializer;
    }

    public FacetExpression<T> allTerms() {
        return only(asList());
    }

    public FacetExpression<T> only(final V value) {
        return only(asList(value));
    }

    public FacetExpression<T> only(final Iterable<V> values) {
        return new TermFacetExpression<>(this, values, typeSerializer);
    }
}
