package io.sphere.sdk.reviews;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class ReviewRatingStatisticsImpl extends Base implements ReviewRatingStatistics {
    private final Double averageRating;
    private final Integer highestRating;
    private final Integer lowestRating;
    private final Integer count;
    private final Map<Integer, Integer> ratingsDistribution;

    @JsonCreator
    ReviewRatingStatisticsImpl(final Double averageRating, final Integer highestRating, final Integer lowestRating, final Integer count, final Map<String, Integer> ratingsDistribution) {
        this.averageRating = averageRating;
        this.highestRating = highestRating;
        this.lowestRating = lowestRating;
        this.count = count;
        final Map<Integer, Integer> rangeDistributionsIntegerMap = new HashMap<>(ratingsDistribution.size());
        ratingsDistribution.forEach((key, value) -> rangeDistributionsIntegerMap.put(Integer.parseInt(key), value));
        this.ratingsDistribution = Collections.unmodifiableMap(rangeDistributionsIntegerMap);
    }

    @Override
    public Double getAverageRating() {
        return averageRating;
    }

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public Integer getHighestRating() {
        return highestRating;
    }

    @Override
    public Integer getLowestRating() {
        return lowestRating;
    }

    @Override
    public Map<Integer, Integer> getRatingsDistribution() {
        return ratingsDistribution;
    }
}
