package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class ChannelByIdGetImpl extends MetaModelGetDslImpl<Channel, Channel, ChannelByIdGet, ChannelExpansionModel<Channel>> implements ChannelByIdGet {
    ChannelByIdGetImpl(final String id) {
        super(id, ChannelEndpoint.ENDPOINT, ChannelExpansionModel.of(), ChannelByIdGetImpl::new);
    }

    public ChannelByIdGetImpl(MetaModelGetDslBuilder<Channel, Channel, ChannelByIdGet, ChannelExpansionModel<Channel>> builder) {
        super(builder);
    }
}
