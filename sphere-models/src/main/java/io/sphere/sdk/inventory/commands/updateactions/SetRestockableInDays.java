package io.sphere.sdk.inventory.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.inventory.InventoryEntry;

import javax.annotation.Nullable;

/**
 *
 * {@include.example io.sphere.sdk.inventories.commands.InventoryEntryUpdateCommandTest#setRestockableInDays()}
 */
public class SetRestockableInDays extends UpdateAction<InventoryEntry> {
    @Nullable
    private final Integer restockableInDays;

    private SetRestockableInDays(@Nullable final Integer restockableInDays) {
        super("setRestockableInDays");
        this.restockableInDays = restockableInDays;
    }

    @Nullable
    public Integer getRestockableInDays() {
        return restockableInDays;
    }

    public static SetRestockableInDays of(@Nullable final Integer restockableInDays) {
        return new SetRestockableInDays(restockableInDays);
    }
}
