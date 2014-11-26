package io.sphere.sdk.search;

import org.junit.Test;

import java.lang.Exception;
import java.lang.Integer;
import java.math.BigDecimal;

import static org.fest.assertions.Assertions.assertThat;

public class RangeTest {

    @Test
    public void createsClosedRange() throws Exception {
        final Range<BigDecimal> range = Range.of(NumberBound.of(4), NumberBound.of(10));
        final Bound<BigDecimal> lowerBound = range.lowerBound().get();
        final Bound<BigDecimal> upperBound = range.upperBound().get();
        assertThat(lowerBound.endpoint()).isEqualTo(new BigDecimal(4));
        assertThat(lowerBound.isExclusive()).isTrue();
        assertThat(upperBound.endpoint()).isEqualTo(new BigDecimal(10));
        assertThat(upperBound.isExclusive()).isTrue();
    }

    @Test
    public void createsRangeLessThan() throws Exception {
        final Range<BigDecimal> range = Range.lessThan(NumberBound.of(4));
        final Bound<BigDecimal> upperBound = range.upperBound().get();
        assertThat(range.lowerBound().isPresent()).isFalse();
        assertThat(upperBound.endpoint()).isEqualTo(new BigDecimal(4));
        assertThat(upperBound.isExclusive()).isTrue();
    }

    @Test
    public void createsRangeGreaterThan() throws Exception {
        final Range<BigDecimal> range = Range.greaterThan(NumberBound.of(4));
        final Bound<BigDecimal> lowerBound = range.lowerBound().get();
        assertThat(range.upperBound().isPresent()).isFalse();
        assertThat(lowerBound.endpoint()).isEqualTo(new BigDecimal(4));
        assertThat(lowerBound.isExclusive()).isTrue();
    }

    @Test
    public void createsRangeAll() throws Exception {
        final Range<Integer> range = Range.<Integer>all();
        assertThat(range.lowerBound().isPresent()).isFalse();
        assertThat(range.upperBound().isPresent()).isFalse();
    }

    @Test
    public void getsEndpointsOfBoundsAsOptionals() throws Exception {
        final Range<BigDecimal> range = Range.lessThan(NumberBound.of(4));
        assertThat(range.lowerEndpoint().isPresent()).isFalse();
        assertThat(range.upperEndpoint().get()).isEqualTo(new BigDecimal(4));
    }

    @Test
    public void isClosedWhenBothBoundsAreDefined() throws Exception {
        assertThat(Range.of(NumberBound.of(4), NumberBound.of(10)).hasClosedBounds()).isTrue();
        assertThat(Range.lessThan(NumberBound.of(4)).hasClosedBounds()).isFalse();
        assertThat(Range.all().hasClosedBounds()).isFalse();
    }

    @Test(expected = InvertedBoundsException.class)
    public void isInvalidWhenBoundsAreInverted() throws Exception {
        Range.of(NumberBound.of(5), NumberBound.of(4));
    }

    @Test(expected = SameExclusiveBoundsException.class)
    public void isInvalidWhenBoundsAreEqualAndExclusive() throws Exception {
        Range.of(NumberBound.of(4), NumberBound.of(4));
    }

    @Test
    public void equalityIsReflexiveAndSymmetric() throws Exception {
        final Range<BigDecimal> range = Range.of(NumberBound.of(4), NumberBound.of(10));
        final Range<BigDecimal> other = Range.of(NumberBound.of(4), NumberBound.of(10));
        assertThat(range.equals(other) && other.equals(range)).isTrue();
        assertThat(range.hashCode() == other.hashCode());
    }
}
