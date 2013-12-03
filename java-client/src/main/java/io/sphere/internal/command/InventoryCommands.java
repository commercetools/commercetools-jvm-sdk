package io.sphere.internal.command;

import net.jcip.annotations.Immutable;
import org.joda.time.DateTime;

/** Commands issued against the HTTP endpoints for working the inventory. */
public class InventoryCommands {
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
