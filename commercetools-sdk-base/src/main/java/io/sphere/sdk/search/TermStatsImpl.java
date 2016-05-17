package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

final class TermStatsImpl extends Base implements TermStats {
    private final String term;
    private final Long count;

    @JsonCreator
    TermStatsImpl(final String term, final Long count) {
        this.term = term;
        this.count = count;
    }

    public String getTerm() {
        return term;
    }

    public Long getCount() {
        return count;
    }
}