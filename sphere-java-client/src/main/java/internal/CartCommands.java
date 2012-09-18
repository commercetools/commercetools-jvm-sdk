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

    @Immutable
    public static final class RemoveLineItem extends CommandBase {
        private final String lineItemId;

        public RemoveLineItem(String id, String version, String lineItemId) {
            super(id, version);
            this.lineItemId = lineItemId;
        }

        public String getLineItemId() { return lineItemId; }
    }

    @Immutable
    public static final class UpdateLineItemQuantity extends CommandBase {
        private final String lineItemId;
        private final int quantity;

        public UpdateLineItemQuantity(String id, String version, String lineItemId, int quantity) {
            super(id, version);
            this.lineItemId = lineItemId;
            this.quantity = quantity;
        }

        public String getLineItemId() { return lineItemId; }
        public int getQuantity() { return quantity; }
    }

    @Immutable
    public static final class SetCustomer extends CommandBase {
        private final String customerId;

        public SetCustomer(String id, String version, String customerId) {
            super(id, version);
            this.customerId = customerId;
        }

        public String getCustomerId() { return customerId; }
    }

    @Immutable
    public static final class SetShippingAddress extends CommandBase {
        private final String address;

        public SetShippingAddress(String id, String version, String address) {
            super(id, version);
            this.address = address;
        }

        public String getAddress() { return address; }
    }

    @Immutable
    public static final class OrderCart extends CommandBase {

        //TODO add payment state
        public OrderCart(String id, String version) {
            super(id, version);
        }
    }
}
