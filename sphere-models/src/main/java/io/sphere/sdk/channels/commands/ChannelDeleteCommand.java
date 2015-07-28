package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;

/** Deletes a channel.

 <p>Example:</p>
 {@include.example io.sphere.sdk.channels.commands.ChannelDeleteCommandTest#execution()}
 */
public interface ChannelDeleteCommand extends ByIdDeleteCommand<Channel> {
    static DeleteCommand<Channel> of(final Versioned<Channel> versioned) {
        return new ChannelDeleteCommandImpl(versioned);
    }
}
