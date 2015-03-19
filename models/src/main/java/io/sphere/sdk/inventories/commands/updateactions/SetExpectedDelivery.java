package io.sphere.sdk.inventories.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.inventories.InventoryEntry;

import java.time.Instant;
import java.util.Optional;

/**
 *
 * {@include.example io.sphere.sdk.inventories.commands.InventoryEntryUpdateCommandTest#setExpectedDelivery()}
 */
public class SetExpectedDelivery extends UpdateAction<InventoryEntry> {
    private final Optional<Instant> expectedDelivery;

    private SetExpectedDelivery(final Optional<Instant> expectedDelivery) {
        super("setExpectedDelivery");
        this.expectedDelivery = expectedDelivery;
    }

    public Optional<Instant> getExpectedDelivery() {
        return expectedDelivery;
    }

    public static SetExpectedDelivery of(final Instant expectedDelivery) {
        return of(Optional.of(expectedDelivery));
    }

    public static SetExpectedDelivery of(final Optional<Instant> expectedDelivery) {
        return new SetExpectedDelivery(expectedDelivery);
    }
}
