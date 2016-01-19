package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModel;
import io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModelImpl;
import io.sphere.sdk.types.queries.CustomResourceQueryModelImpl;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

public class ChannelQueryModel extends CustomResourceQueryModelImpl<Channel> implements WithCustomQueryModel<Channel> {
    private ChannelQueryModel(final QueryModel<Channel> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static ChannelQueryModel of() {
        return new ChannelQueryModel(null, null);
    }

    public StringQuerySortingModel<Channel> key() {
        return stringModel("key");
    }

    public ReviewRatingStatisticsQueryModel<Channel> reviewRatingStatistics() {
        return new ReviewRatingStatisticsQueryModelImpl<>(this, "reviewRatingStatistics");
    }
}
