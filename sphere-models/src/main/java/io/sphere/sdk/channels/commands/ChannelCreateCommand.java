package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.commands.CreateCommand;

/** Creates a channel.

 <p>Example:</p>
 {@include.example io.sphere.sdk.channels.commands.ChannelCreateCommandTest#execution()}

@see io.sphere.sdk.channels.ChannelDraftBuilder
 */
public interface ChannelCreateCommand extends CreateCommand<Channel> {
    static ChannelCreateCommand of(final ChannelDraft draft) {
        return new ChannelCreateCommandImpl(draft);
    }
}
