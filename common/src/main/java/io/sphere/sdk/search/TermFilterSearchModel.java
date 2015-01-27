package io.sphere.sdk.search;

import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class TermFilterSearchModel<T, V> extends SearchModelImpl<T> {
    protected TypeParser<V> typeParser;

    TermFilterSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final TypeParser<V> typeParser) {
        super(parent, pathSegment);
        this.typeParser = typeParser;
    }

    public FilterExpression<T> is(final V value) {
        return isIn(asList(value));
    }

    public FilterExpression<T> isIn(final Iterable<V> values) {
        return new TermFilterExpression<>(this, toStringTerms(values));
    }

    private Iterable<String> toStringTerms(final Iterable<V> values) {
        return toStream(values).map(v -> typeParser.renderer().apply(v)).filter(v -> !v.isEmpty()).collect(toList());
    }
}
