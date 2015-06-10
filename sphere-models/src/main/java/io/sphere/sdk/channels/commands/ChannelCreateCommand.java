package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.commands.CreateCommandImpl;

/** Creates a channel.

 <p>Example:</p>
 {@include.example io.sphere.sdk.channels.commands.ChannelCreateCommandTest#execution()}

@see io.sphere.sdk.channels.ChannelDraftBuilder
 */
public class ChannelCreateCommand extends CreateCommandImpl<Channel, ChannelDraft> {
    private ChannelCreateCommand(final ChannelDraft body) {
        super(body, ChannelEndpoint.ENDPOINT);
    }

    public static ChannelCreateCommand of(final ChannelDraft draft) {
        return new ChannelCreateCommand(draft);
    }
}
