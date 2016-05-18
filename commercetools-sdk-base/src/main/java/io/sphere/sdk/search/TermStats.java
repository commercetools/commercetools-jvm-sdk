package io.sphere.sdk.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = TermStatsImpl.class)
public interface TermStats {
    String getTerm();

    Long getCount();

    static TermStats of(final String term, final Long count) {
        return new TermStatsImpl(term, count);
    }
}