package io.sphere.sdk.inventories.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.inventories.InventoryEntry;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 *
 * {@include.example io.sphere.sdk.inventories.commands.InventoryEntryUpdateCommandTest#setExpectedDelivery()}
 */
public class SetExpectedDelivery extends UpdateAction<InventoryEntry> {
    private final Optional<ZonedDateTime> expectedDelivery;

    private SetExpectedDelivery(final Optional<ZonedDateTime> expectedDelivery) {
        super("setExpectedDelivery");
        this.expectedDelivery = expectedDelivery;
    }

    public Optional<ZonedDateTime> getExpectedDelivery() {
        return expectedDelivery;
    }

    public static SetExpectedDelivery of(final ZonedDateTime expectedDelivery) {
        return of(Optional.of(expectedDelivery));
    }

    public static SetExpectedDelivery of(final Optional<ZonedDateTime> expectedDelivery) {
        return new SetExpectedDelivery(expectedDelivery);
    }
}
