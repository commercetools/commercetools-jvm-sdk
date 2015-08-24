package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import java.util.List;

public class RangeFacetResult extends Base implements FacetResult {
    private final List<RangeStats> ranges;

    @JsonCreator
    private RangeFacetResult(final List<RangeStats> ranges) {
        this.ranges = ranges;
    }

    public List<RangeStats> getRanges() {
        return ranges;
    }

    public static RangeFacetResult of(final List<RangeStats> ranges) {
        return new RangeFacetResult(ranges);
    }
}
