package io.sphere.sdk.search.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class FacetRangeTest {

    @Test
    public void buildsFacetRange() throws Exception {
        final Range<Integer> range = FacetRange.of(4, 10);
        final Bound<Integer> lowerBound = range.lowerBound();
        final Bound<Integer> upperBound = range.upperBound();
        assertThat(lowerBound.endpoint()).isEqualTo(4);
        assertThat(lowerBound.isInclusive()).isTrue();
        assertThat(upperBound.endpoint()).isEqualTo(10);
        assertThat(upperBound.isExclusive()).isTrue();
    }

    @Test
    public void buildsFacetRangeLessThan() throws Exception {
        final Range<Integer> range = FacetRange.lessThan(4);
        final Bound<Integer> upperBound = range.upperBound();
        assertThat(range.lowerBound()).isNull();
        assertThat(upperBound.endpoint()).isEqualTo(4);
        assertThat(upperBound.isExclusive()).isTrue();
    }

    @Test
    public void buildsFacetRangeGreaterThanOrEqualTo() throws Exception {
        final Range<Integer> range = FacetRange.atLeast(4);
        final Bound<Integer> lowerBound = range.lowerBound();
        assertThat(range.upperBound()).isNull();
        assertThat(lowerBound.endpoint()).isEqualTo(4);
        assertThat(lowerBound.isInclusive()).isTrue();
    }
}