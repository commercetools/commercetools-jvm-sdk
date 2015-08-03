package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedStrings;

/**
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandTest#changeName()}
 */
public class ChangeName extends UpdateActionImpl<Channel> {
    private final LocalizedStrings name;

    private ChangeName(final LocalizedStrings name) {
        super("changeName");
        this.name = name;
    }

    public LocalizedStrings getName() {
        return name;
    }

    public static ChangeName of(final LocalizedStrings name) {
        return new ChangeName(name);
    }
}
