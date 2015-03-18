package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public class TermStats extends Base {
    private final String term;
    private final long count;

    @JsonCreator
    private TermStats(final String term, final long count) {
        this.term = term;
        this.count = count;
    }

    public String getTerm() {
        return term;
    }

    public long getCount() {
        return count;
    }

    public static TermStats of(final String term, final int count) {
        return new TermStats(term, count);
    }
}