package io.sphere.sdk.reviews.search;

import io.sphere.sdk.search.model.RangeTermFacetSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import java.math.BigDecimal;

public class ReviewRatingStatisticsFacetSearchModel<T> extends SearchModelImpl<T> {
    public ReviewRatingStatisticsFacetSearchModel(final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeTermFacetSearchModel<T, BigDecimal> averageRating() {
        return numberSearchModel("averageRating").faceted();
    }
}
