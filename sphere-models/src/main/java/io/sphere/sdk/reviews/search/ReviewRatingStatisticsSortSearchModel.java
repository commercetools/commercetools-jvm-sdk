package io.sphere.sdk.reviews.search;

import io.sphere.sdk.search.model.*;

import javax.annotation.Nullable;

public final class ReviewRatingStatisticsSortSearchModel<T> extends SortableSearchModel<T, SingleValueSortSearchModel<T>> {
    public ReviewRatingStatisticsSortSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, SingleValueSortSearchModelFactory.of());
    }

    public SingleValueSortSearchModel<T> averageRating() {
        return searchModel(this, "averageRating").sorted();
    }

    public SingleValueSortSearchModel<T> highestRating() {
        return searchModel(this, "highestRating").sorted();
    }

    public SingleValueSortSearchModel<T> lowestRating() {
        return searchModel(this, "lowestRating").sorted();
    }

    public SingleValueSortSearchModel<T> count() {
        return searchModel(this, "count").sorted();
    }
}
