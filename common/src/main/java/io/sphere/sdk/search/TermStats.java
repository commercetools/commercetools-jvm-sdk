package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public class TermStats<T> extends Base {
    private final T term;
    private final long count;

    @JsonCreator
    private TermStats(final String term, final long count) {
        this.term = term;
        this.count = count;
    }

    public T getTerm() {
        return term;
    }

    public long getCount() {
        return count;
    }

    public static TermStats<String> of(final String term, final long count) {
        return new TermStats<>(term, count);
    }
}