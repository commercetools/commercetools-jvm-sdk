package io.sphere.internal.command;

import net.jcip.annotations.Immutable;
import org.joda.time.DateTime;

/** Commands issued against the HTTP endpoints for working the inventory. */
public class InventoryEntryCommands {

    public static abstract class InventoryEntryUpdateAction extends UpdateAction {
        public InventoryEntryUpdateAction(String action) {
            super(action);
        }
    }

    @Immutable
    public static class SetExpectedDelivery extends InventoryEntryUpdateAction {
        private final DateTime expectedDelivery;

        public SetExpectedDelivery(DateTime expectedDelivery) {
            super("setExpectedDelivery");
            this.expectedDelivery = expectedDelivery;
        }

        public DateTime getExpectedDelivery() {
            return expectedDelivery;
        }
    }

    @Immutable
    public static class SetRestockableInDays extends InventoryEntryUpdateAction {

        private final long restockableInDays;

        public SetRestockableInDays(long restockableInDays) {
            super("setRestockableInDays");
            this.restockableInDays = restockableInDays;
        }

        public long getRestockableInDays() {
            return restockableInDays;
        }
    }

    @Immutable
    public static class RemoveQuantity extends InventoryEntryUpdateAction {
        private final long quantity;

        public RemoveQuantity(long quantity) {
            super("removeQuantity");
            this.quantity = quantity;
        }

        public long getQuantity() {
            return quantity;
        }
    }

    @Immutable
    public static class AddQuantity extends InventoryEntryUpdateAction {
        private final long quantity;

        public AddQuantity(long quantity) {
            super("addQuantity");
            this.quantity = quantity;
        }

        public long getQuantity() {
            return quantity;
        }
    }

    @Immutable
    public static final class CreateInventoryEntry implements Command {
        private final String sku;
        private final long quantityOnStock;
        private final Long restockableInDays;
        private final DateTime expectedDelivery;

        public CreateInventoryEntry(String sku, long quantityOnStock, Long restockableInDays, DateTime expectedDelivery) {
            this.sku = sku;
            this.quantityOnStock = quantityOnStock;
            this.restockableInDays = restockableInDays;
            this.expectedDelivery = expectedDelivery;
        }

        public String getSku() {
            return sku;
        }

        public long getQuantityOnStock() {
            return quantityOnStock;
        }

        public DateTime getExpectedDelivery() {
            return expectedDelivery;
        }

        public Long getRestockableInDays() {
            return restockableInDays;
        }

        @Override
        public String toString() {
            return "CreateInventoryEntry{" +
                    "sku='" + sku + '\'' +
                    ", quantityOnStock=" + quantityOnStock +
                    ", restockableInDays=" + restockableInDays +
                    ", expectedDelivery=" + expectedDelivery +
                    '}';
        }
    }
}
