package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

import java.util.function.Function;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

abstract class RangeExpression<T, V extends Comparable<? super V>> extends SearchModelExpression<T, V> {
    private final Iterable<? extends Range<V>> ranges;

    RangeExpression(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final Iterable<? extends Range<V>> ranges, @Nullable final String alias) {
        super(searchModel, typeSerializer, alias);
        this.ranges = ranges;
    }

    @Override
    protected String serializedValue() {
        return ":range" + toRangeExpression(ranges);
    }

    /**
     * Turns a group of ranges into an expression of the form "(e1 to e2),(e3 to e4),..."
     * @return the generated range expression.
     */
    private String toRangeExpression(Iterable<? extends Range<V>> ranges) {
        return toStream(ranges).map(r -> r.serialize(serializer())).filter(r -> !r.isEmpty()).collect(joining(","));
    }
}
