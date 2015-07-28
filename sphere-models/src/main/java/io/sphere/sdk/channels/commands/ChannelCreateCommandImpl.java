package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.commands.CreateCommandImpl;

final class ChannelCreateCommandImpl extends CreateCommandImpl<Channel, ChannelDraft> implements ChannelCreateCommand {
    ChannelCreateCommandImpl(final ChannelDraft body) {
        super(body, ChannelEndpoint.ENDPOINT);
    }
}
