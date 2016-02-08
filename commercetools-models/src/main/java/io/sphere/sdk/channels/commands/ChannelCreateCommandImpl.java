package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;

final class ChannelCreateCommandImpl extends MetaModelCreateCommandImpl<Channel, ChannelCreateCommand, ChannelDraft, ChannelExpansionModel<Channel>> implements ChannelCreateCommand {
    ChannelCreateCommandImpl(final MetaModelCreateCommandBuilder<Channel, ChannelCreateCommand, ChannelDraft, ChannelExpansionModel<Channel>> builder) {
        super(builder);
    }

    ChannelCreateCommandImpl(final ChannelDraft body) {
        super(body, ChannelEndpoint.ENDPOINT, ChannelExpansionModel.of(), ChannelCreateCommandImpl::new);
    }
}
