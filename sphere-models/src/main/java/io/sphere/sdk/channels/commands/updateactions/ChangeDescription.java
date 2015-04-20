package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;

/**
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandTest#changeDescription()}
 */
public class ChangeDescription extends UpdateAction<Channel> {
    private final LocalizedStrings description;

    private ChangeDescription(final LocalizedStrings description) {
        super("changeDescription");
        this.description = description;
    }

    public LocalizedStrings getDescription() {
        return description;
    }

    public static ChangeDescription of(final LocalizedStrings description) {
        return new ChangeDescription(description);
    }
}
