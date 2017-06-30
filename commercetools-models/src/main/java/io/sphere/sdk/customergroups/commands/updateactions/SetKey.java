package io.sphere.sdk.customergroups.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customergroups.CustomerGroup;


/**
 Changes the key of the customer group.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.customergroups.commands.CustomerGroupUpdateCommandIntegrationTest#setKey()}

 @see CustomerGroup
 */
public final class SetKey extends UpdateActionImpl<CustomerGroup> {
    private final String key;

    private SetKey(final String key) {
        super("setKey");
        this.key = key;
    }

    public static SetKey of(final String key) {
        return new SetKey(key);
    }

    public String getKey() {
        return key;
    }
}
