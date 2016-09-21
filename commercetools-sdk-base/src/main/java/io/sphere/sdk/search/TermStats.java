package io.sphere.sdk.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;

@JsonDeserialize(as = TermStatsImpl.class)
public interface TermStats {
    String getTerm();

    Long getCount();

    @Nullable
    Long getProductCount();

    static TermStats of(final String term, final Long count, @Nullable final Long productCount) {
        return new TermStatsImpl(term, count, productCount);
    }

    static TermStats of(final String term, final Long count) {
        return TermStats.of(term, count, null);
    }
}