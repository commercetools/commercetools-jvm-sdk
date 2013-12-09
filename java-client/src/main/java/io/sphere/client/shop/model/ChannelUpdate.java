package io.sphere.client.shop.model;

import io.sphere.internal.command.ChannelCommands;
import io.sphere.internal.command.Update;

public class ChannelUpdate extends Update<ChannelCommands.ChannelUpdateAction> {

    public ChannelUpdate changeKey(final String key) {
        add(new ChannelCommands.ChangeKey(key));
        return this;
    }
}
