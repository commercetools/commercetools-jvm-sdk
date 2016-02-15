package io.sphere.sdk.customergroups.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customergroups.CustomerGroup;


/**
 Changes the name of the customer group.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.customergroups.commands.CustomerGroupUpdateCommandIntegrationTest#changeName()}

 @see CustomerGroup
 */
public final class ChangeName extends UpdateActionImpl<CustomerGroup> {
    private final String name;

    private ChangeName(final String name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final String name) {
        return new ChangeName(name);
    }

    public String getName() {
        return name;
    }
}
