package io.sphere.sdk.search;

import org.junit.Test;

import java.lang.Exception;
import java.lang.Integer;
import java.math.BigDecimal;

import static org.fest.assertions.Assertions.assertThat;

public class RangeTest {

    @Test
    public void createsClosedRange() throws Exception {
        final Range<Integer> range = Range.of(Bound.exclusive(4), Bound.inclusive(10));
        final Bound<Integer> lowerBound = range.lowerBound().get();
        final Bound<Integer> upperBound = range.upperBound().get();
        assertThat(lowerBound.endpoint()).isEqualTo(4);
        assertThat(lowerBound.isExclusive()).isTrue();
        assertThat(upperBound.endpoint()).isEqualTo(10);
        assertThat(upperBound.isInclusive()).isTrue();
    }

    @Test
    public void createsRangeLessThan() throws Exception {
        final Range<Integer> range = Range.lessThan(4);
        final Bound<Integer> upperBound = range.upperBound().get();
        assertThat(range.lowerBound().isPresent()).isFalse();
        assertThat(upperBound.endpoint()).isEqualTo(4);
        assertThat(upperBound.isExclusive()).isTrue();
    }

    @Test
    public void createsRangeGreaterThan() throws Exception {
        final Range<Integer> range = Range.greaterThan(4);
        final Bound<Integer> lowerBound = range.lowerBound().get();
        assertThat(range.upperBound().isPresent()).isFalse();
        assertThat(lowerBound.endpoint()).isEqualTo(4);
        assertThat(lowerBound.isExclusive()).isTrue();
    }

    @Test
    public void createsRangeLessThanOrEqualTo() throws Exception {
        final Range<Integer> range = Range.atMost(4);
        final Bound<Integer> upperBound = range.upperBound().get();
        assertThat(range.lowerBound().isPresent()).isFalse();
        assertThat(upperBound.endpoint()).isEqualTo(4);
        assertThat(upperBound.isInclusive()).isTrue();
    }

    @Test
    public void createsRangeGreaterThanOrEqualTo() throws Exception {
        final Range<Integer> range = Range.atLeast(4);
        final Bound<Integer> lowerBound = range.lowerBound().get();
        assertThat(range.upperBound().isPresent()).isFalse();
        assertThat(lowerBound.endpoint()).isEqualTo(4);
        assertThat(lowerBound.isInclusive()).isTrue();
    }

    @Test
    public void createsRangeAll() throws Exception {
        final Range<Integer> range = Range.<Integer>all();
        assertThat(range.lowerBound().isPresent()).isFalse();
        assertThat(range.upperBound().isPresent()).isFalse();
    }

    @Test
    public void constructsANewBoundWithDifferentEndpoint() throws Exception {
        Bound<Integer> bound = Bound.exclusive(4);
        Range<Integer> range = Range.of(bound, Bound.exclusive(10));
        Bound<Integer> changedBound = bound.withEndpoint(6);
        assertThat(changedBound.endpoint()).isEqualTo(6);
        assertThat(bound.endpoint()).isEqualTo(4);
        assertThat(range.lowerEndpoint().get()).isEqualTo(4);
    }

    @Test
    public void getsEndpointsOfBoundsAsOptionals() throws Exception {
        final Range<Integer> range = Range.lessThan(4);
        assertThat(range.lowerEndpoint().isPresent()).isFalse();
        assertThat(range.upperEndpoint().get()).isEqualTo(4);
    }

    @Test
    public void isClosedWhenBothBoundsAreDefined() throws Exception {
        assertThat(Range.of(Bound.exclusive(4), Bound.inclusive(10)).hasClosedBounds()).isTrue();
        assertThat(Range.lessThan(4).hasClosedBounds()).isFalse();
        assertThat(Range.all().hasClosedBounds()).isFalse();
    }

    @Test
    public void isEmptyWhenItCannotContainAnyValue() throws Exception {
        assertThat(Range.of(Bound.inclusive(4), Bound.inclusive(4)).isEmpty()).isFalse();
        assertThat(Range.of(Bound.inclusive(4), Bound.exclusive(4)).isEmpty()).isTrue();
        assertThat(Range.of(Bound.exclusive(4), Bound.inclusive(4)).isEmpty()).isTrue();
    }

    @Test(expected = InvertedBoundsException.class)
    public void isInvalidWhenBoundsAreInverted() throws Exception {
        Range.of(Bound.exclusive(5), Bound.exclusive(4));
    }

    @Test(expected = SameExclusiveBoundsException.class)
    public void isInvalidWhenBoundsAreEqualAndExclusive() throws Exception {
        Range.of(Bound.exclusive(4), Bound.exclusive(4));
    }

    @Test
    public void equalityIsReflexiveAndSymmetric() throws Exception {
        final Range<Integer> range = Range.of(Bound.exclusive(4), Bound.exclusive(10));
        final Range<Integer> other = Range.of(Bound.exclusive(4), Bound.exclusive(10));
        assertThat(range.equals(other) && other.equals(range)).isTrue();
        assertThat(range.hashCode() == other.hashCode());
    }

    @Test
    public void printsRange() throws Exception {
        final Range<Integer> range = Range.of(Bound.exclusive(4), Bound.inclusive(10));
        assertThat(range.toString()).isEqualTo("(4 to 10]");
        assertThat(Range.atLeast(4).toString()).isEqualTo("[4 to *)");
        assertThat(Range.all().toString()).isEqualTo("(* to *)");
    }
}
