package io.sphere.client.shop.model;

import io.sphere.internal.command.InventoryEntryCommands;
import io.sphere.internal.command.Update;
import org.joda.time.DateTime;

public class InventoryEntryUpdate extends Update<InventoryEntryCommands.InventoryEntryUpdateAction> {

    public InventoryEntryUpdate addQuantity(long quantity) {
        add(new InventoryEntryCommands.AddQuantity(quantity));
        return this;
    }

    public InventoryEntryUpdate removeQuantity(long quantity) {
        add(new InventoryEntryCommands.RemoveQuantity(quantity));
        return this;
    }

    public InventoryEntryUpdate setRestockableInDays(int restockableInDays) {
        add(new InventoryEntryCommands.SetRestockableInDays(restockableInDays));
        return this;
    }

    public InventoryEntryUpdate setExpectedDelivery(DateTime expectedDelivery) {
        add(new InventoryEntryCommands.SetExpectedDelivery(expectedDelivery));
        return this;
    }
}
