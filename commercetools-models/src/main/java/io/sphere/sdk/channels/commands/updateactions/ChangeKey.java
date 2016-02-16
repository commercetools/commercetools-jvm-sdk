package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 * Changes the key of a channel.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandIntegrationTest#changeKey()}
 */
public final class ChangeKey extends UpdateActionImpl<Channel> {
    private final String key;

    private ChangeKey(final String key) {
        super("changeKey");
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static ChangeKey of(final String key) {
        return new ChangeKey(key);
    }
}
