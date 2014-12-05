package io.sphere.sdk.search;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

abstract class RangeExpression<T> extends SearchModelExpression<T> {
    final Iterable<String> ranges;

    RangeExpression(final SearchModel<T> searchModel, final Iterable<String> ranges) {
        super(searchModel);
        this.ranges = ranges;
    }

    @Override
    protected String render() {
        return ":range" + toRangeExpression(ranges);
    }

    /**
     * Turns a group of terms into an expression of the form "(e1 to e2),(e3 to e4),..."
     * @return the generated term expression.
     */
    private String toRangeExpression(Iterable<String> ranges) {
        return toStream(ranges).filter(r -> !r.isEmpty()).collect(joining(","));
    }
}
