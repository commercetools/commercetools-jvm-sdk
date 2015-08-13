package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;

/**
 * Creates a channel.
 *
 * <p>Example:</p>
 * {@include.example io.sphere.sdk.channels.commands.ChannelCreateCommandTest#execution()}
 *
 * @see io.sphere.sdk.channels.ChannelDraftBuilder
 */
public interface ChannelCreateCommand extends CreateCommand<Channel>, MetaModelExpansionDsl<Channel, ChannelCreateCommand, ChannelExpansionModel<Channel>> {
    static ChannelCreateCommand of(final ChannelDraft draft) {
        return new ChannelCreateCommandImpl(draft);
    }
}
