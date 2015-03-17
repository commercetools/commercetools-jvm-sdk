package io.sphere.sdk.inventories.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.inventories.InventoryEntry;

import java.util.Optional;

/**
 *
 * {@include.example io.sphere.sdk.inventories.commands.InventoryEntryUpdateCommandTest#setRestockableInDays()}
 */
public class SetRestockableInDays extends UpdateAction<InventoryEntry> {
    private final Optional<Integer> restockableInDays;

    private SetRestockableInDays(final Optional<Integer> restockableInDays) {
        super("setRestockableInDays");
        this.restockableInDays = restockableInDays;
    }

    public Optional<Integer> getRestockableInDays() {
        return restockableInDays;
    }

    public static SetRestockableInDays of(final int restockableInDays) {
        return of(Optional.of(restockableInDays));
    }

    public static SetRestockableInDays of(final Optional<Integer> restockableInDays) {
        return new SetRestockableInDays(restockableInDays);
    }
}
