package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

final class ChannelByIdFetchImpl extends MetaModelFetchDslImpl<Channel, ChannelByIdFetch, ChannelExpansionModel<Channel>> implements ChannelByIdFetch {
    ChannelByIdFetchImpl(final String id) {
        super(id, ChannelEndpoint.ENDPOINT, ChannelExpansionModel.of(), ChannelByIdFetchImpl::new);
    }

    public ChannelByIdFetchImpl(MetaModelFetchDslBuilder<Channel, ChannelByIdFetch, ChannelExpansionModel<Channel>> builder) {
        super(builder);
    }
}
