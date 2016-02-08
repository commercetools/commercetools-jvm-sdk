package io.sphere.sdk.reviews.search;

import io.sphere.sdk.search.model.RangeTermFilterSearchModel;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.SearchModelImpl;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public final class ReviewRatingStatisticsFilterSearchModel<T> extends SearchModelImpl<T> {
    public ReviewRatingStatisticsFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeTermFilterSearchModel<T, BigDecimal> averageRating() {
        return numberSearchModel("averageRating").filtered();
    }

    public RangeTermFilterSearchModel<T, BigDecimal> count() {
        return numberSearchModel("count").filtered();
    }

    public RangeTermFilterSearchModel<T, BigDecimal> highestRating() {
        return numberSearchModel("highestRating").filtered();
    }

    public RangeTermFilterSearchModel<T, BigDecimal> lowestRating() {
        return numberSearchModel("lowestRating").filtered();
    }
}
