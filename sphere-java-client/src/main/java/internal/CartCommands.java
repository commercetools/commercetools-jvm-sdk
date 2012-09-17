package de.commercetools.internal;

import net.jcip.annotations.Immutable;

import java.util.Currency;

/** Commands issued against the HTTP endpoints for working with shopping carts. */
public class CartCommands {
    @Immutable
    public static final class CreateCart implements Command {
        private Currency currency;
        private String customerId;

        public CreateCart(Currency currency, String customerId) {
            this.currency = currency;
            this.customerId = customerId;
        }

        public Currency getCurrency() { return currency; }
        public String getCustomerId() { return customerId; }
    }

    @Immutable
    public static final class AddLineItem extends CommandBase {
        private final String productId;
        private final int quantity;

        public AddLineItem(String id, String version, String productId, int quantity) {
            super(id, version);
            this.productId = productId;
            this.quantity = quantity;
        }

        public String getProductId() { return productId; }
        public int getQuantity() { return quantity; }
    }
}
