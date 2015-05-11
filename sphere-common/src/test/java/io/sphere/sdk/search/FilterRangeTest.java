package io.sphere.sdk.search;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class FilterRangeTest {

    @Test
    public void buildsFilterRange() throws Exception {
        final Range<Integer> range = FilterRange.of(4, 10);
        final Bound<Integer> lowerBound = range.lowerBound().get();
        final Bound<Integer> upperBound = range.upperBound().get();
        assertThat(lowerBound.endpoint()).isEqualTo(4);
        assertThat(lowerBound.isInclusive()).isTrue();
        assertThat(upperBound.endpoint()).isEqualTo(10);
        assertThat(upperBound.isInclusive()).isTrue();
    }

    @Test
    public void buildsFilterRangeLessThanOrEqualTo() throws Exception {
        final Range<Integer> range = FilterRange.atMost(4);
        final Bound<Integer> upperBound = range.upperBound().get();
        assertThat(range.lowerBound().isPresent()).isFalse();
        assertThat(upperBound.endpoint()).isEqualTo(4);
        assertThat(upperBound.isInclusive()).isTrue();
    }

    @Test
    public void buildsFilterRangeGreaterThanOrEqualTo() throws Exception {
        final Range<Integer> range = FilterRange.atLeast(4);
        final Bound<Integer> lowerBound = range.lowerBound().get();
        assertThat(range.upperBound().isPresent()).isFalse();
        assertThat(lowerBound.endpoint()).isEqualTo(4);
        assertThat(lowerBound.isInclusive()).isTrue();
    }
}