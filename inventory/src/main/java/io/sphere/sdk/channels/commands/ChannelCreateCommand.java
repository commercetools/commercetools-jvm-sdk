package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.NewChannel;
import io.sphere.sdk.commands.CreateCommandImpl;

public class ChannelCreateCommand extends CreateCommandImpl<Channel, NewChannel> {
    public ChannelCreateCommand(final NewChannel body) {
        super(body, ChannelsEndpoint.ENDPOINT);
    }
}
