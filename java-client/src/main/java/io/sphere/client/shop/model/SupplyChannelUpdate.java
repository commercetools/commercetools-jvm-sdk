package io.sphere.client.shop.model;

import io.sphere.internal.command.SupplyChannelCommands;
import io.sphere.internal.command.Update;

public class SupplyChannelUpdate  extends Update<SupplyChannelCommands.SupplyChannelUpdateAction> {

    public SupplyChannelUpdate changeKey(final String key) {
        add(new SupplyChannelCommands.ChangeKey(key));
        return this;
    }
}
