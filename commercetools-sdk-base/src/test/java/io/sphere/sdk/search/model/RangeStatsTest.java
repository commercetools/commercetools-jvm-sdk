package io.sphere.sdk.search.model;

import io.sphere.sdk.search.model.RangeStats;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class RangeStatsTest {

    @Test
    public void unboundRangeIsParsed() throws Exception {
        final RangeStats stats = stats("0.0", "0.0", "", "");
        assertThat(stats.getLowerEndpoint()).isNull();
        assertThat(stats.getUpperEndpoint()).isNull();
    }

    @Test
    public void boundRangeIsParsed() throws Exception {
        final RangeStats stats = stats("3.0", "5.0", "3.0", "5.0");
        assertThat(stats.getLowerEndpoint()).isEqualTo("3.0");
        assertThat(stats.getUpperEndpoint()).isEqualTo("5.0");
    }

    private RangeStats stats(final String from, final String to, final String fromStr, final String toStr) {
        return new RangeStats(from, to, fromStr, toStr, 2L, null, 2L, "4.0", "5.0", "3.0", 6.0);
    }
}