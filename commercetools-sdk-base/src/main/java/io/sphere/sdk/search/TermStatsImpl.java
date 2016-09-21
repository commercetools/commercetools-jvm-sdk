package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

final class TermStatsImpl extends Base implements TermStats {
    private final String term;
    private final Long count;
    @Nullable
    private final Long productCount;

    @JsonCreator
    TermStatsImpl(final String term, final Long count, @Nullable final Long productCount) {
        this.term = term;
        this.count = count;
        this.productCount = productCount;
    }

    public String getTerm() {
        return term;
    }

    public Long getCount() {
        return count;
    }

    @Nullable
    public Long getProductCount() {
        return productCount;
    }
}