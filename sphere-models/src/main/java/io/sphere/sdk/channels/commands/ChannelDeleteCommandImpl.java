package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;

final class ChannelDeleteCommandImpl extends ByIdDeleteCommandImpl<Channel> implements ChannelDeleteCommand {
    ChannelDeleteCommandImpl(final Versioned<Channel> versioned) {
        super(versioned, ChannelEndpoint.ENDPOINT);
    }
}
