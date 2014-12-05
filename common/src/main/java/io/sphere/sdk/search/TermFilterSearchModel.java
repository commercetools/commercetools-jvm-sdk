package io.sphere.sdk.search;

import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class TermFilterSearchModel<T, V> extends SearchModelImpl<T> {
    protected Function<V, String> renderer;

    TermFilterSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final Function<V, String> renderer) {
        super(parent, pathSegment);
        this.renderer = renderer;
    }

    public FilterExpression<T> is(final V value) {
        return isIn(asList(value));
    }

    public FilterExpression<T> isIn(final Iterable<V> values) {
        return new TermFilterExpression<>(this, toStringTerms(values));
    }

    private Iterable<String> toStringTerms(final Iterable<V> values) {
        return toStream(values).map(v -> renderer.apply(v)).filter(v -> !v.isEmpty()).collect(toList());
    }
}
