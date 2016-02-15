package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

/** Deletes a channel.

 <p>Example:</p>
 {@include.example io.sphere.sdk.channels.commands.ChannelDeleteCommandIntegrationTest#execution()}
 */
public interface ChannelDeleteCommand extends MetaModelReferenceExpansionDsl<Channel, ChannelDeleteCommand, ChannelExpansionModel<Channel>>, DeleteCommand<Channel> {
    static ChannelDeleteCommand of(final Versioned<Channel> versioned) {
        return new ChannelDeleteCommandImpl(versioned);
    }
}
