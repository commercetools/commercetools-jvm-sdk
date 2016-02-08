package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.queries.DoubleQuerySortingModel;
import io.sphere.sdk.queries.IntegerQuerySortingModel;
import io.sphere.sdk.queries.OptionalQueryModel;
import io.sphere.sdk.queries.QueryModel;

public interface ReviewRatingStatisticsQueryModel<T> extends OptionalQueryModel<T> {
    IntegerQuerySortingModel<T> count();

    IntegerQuerySortingModel<T> highestRating();

    IntegerQuerySortingModel<T> lowestRating();

    DoubleQuerySortingModel<T> averageRating();

    static <T> ReviewRatingStatisticsQueryModel<T> of(final QueryModel<T> parent, final String pathSegment) {
        return new ReviewRatingStatisticsQueryModelImpl<>(parent, pathSegment);
    }
}
