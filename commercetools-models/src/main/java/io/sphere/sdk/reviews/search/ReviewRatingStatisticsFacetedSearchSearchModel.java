package io.sphere.sdk.reviews.search;

import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

public final class ReviewRatingStatisticsFacetedSearchSearchModel<T> extends SearchModelImpl<T> {
    public ReviewRatingStatisticsFacetedSearchSearchModel(final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeTermFacetedSearchSearchModel<T> averageRating() {
        return numberSearchModel("averageRating").facetedAndFiltered();
    }

    public RangeTermFacetedSearchSearchModel<T> count() {
        return numberSearchModel("count").facetedAndFiltered();
    }

    public RangeTermFacetedSearchSearchModel<T> highestRating() {
        return numberSearchModel("highestRating").facetedAndFiltered();
    }

    public RangeTermFacetedSearchSearchModel<T> lowestRating() {
        return numberSearchModel("lowestRating").facetedAndFiltered();
    }
}
