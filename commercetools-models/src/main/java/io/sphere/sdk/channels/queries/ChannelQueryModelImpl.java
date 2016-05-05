package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelRole;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.reviews.queries.ReviewRatingStatisticsQueryModel;
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
        return ReviewRatingStatisticsQueryModel.of(this, "reviewRatingStatistics");
    }

    @Override
    public SphereEnumerationCollectionQueryModel<Channel, ChannelRole> roles() {
        return enumerationQueryModel("roles");
    }

    @Override
    public LocalizedStringOptionalQueryModel<Channel> name() {
        return localizedStringQuerySortingModel("name");
    }

    @Override
    public LocalizedStringOptionalQueryModel<Channel> description() {
        return localizedStringQuerySortingModel("description");
    }
}
