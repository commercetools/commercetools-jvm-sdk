package io.sphere.sdk.channels.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.queries.*;


/**
 {@doc.gen summary channels}
 */
final class ChannelQueryImpl extends UltraQueryDslImpl<Channel, ChannelQuery, ChannelQueryModel<Channel>, ChannelExpansionModel<Channel>> implements ChannelQuery {
    ChannelQueryImpl(){
        super(ChannelEndpoint.ENDPOINT.endpoint(), ChannelQuery.resultTypeReference(), ChannelQueryModel.of(), ChannelExpansionModel.of(), ChannelQueryImpl::new);
    }

    private ChannelQueryImpl(final UltraQueryDslBuilder<Channel, ChannelQuery, ChannelQueryModel<Channel>, ChannelExpansionModel<Channel>> builder) {
        super(builder);
    }
}