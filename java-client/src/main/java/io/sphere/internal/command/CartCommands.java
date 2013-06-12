package io.sphere.internal.command;

import java.util.Currency;
import io.sphere.client.model.ReferenceId;
import io.sphere.client.shop.model.*;
import com.neovisionaries.i18n.CountryCode;
import net.jcip.annotations.Immutable;

/** Commands issued against the HTTP endpoints for working with shopping carts. */
public class CartCommands {
    @Immutable
    public static final class CreateCart implements Command {
        private Currency currency;
        private String customerId;
        private CountryCode country;
        private Cart.InventoryMode inventoryMode;

        public CreateCart(Currency currency, String customerId, CountryCode country, Cart.InventoryMode inventoryMode) {
            this.currency = currency;
            this.customerId = customerId;
            this.country = country;
            this.inventoryMode = inventoryMode;

        }

        public Currency getCurrency() { return currency; }
        public String getCustomerId() { return customerId; }
        public Cart.InventoryMode getInventoryMode() { return inventoryMode; }
        public CountryCode getCountry() { return country; }
    }

    @Immutable
    public static final class LoginWithAnonymousCart extends CommandBase {
        private final String email;
        private final String password;

        public LoginWithAnonymousCart(String cartId, int cartVersion, String email, String password) {
            super(cartId, cartVersion);
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

        public OrderCart(String cartId, int cartVersion, PaymentState paymentState) {
            super(cartId, cartVersion);
            this.paymentState = paymentState;
        }

        public PaymentState getPaymentState() { return paymentState; }
    }

    public static abstract class CartUpdateAction extends UpdateAction {
        public CartUpdateAction(String action) {
            super(action);
        }
    }

    @Immutable
    public static class AddLineItemFromMasterVariant extends CartUpdateAction {
        private final String productId;
        private final int quantity;

        public AddLineItemFromMasterVariant(String productId, int quantity) {
            super("addLineItem");
            this.productId = productId;
            this.quantity = quantity;
        }

        public String getProductId() { return productId; }
        public int getQuantity() { return quantity; }
    }

    @Immutable
    public static final class AddLineItem extends AddLineItemFromMasterVariant {
        private final int variantId;

        public AddLineItem(String productId, int quantity, int variantId) {
            super(productId, quantity);
            this.variantId = variantId;
        }

        public int getVariantId() { return variantId; }
    }

    @Immutable
    public static class RemoveLineItem extends CartUpdateAction {
        private final String lineItemId;

        public RemoveLineItem(String lineItemId) {
            super("removeLineItem");
            this.lineItemId = lineItemId;
        }

        public String getLineItemId() { return lineItemId; }
    }

    @Immutable
    public static class ChangeLineItemQuantity extends CartUpdateAction {
        private final String lineItemId;
        private final int quantity;

        public ChangeLineItemQuantity(String lineItemId, int quantity) {
            super("changeLineItemQuantity");
            this.lineItemId = lineItemId;
            this.quantity = quantity;
        }

        public String getLineItemId() { return lineItemId; }

        public int getQuantity() { return quantity; }
    }

    @Immutable
    public static final class DecreaseLineItemQuantity extends RemoveLineItem {
        private final int quantity;

        public DecreaseLineItemQuantity(String lineItemId, int quantity) {
            super(lineItemId);
            this.quantity = quantity;
        }

        public int getQuantity() { return quantity; }
    }

    @Immutable
    public static final class SetCustomerEmail extends CartUpdateAction {
        private final String email;

        public SetCustomerEmail(String email) {
            super("setCustomerEmail");
            this.email = email;
        }

        public String getEmail() { return email; }
    }

    @Immutable
    public static final class SetBillingAddress extends CartUpdateAction {
        private final Address address;

        public SetBillingAddress(Address address) {
            super("setBillingAddress");
            this.address = address;
        }

        public Address getAddress() { return address; }
    }

    @Immutable
    public static final class SetShippingAddress extends CartUpdateAction {
        private final Address address;

        public SetShippingAddress(Address address) {
            super("setShippingAddress");
            this.address = address;
        }

        public Address getAddress() { return address; }
    }

    @Immutable
    public static final class SetCountry extends CartUpdateAction {
        private final CountryCode country;

        public SetCountry(CountryCode country) {
            super("setCountry");
            this.country = country;
        }

        public CountryCode getCountry() { return country; }
    }

    @Immutable
    public static final class SetShippingMethod extends CartUpdateAction {
        private final ReferenceId<ShippingMethod> shippingMethod;

        public SetShippingMethod(ReferenceId<ShippingMethod> shippingMethod) {
            super("setShippingMethod");
            this.shippingMethod = shippingMethod;
        }

        public ReferenceId<ShippingMethod> getShippingMethod() { return shippingMethod; }
    }    
    
    @Immutable
    public static final class SetCustomShippingMethod extends CartUpdateAction {
        private final String shippingMethodName;
        private final ShippingRate shippingRate;
        private final ReferenceId<TaxCategory> taxCategory;

        public SetCustomShippingMethod(String shippingMethodName, ShippingRate shippingRate, ReferenceId<TaxCategory> taxCategory) {
            super("setCustomShippingMethod");
            this.shippingMethodName = shippingMethodName;
            this.shippingRate = shippingRate;
            this.taxCategory = taxCategory;
        }

        public String getShippingMethodName() { return shippingMethodName; }

        public ShippingRate getShippingRate() { return shippingRate; }

        public ReferenceId<TaxCategory> getTaxCategory() { return taxCategory; }
    }

    @Immutable
    public static final class SetTrackingData extends CartUpdateAction {
        private final String trackingData;

        public SetTrackingData(String trackingData) {
            super("setShippingMethod");
            this.trackingData = trackingData;
        }

        public String getTrackingData() { return trackingData; }
    }

    @Immutable
    public static final class RecalculateCartPrices extends CartUpdateAction {

        public RecalculateCartPrices() {
            super("recalculate");
        }
    }
}
