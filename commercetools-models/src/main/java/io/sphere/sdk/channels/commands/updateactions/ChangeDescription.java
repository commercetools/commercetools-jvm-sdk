package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;

/**
 * Changes the description of a channel.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandIntegrationTest#changeDescription()}
 */
public final class ChangeDescription extends UpdateActionImpl<Channel> {
    private final LocalizedString description;

    private ChangeDescription(final LocalizedString description) {
        super("changeDescription");
        this.description = description;
    }

    public LocalizedString getDescription() {
        return description;
    }

    public static ChangeDescription of(final LocalizedString description) {
        return new ChangeDescription(description);
    }
}
