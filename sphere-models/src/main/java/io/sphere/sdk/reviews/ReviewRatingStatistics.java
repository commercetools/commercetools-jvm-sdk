package io.sphere.sdk.reviews;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Map;

@JsonDeserialize(as = ReviewRatingStatisticsImpl.class)
public interface ReviewRatingStatistics {
    Double getAverageRating();

    Integer getCount();

    Integer getHighestRating();

    Integer getLowestRating();

    /**
     * A mapping from a concrete rating to the count of this rating.
     * It uses a sparse representation so ratings with count of 0 can be absent.
     * Use {@link Map#getOrDefault(Object, Object)} with 0 as second parameter.
     *
     * @return ratings distribution
     */
    Map<Integer, Integer> getRatingsDistribution();
}
