package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModel;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

public interface ChannelQueryModel extends ResourceQueryModel<Channel>, WithCustomQueryModel<Channel> {
    StringQuerySortingModel<Channel> key();

    ReviewRatingStatisticsQueryModel<Channel> reviewRatingStatistics();

    SphereEnumerationCollectionQueryModel<Channel, ChannelRole> roles();

    LocalizedStringOptionalQueryModel<Channel> name();

    LocalizedStringOptionalQueryModel<Channel> description();

    static ChannelQueryModel of() {
        return new ChannelQueryModelImpl(null, null);
    }
}
