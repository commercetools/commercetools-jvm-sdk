package io.sphere.sdk.search;

import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;
import static org.fest.assertions.Assertions.*;

public class RangeStatsTest {

    @Test
    public void unboundRangeIsParsed() throws Exception {
        final RangeStats stats = stats(valueOf(0.0), valueOf(0));
        assertThat(stats.getLowerEndpoint().isPresent()).isFalse();
        assertThat(stats.getUpperEndpoint().isPresent()).isFalse();
    }

    @Test
    public void boundRangeIsParsed() throws Exception {
        final RangeStats stats = stats(valueOf(3.0), valueOf(5));
        assertThat(stats.getLowerEndpoint().get().doubleValue()).isEqualTo(3.0);
        assertThat(stats.getUpperEndpoint().get().doubleValue()).isEqualTo(5.0);
    }

    private RangeStats stats(final BigDecimal lowerEndpoint, final BigDecimal upperEndpoint) {
        return new RangeStats(lowerEndpoint, upperEndpoint, "", "", 2, 2, ONE, ONE, ONE, ONE);
    }
}