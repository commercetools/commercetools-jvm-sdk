package io.sphere.sdk.inventory.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.inventory.InventoryEntry;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 *
 * {@include.example io.sphere.sdk.inventory.commands.InventoryEntryUpdateCommandTest#setExpectedDelivery()}
 */
public class SetExpectedDelivery extends UpdateActionImpl<InventoryEntry> {
    @Nullable
    private final ZonedDateTime expectedDelivery;

    private SetExpectedDelivery(@Nullable final ZonedDateTime expectedDelivery) {
        super("setExpectedDelivery");
        this.expectedDelivery = expectedDelivery;
    }

    @Nullable
    public ZonedDateTime getExpectedDelivery() {
        return expectedDelivery;
    }

    public static SetExpectedDelivery of(@Nullable final ZonedDateTime expectedDelivery) {
        return new SetExpectedDelivery(expectedDelivery);
    }
}
