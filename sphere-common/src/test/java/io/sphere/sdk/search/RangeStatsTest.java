package io.sphere.sdk.search;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class RangeStatsTest {

    @Test
    public void unboundRangeIsParsed() throws Exception {
        final RangeStats<Double> stats = stats(0.0, 0, "", "");
        assertThat(stats.getLowerEndpoint().isPresent()).isFalse();
        assertThat(stats.getUpperEndpoint().isPresent()).isFalse();
    }

    @Test
    public void boundRangeIsParsed() throws Exception {
        final RangeStats<Double> stats = stats(3.0, 5, "3.0", "5.0");
        assertThat(stats.getLowerEndpoint().get()).isEqualTo(3.0);
        assertThat(stats.getUpperEndpoint().get()).isEqualTo(5.0);
    }

    private RangeStats<Double> stats(final double from, final double to, final String fromStr, final String toStr) {
        return new RangeStats<>(from, to, fromStr, toStr, 2, 2, 4.0, 5.0, 3.0, 6.0);
    }
}