package io.sphere.sdk.reviews;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Map;

@JsonDeserialize(as = ReviewRatingStatisticsImpl.class)
public interface ReviewRatingStatistics {
    Double getAverageRating();

    Integer getCount();

    Integer getHighestRating();

    Integer getLowestRating();

    Map<Integer, Integer> getRatingsDistribution();
}
