package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.queries.DoubleQuerySortingModel;
import io.sphere.sdk.queries.IntegerQuerySortingModel;
import io.sphere.sdk.queries.OptionalQueryModel;

public interface ReviewRatingStatisticsQueryModel<T> extends OptionalQueryModel<T> {
    IntegerQuerySortingModel<T> count();

    IntegerQuerySortingModel<T> highestRating();

    IntegerQuerySortingModel<T> lowestRating();

    DoubleQuerySortingModel<T> averageRating();
}
