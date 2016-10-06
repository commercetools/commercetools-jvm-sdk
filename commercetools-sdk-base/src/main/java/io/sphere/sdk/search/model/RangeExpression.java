package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.util.function.Function;

import static io.sphere.sdk.utils.SphereInternalUtils.requireNonEmpty;
import static io.sphere.sdk.utils.SphereInternalUtils.toStream;
import static java.util.stream.Collectors.joining;

abstract class RangeExpression<T, V extends Comparable<? super V>> extends SearchModelExpression<T, V> {
    private final Iterable<? extends Range<V>> ranges;

    RangeExpression(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final Iterable<? extends Range<V>> ranges, @Nullable final String alias, final Boolean isCountingProducts) {
        super(searchModel, typeSerializer, alias, isCountingProducts);
        this.ranges = requireNonEmpty(ranges);
    }

    /**
     * Turns a group of ranges into an expression of the form ":range(e1 to e2),(e3 to e4),..."
     * @return the generated range expression.
     */
    public String value() {
        return ":range" + toStream(ranges)
                .map(r -> r.serialize(serializer()))
                .filter(r -> !r.isEmpty())
                .collect(joining(","));
    }
}
