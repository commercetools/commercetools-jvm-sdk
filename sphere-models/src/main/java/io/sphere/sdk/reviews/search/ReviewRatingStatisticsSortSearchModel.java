package io.sphere.sdk.reviews.search;

import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SingleValueSortSearchModel;
import io.sphere.sdk.search.model.SingleValueSortSearchModelFactory;
import io.sphere.sdk.search.model.SortableSearchModel;

import javax.annotation.Nullable;

public class ReviewRatingStatisticsSortSearchModel<T> extends SortableSearchModel<T, SingleValueSortSearchModel<T>> {
    public ReviewRatingStatisticsSortSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, SingleValueSortSearchModelFactory.of());
    }

    public SingleValueSortSearchModel<T> averageRating() {
        return searchModel(this, "averageRating").sorted();
    }
}
