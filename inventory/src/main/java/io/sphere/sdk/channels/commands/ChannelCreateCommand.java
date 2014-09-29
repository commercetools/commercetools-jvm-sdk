package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.NewChannel;
import io.sphere.sdk.commands.CreateCommandImpl;

/** Creates a channel.

 <p>Example:</p>
 {@include.example io.sphere.sdk.channels.ChannelIntegrationTest#createChannel()}

@see io.sphere.sdk.channels.NewChannelBuilder
 */
public class ChannelCreateCommand extends CreateCommandImpl<Channel, NewChannel> {
    public ChannelCreateCommand(final NewChannel body) {
        super(body, ChannelsEndpoint.ENDPOINT);
    }
}
