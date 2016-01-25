package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;
import io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModel;
import io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModelImpl;
import io.sphere.sdk.types.queries.CustomResourceQueryModelImpl;

final class ChannelQueryModelImpl extends CustomResourceQueryModelImpl<Channel> implements ChannelQueryModel {
    ChannelQueryModelImpl(final QueryModel<Channel> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<Channel> key() {
        return stringModel("key");
    }

    @Override
    public ReviewRatingStatisticsQueryModel<Channel> reviewRatingStatistics() {
        return new ReviewRatingStatisticsQueryModelImpl<>(this, "reviewRatingStatistics");
    }
}
