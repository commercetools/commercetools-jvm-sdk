package io.sphere.sdk.search;

import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class FacetRangeTest {

    @Test
    public void buildsFacetRange() throws Exception {
        final Range<Integer> range = FacetRange.of(4, 10);
        final Bound<Integer> lowerBound = range.lowerBound().get();
        final Bound<Integer> upperBound = range.upperBound().get();
        assertThat(lowerBound.endpoint()).isEqualTo(4);
        assertThat(lowerBound.isInclusive()).isTrue();
        assertThat(upperBound.endpoint()).isEqualTo(10);
        assertThat(upperBound.isExclusive()).isTrue();
    }

    @Test
    public void buildsFacetRangeLessThan() throws Exception {
        final Range<Integer> range = FacetRange.lessThan(4);
        final Bound<Integer> upperBound = range.upperBound().get();
        assertThat(range.lowerBound().isPresent()).isFalse();
        assertThat(upperBound.endpoint()).isEqualTo(4);
        assertThat(upperBound.isExclusive()).isTrue();
    }

    @Test
    public void buildsFacetRangeGreaterThanOrEqualTo() throws Exception {
        final Range<Integer> range = FacetRange.atLeast(4);
        final Bound<Integer> lowerBound = range.lowerBound().get();
        assertThat(range.upperBound().isPresent()).isFalse();
        assertThat(lowerBound.endpoint()).isEqualTo(4);
        assertThat(lowerBound.isInclusive()).isTrue();
    }
}