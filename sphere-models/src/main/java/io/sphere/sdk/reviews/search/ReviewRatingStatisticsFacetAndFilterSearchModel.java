package io.sphere.sdk.reviews.search;

import io.sphere.sdk.search.model.RangeTermFacetAndFilterSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

public class ReviewRatingStatisticsFacetAndFilterSearchModel<T> extends SearchModelImpl<T> {
    public ReviewRatingStatisticsFacetAndFilterSearchModel(final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeTermFacetAndFilterSearchModel<T> averageRating() {
        return numberSearchModel("averageRating").facetedAndFiltered();
    }

    public RangeTermFacetAndFilterSearchModel<T> count() {
        return numberSearchModel("count").facetedAndFiltered();
    }

    public RangeTermFacetAndFilterSearchModel<T> highestRating() {
        return numberSearchModel("highestRating").facetedAndFiltered();
    }

    public RangeTermFacetAndFilterSearchModel<T> lowestRating() {
        return numberSearchModel("lowestRating").facetedAndFiltered();
    }
}
