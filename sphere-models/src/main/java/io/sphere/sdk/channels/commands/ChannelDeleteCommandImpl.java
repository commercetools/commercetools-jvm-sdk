package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;

final class ChannelDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Channel, ChannelDeleteCommand, ChannelExpansionModel<Channel>> implements ChannelDeleteCommand {
    ChannelDeleteCommandImpl(final Versioned<Channel> versioned) {
        super(versioned, ChannelEndpoint.ENDPOINT, ChannelExpansionModel.of(), ChannelDeleteCommandImpl::new);
    }


    ChannelDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Channel, ChannelDeleteCommand, ChannelExpansionModel<Channel>> builder) {
        super(builder);
    }
}
