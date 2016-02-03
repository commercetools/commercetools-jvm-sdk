package io.sphere.sdk.reviews.search;

import io.sphere.sdk.search.model.RangeTermFacetSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import java.math.BigDecimal;

public final class ReviewRatingStatisticsFacetSearchModel<T> extends SearchModelImpl<T> {
    public ReviewRatingStatisticsFacetSearchModel(final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeTermFacetSearchModel<T, BigDecimal> averageRating() {
        return numberSearchModel("averageRating").faceted();
    }

    public RangeTermFacetSearchModel<T, BigDecimal> count() {
        return numberSearchModel("count").faceted();
    }

    public RangeTermFacetSearchModel<T, BigDecimal> highestRating() {
        return numberSearchModel("highestRating").faceted();
    }

    public RangeTermFacetSearchModel<T, BigDecimal> lowestRating() {
        return numberSearchModel("lowestRating").faceted();
    }
}
