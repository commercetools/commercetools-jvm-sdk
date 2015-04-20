package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;

/** Deletes a channel.

 <p>Example:</p>
 {@include.example io.sphere.sdk.channels.ChannelIntegrationTest#deleteChannelById()}
 */
public class ChannelDeleteCommand extends ByIdDeleteCommandImpl<Channel> {
    private ChannelDeleteCommand(final Versioned<Channel> versioned) {
        super(versioned, ChannelsEndpoint.ENDPOINT);
    }

    public static DeleteCommand<Channel> of(final Versioned<Channel> versioned) {
        return new ChannelDeleteCommand(versioned);
    }
}
