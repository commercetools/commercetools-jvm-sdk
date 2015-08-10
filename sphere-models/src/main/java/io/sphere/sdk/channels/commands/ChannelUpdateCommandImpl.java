package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.commands.*;
import io.sphere.sdk.models.Versioned;

import java.util.List;


final class ChannelUpdateCommandImpl extends ReferenceExpandeableUpdateCommandDslImpl<Channel, ChannelUpdateCommand, ChannelExpansionModel<Channel>> implements ChannelUpdateCommand {
    public ChannelUpdateCommandImpl(final Versioned<Channel> versioned, final List<? extends UpdateAction<Channel>> updateActions) {
        super(versioned, updateActions, ChannelEndpoint.ENDPOINT, ChannelUpdateCommandImpl::new, ChannelExpansionModel.of());
    }

    ChannelUpdateCommandImpl(final ReferenceExpanseableUpdateCommandDslBuilder<Channel, ChannelUpdateCommand, ChannelExpansionModel<Channel>> builder) {
        super(builder);
    }
}
