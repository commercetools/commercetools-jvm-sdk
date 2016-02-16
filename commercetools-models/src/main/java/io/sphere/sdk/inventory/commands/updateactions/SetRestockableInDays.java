package io.sphere.sdk.inventory.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.inventory.InventoryEntry;

import javax.annotation.Nullable;

/**
 * Sets the restackable in days value.
 * 
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.inventory.commands.InventoryEntryUpdateCommandIntegrationTest#setRestockableInDays()}
 */
public final class SetRestockableInDays extends UpdateActionImpl<InventoryEntry> {
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
