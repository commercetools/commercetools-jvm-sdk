package io.sphere.internal.command;

import net.jcip.annotations.Immutable;

/** Commands issued against the HTTP endpoints for working the inventory. */
public class InventoryCommands {
    @Immutable
    public static final class CreateInventoryEntry implements Command {
        private final String sku;
        private final long quantityOnStock;

        public CreateInventoryEntry(String sku, long quantityOnStock) {
            this.sku = sku;
            this.quantityOnStock = quantityOnStock;
        }

        public String getSku() {
            return sku;
        }

        public long getQuantityOnStock() {
            return quantityOnStock;
        }

        @Override
        public String toString() {
            return "CreateInventoryEntry{" +
                    "sku='" + sku + '\'' +
                    ", quantityOnStock=" + quantityOnStock +
                    '}';
        }
    }
}
