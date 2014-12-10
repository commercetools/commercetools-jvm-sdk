package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.models.Versioned;

/** Deletes a channel.

 <p>Example:</p>
 {@include.example io.sphere.sdk.channels.ChannelIntegrationTest#deleteChannelById()}
 */
public class ChannelDeleteByIdCommand extends DeleteByIdCommandImpl<Channel> {
    private ChannelDeleteByIdCommand(final Versioned<Channel> versioned) {
        super(versioned, ChannelsEndpoint.ENDPOINT);
    }

    public static ChannelDeleteByIdCommand of(final Versioned<Channel> versioned) {
        return new ChannelDeleteByIdCommand(versioned);
    }
}
