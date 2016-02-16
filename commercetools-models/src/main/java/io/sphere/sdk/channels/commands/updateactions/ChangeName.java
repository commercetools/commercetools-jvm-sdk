package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;

/**
 *
 * Changes the name of a channel.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandIntegrationTest#changeName()}
 */
public final class ChangeName extends UpdateActionImpl<Channel> {
    private final LocalizedString name;

    private ChangeName(final LocalizedString name) {
        super("changeName");
        this.name = name;
    }

    public LocalizedString getName() {
        return name;
    }

    public static ChangeName of(final LocalizedString name) {
        return new ChangeName(name);
    }
}
