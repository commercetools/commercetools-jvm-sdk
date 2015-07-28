package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class ChannelUpdateCommandImpl extends UpdateCommandDslImpl<Channel, ChannelUpdateCommandImpl> {
    public ChannelUpdateCommandImpl(final Versioned<Channel> versioned, final List<? extends UpdateAction<Channel>> updateActions) {
        super(versioned, updateActions, ChannelEndpoint.ENDPOINT);
    }

    public static ChannelUpdateCommandImpl of(final Versioned<Channel> versioned, final List<? extends UpdateAction<Channel>> updateActions) {
        return new ChannelUpdateCommandImpl(versioned, updateActions);
    }

    public static ChannelUpdateCommandImpl of(final Versioned<Channel> versioned, final UpdateAction<Channel> updateAction) {
        return of(versioned, asList(updateAction));
    }
}
