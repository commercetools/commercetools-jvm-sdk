package de.commercetools.internal.command;

import java.util.Currency;

import de.commercetools.sphere.client.shop.model.*;

import net.jcip.annotations.Immutable;

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

        public AddLineItem(String id, int version, String productId, int quantity) {
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

        public RemoveLineItem(String id, int version, String lineItemId) {
            super(id, version);
            this.lineItemId = lineItemId;
        }

        public String getLineItemId() { return lineItemId; }
    }

    @Immutable
    public static final class UpdateLineItemQuantity extends CommandBase {
        private final String lineItemId;
        private final int quantity;

        public UpdateLineItemQuantity(String id, int version, String lineItemId, int quantity) {
            super(id, version);
            this.lineItemId = lineItemId;
            this.quantity = quantity;
        }

        public String getLineItemId() { return lineItemId; }
        public int getQuantity() { return quantity; }
    }

    @Immutable
    public static final class IncreaseLineItemQuantity extends CommandBase {
        private final String lineItemId;
        private final int quantityAdded;

        public IncreaseLineItemQuantity(String id, int version, String lineItemId, int quantityAdded) {
            super(id, version);
            this.lineItemId = lineItemId;
            this.quantityAdded = quantityAdded;
        }

        public String getLineItemId() { return lineItemId; }
        public int getQuantityAdded() { return quantityAdded; }
    }

    @Immutable
    public static final class DecreaseLineItemQuantity extends CommandBase {
        private final String lineItemId;
        private final int quantityRemoved;

        public DecreaseLineItemQuantity(String id, int version, String lineItemId, int quantityRemoved) {
            super(id, version);
            this.lineItemId = lineItemId;
            this.quantityRemoved = quantityRemoved;
        }

        public String getLineItemId() { return lineItemId; }
        public int getQuantityRemoved() { return quantityRemoved; }
    }

    @Immutable
    public static final class SetShippingAddress extends CommandBase {
        private final String address;

        public SetShippingAddress(String id, int version, String address) {
            super(id, version);
            this.address = address;
        }

        public String getAddress() { return address; }
    }

    @Immutable
    public static final class LoginWithAnonymousCart extends CommandBase {
        private final String email;
        private final String password;

        public LoginWithAnonymousCart(String id, int version, String email, String password) {

            super(id, version);
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }


    @Immutable
    public static final class OrderCart extends CommandBase {
        private final PaymentState paymentState;

        public OrderCart(String id, int version, PaymentState paymentState) {
            super(id, version);
            this.paymentState = paymentState;
        }

        public PaymentState getPaymentState() { return paymentState; }
    }
}
