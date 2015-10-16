package io.sphere.sdk.search.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RangeTest {

    @Test
    public void createsBoundedRange() throws Exception {
        final Range<Integer> range = range(Bound.exclusive(4), Bound.inclusive(10));
        assertThat(range.lowerEndpoint()).isEqualTo(4);
        assertThat(range.isLowerBoundExclusive()).isTrue();
        assertThat(range.upperEndpoint()).isEqualTo(10);
        assertThat(range.isUpperBoundInclusive()).isTrue();
    }

    @Test
    public void createsUnboundedRange() throws Exception {
        final Range<Integer> range = new Range<>(null, null);
        assertThat(range.lowerBound()).isNull();
        assertThat(range.upperBound()).isNull();
    }

    @Test
    public void constructsANewBoundWithDifferentEndpoint() throws Exception {
        Bound<Integer> bound = Bound.exclusive(4);
        Range<Integer> range = range(bound, Bound.exclusive(10));
        Bound<Integer> changedBound = bound.withEndpoint(6);
        assertThat(changedBound.endpoint()).isEqualTo(6);
        assertThat(bound.endpoint()).isEqualTo(4);
        assertThat(range.lowerEndpoint()).isEqualTo(4);
    }

    @Test
    public void getsEndpointsOfBoundsAsOptionals() throws Exception {
        final Range<Integer> range = new Range<>(null, Bound.exclusive(4));
        assertThat(range.lowerEndpoint()).isNull();
        assertThat(range.upperEndpoint()).isEqualTo(4);
    }

    @Test
    public void determinesBoundTypes() throws Exception {
        final Range<Integer> range = range(Bound.exclusive(4), Bound.inclusive(10));
        assertThat(range.isLowerBoundExclusive()).isTrue();
        assertThat(range.isLowerBoundInclusive()).isFalse();
        assertThat(range.isUpperBoundInclusive()).isTrue();
        assertThat(range.isUpperBoundExclusive()).isFalse();
    }

    @Test
    public void isClosedWhenBothBoundsAreDefined() throws Exception {
        assertThat(range(Bound.exclusive(4), Bound.inclusive(10)).isBounded()).isTrue();
        assertThat(new Range<>(null, Bound.exclusive(4)).isBounded()).isFalse();
        assertThat(new Range<>(null, null).isBounded()).isFalse();
    }

    @Test
    public void isEmptyWhenItCannotContainAnyValue() throws Exception {
        assertThat(range(Bound.inclusive(4), Bound.inclusive(4)).isEmpty()).isFalse();
        assertThat(range(Bound.inclusive(4), Bound.exclusive(4)).isEmpty()).isTrue();
        assertThat(range(Bound.exclusive(4), Bound.inclusive(4)).isEmpty()).isTrue();
    }

    @Test(expected = InvertedBoundsException.class)
    public void isInvalidWhenBoundsAreInverted() throws Exception {
        range(Bound.exclusive(5), Bound.exclusive(4));
    }

    @Test(expected = SameExclusiveBoundsException.class)
    public void isInvalidWhenBoundsAreEqualAndExclusive() throws Exception {
        range(Bound.exclusive(4), Bound.exclusive(4));
    }

    @Test
    public void equalityIsReflexiveAndSymmetric() throws Exception {
        final Range<Integer> range = range(Bound.exclusive(4), Bound.exclusive(10));
        final Range<Integer> other = range(Bound.exclusive(4), Bound.exclusive(10));
        assertThat(range.equals(other) && other.equals(range)).isTrue();
        assertThat(range.hashCode() == other.hashCode());
    }

    @Test
    public void printsRange() throws Exception {
        final Range<Integer> range = range(Bound.exclusive(4), Bound.inclusive(10));
        assertThat(range.toString()).isEqualTo("(4 to 10]");
        assertThat(new Range<>(Bound.inclusive(4), null).toString()).isEqualTo("[4 to *)");
        assertThat(new Range<>(null, null).toString()).isEqualTo("(* to *)");
    }

    private <T extends Comparable<? super T>> Range<T> range(Bound<T> lowerBound, Bound<T> upperBound) {
        return new Range<>(lowerBound, upperBound);
    }
}
