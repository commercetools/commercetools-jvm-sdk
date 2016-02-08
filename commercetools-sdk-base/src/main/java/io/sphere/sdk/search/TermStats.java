package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public final class TermStats extends Base {
    private final String term;
    private final Long count;

    @JsonCreator
    private TermStats(final String term, final Long count) {
        this.term = term;
        this.count = count;
    }

    public String getTerm() {
        return term;
    }

    public Long getCount() {
        return count;
    }

    public static TermStats of(final String term, final Long count) {
        return new TermStats(term, count);
    }
}