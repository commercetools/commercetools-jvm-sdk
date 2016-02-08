package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModel;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

public interface ChannelQueryModel extends ResourceQueryModel<Channel>, WithCustomQueryModel<Channel> {
    StringQuerySortingModel<Channel> key();

    ReviewRatingStatisticsQueryModel<Channel> reviewRatingStatistics();

    static ChannelQueryModel of() {
        return new ChannelQueryModelImpl(null, null);
    }
}
