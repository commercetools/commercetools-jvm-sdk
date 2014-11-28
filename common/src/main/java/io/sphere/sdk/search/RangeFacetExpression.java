package io.sphere.sdk.search;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.joining;

class RangeFacetExpression<T> extends SearchModelFacetExpression<T>  {
    private final Iterable<String> ranges;

    public RangeFacetExpression(final SearchModel<T> searchModel, final Iterable<String> ranges) {
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
