package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.channels.ChannelDraft;
import io.sphere.sdk.channels.ChannelDraftDsl;
import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;

/**
 * Creates a channel.
 *
 * <p>Example:</p>
 * {@include.example io.sphere.sdk.channels.commands.ChannelCreateCommandTest#execution()}
 *
 * @see io.sphere.sdk.channels.ChannelDraftBuilder
 * @see ChannelDraft
 * @see ChannelDraftDsl
 */
public interface ChannelCreateCommand extends DraftBasedCreateCommand<Channel, ChannelDraft>, MetaModelReferenceExpansionDsl<Channel, ChannelCreateCommand, ChannelExpansionModel<Channel>> {
    static ChannelCreateCommand of(final ChannelDraft draft) {
        return new ChannelCreateCommandImpl(draft);
    }
}
