package io.sphere.internal.command;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.client.model.LocalizedString;
import io.sphere.client.model.Money;
import io.sphere.client.model.ReferenceId;
import io.sphere.client.shop.model.*;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonRawValue;

import java.util.Currency;

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
        private final ReferenceId<Channel> supplyChannel;

        public AddLineItemFromMasterVariant(String productId, int quantity, final String channelId) {
            super("addLineItem");
            this.productId = productId;
            this.quantity = quantity;
            this.supplyChannel = Channel.reference(channelId).toReferenceIdOrNull();
        }

        public String getProductId() { return productId; }
        public int getQuantity() { return quantity; }

        public ReferenceId<Channel> getSupplyChannel() {
            return supplyChannel;
        }
    }

    @Immutable
    public static final class AddLineItem extends AddLineItemFromMasterVariant {
        private final int variantId;

        public AddLineItem(String productId, int quantity, int variantId) {
            this(productId, quantity, variantId, null);
        }

        public AddLineItem(String productId, int quantity, int variantId, final String channelId) {
            super(productId, quantity, channelId);
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
    public static final class AddCustomLineItem extends CartUpdateAction {
        @JsonRawValue private LocalizedString name;
        private Money money;
        private String slug;
        private int quantity;
        private final ReferenceId<TaxCategory> taxCategory;

        public AddCustomLineItem(LocalizedString name, Money money, String slug, ReferenceId<TaxCategory> taxCategory, int quantity) {
            super("addCustomLineItem");
            this.taxCategory = taxCategory;
            this.quantity = quantity;
            this.slug = slug;
            this.money = money;
            this.name = name;
        }

        public LocalizedString getName() { return name; }

        public Money getMoney() { return money; }

        public String getSlug() { return slug; }

        public int getQuantity() { return quantity; }

        public ReferenceId<TaxCategory> getTaxCategory() { return taxCategory; }
    }

    @Immutable
    public static class RemoveCustomLineItem extends CartUpdateAction {
        private String customLineItemId;
        
        public RemoveCustomLineItem(String lineItemId) {
            super("removeCustomLineItem");
            this.customLineItemId = lineItemId;
        }

        public String getCustomLineItemId() { return customLineItemId; }
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
        private final String trackingId;
        private final String carrier;
        private final boolean isReturn;

        public SetTrackingData(final TrackingData trackingData) {
            super("addTrackingData");
            this.trackingId = trackingData.getTrackingId();
            this.carrier = trackingData.getCarrier();
            this.isReturn = trackingData.isReturn();
        }

        public String getTrackingId() { return trackingId; }

        public String getCarrier() { return carrier; }

        public boolean isReturn() { return isReturn; }
    }

    @Immutable
    public static final class RecalculateCartPrices extends CartUpdateAction {

        public RecalculateCartPrices() {
            super("recalculate");
        }
    }
}
