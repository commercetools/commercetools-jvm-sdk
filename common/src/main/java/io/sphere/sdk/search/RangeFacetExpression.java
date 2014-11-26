package io.sphere.sdk.search;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import static io.sphere.sdk.utils.IterableUtils.toStream;

class RangeFacetExpression<T, V extends Comparable<? super V>> extends SearchModelFacetExpression<T>  {
    private final Iterable<Range<V>> ranges;

    public RangeFacetExpression(SearchModel<T> searchModel, Iterable<Range<V>> ranges) {
        super(searchModel);
        this.ranges = ranges;
    }

    @Override
    protected String render() {
        return ":range" + renderRanges(ranges);
    }

    private String renderRanges(Iterable<Range<V>> ranges) {
        return toStream(ranges).map(this::renderRange).collect(rangeJoiner());
    }

    private String renderRange(Range<V> range) {
        return range.render();
    }

    private Collector<CharSequence, ?, String> rangeJoiner() {
        return Collectors.joining(",");
    }
}
