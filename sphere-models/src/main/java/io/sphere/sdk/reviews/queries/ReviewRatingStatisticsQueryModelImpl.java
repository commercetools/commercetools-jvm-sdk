package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.queries.*;

public class ReviewRatingStatisticsQueryModelImpl<T> extends QueryModelImpl<T> implements ReviewRatingStatisticsQueryModel<T> {
    public ReviewRatingStatisticsQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public IntegerQuerySortingModel<T> count() {
        return integerModel("count");
    }

    @Override
    public DoubleQuerySortingModel<T> averageRating() {
        return doubleModel("averageRating");
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }
}
