package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.*;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import java.util.List;
import java.util.Optional;

/**
 * <p>Builder to create {@link CartDraft}s.</p>
 *
 *  <p>Example of creating a cart with custom fields:</p>
 * {@include.example io.sphere.sdk.carts.CartsCustomFieldsTest#createCartWithCustomType()}
 */
public class CartDraftBuilder extends Base implements Builder<CartDraftDsl> {
    private final CurrencyUnit currency;
    @Nullable
    private String customerId;    
    @Nullable
    private String customerEmail;
    @Nullable
    private CountryCode country;
    @Nullable
    private InventoryMode inventoryMode;
    @Nullable
    private CustomFieldsDraft custom;
    @Nullable
    private List<LineItemDraft> lineItems;
    @Nullable
    private List<CustomLineItemDraft> customLineItems;
    @Nullable
    private Address shippingAddress;
    @Nullable
    private Address billingAddress;
    @Nullable
    private Reference<ShippingMethod> shippingMethod;

    CartDraftBuilder(final CurrencyUnit currency, @Nullable final String customerId,
                     @Nullable final CountryCode country, @Nullable final InventoryMode inventoryMode,
                     @Nullable final CustomFieldsDraft custom, @Nullable String customerEmail,
                     @Nullable final List<LineItemDraft> lineItems,
                     @Nullable final List<CustomLineItemDraft> customLineItems,
                     @Nullable final Address shippingAddress,
                     @Nullable final Address billingAddress,
                     @Nullable final Reference<ShippingMethod> shippingMethod) {
        this.currency = currency;
        this.customerId = customerId;
        this.country = country;
        this.inventoryMode = inventoryMode;
        this.custom = custom;
        this.customerEmail = customerEmail;
        this.lineItems = lineItems;
        this.customLineItems = customLineItems;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.shippingMethod = shippingMethod;
    }

    CartDraftBuilder(final CartDraft template) {
        this(template.getCurrency(), template.getCustomerId(), template.getCountry(), template.getInventoryMode(), template.getCustom(), template.getCustomerEmail(), template.getLineItems(), template.getCustomLineItems(), template.getShippingAddress(), template.getBillingAddress(), template.getShippingMethod());
    }

    public static CartDraftBuilder of(final CurrencyUnit currency) {
        return new CartDraftBuilder(CartDraft.of(currency));
    }

    public CartDraftBuilder customerId(@Nullable final String customerId) {
        this.customerId = customerId;
        return this;
    }
    
    public CartDraftBuilder customerEmail(@Nullable final String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public CartDraftBuilder country(@Nullable final CountryCode country) {
        this.country = country;
        return this;
    }

    public CartDraftBuilder inventoryMode(@Nullable final InventoryMode inventoryMode) {
        this.inventoryMode = inventoryMode;
        return this;
    }

    @Override
    public CartDraftDsl build() {
        return new CartDraftDsl(currency, customerId, country, inventoryMode, custom, customerEmail, lineItems, customLineItems, shippingAddress, billingAddress, shippingMethod);
    }

    public CartDraftBuilder custom(@Nullable final CustomFieldsDraft custom) {
        this.custom = custom;
        return this;
    }

    public CartDraftBuilder lineItems(@Nullable final List<LineItemDraft> lineItems) {
        this.lineItems = lineItems;
        return this;
    }

    public CartDraftBuilder customLineItems(@Nullable final List<CustomLineItemDraft> customLineItems) {
        this.customLineItems = customLineItems;
        return this;
    }

    public CartDraftBuilder shippingAddress(@Nullable final Address shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public CartDraftBuilder billingAddress(@Nullable final Address billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public CartDraftBuilder shippingMethod(@Nullable final Referenceable<ShippingMethod> shippingMethod) {
        final Reference<ShippingMethod> reference = Optional.ofNullable(shippingMethod)
                .map(Referenceable::toReference)
                .orElse(null);
        this.shippingMethod = reference;
        return this;
    }

    @Nullable
    public Address getBillingAddress() {
        return billingAddress;
    }

    @Nullable
    public CountryCode getCountry() {
        return country;
    }

    public CurrencyUnit getCurrency() {
        return currency;
    }

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Nullable
    public String getCustomerEmail() {
        return customerEmail;
    }

    @Nullable
    public String getCustomerId() {
        return customerId;
    }

    @Nullable
    public List<CustomLineItemDraft> getCustomLineItems() {
        return customLineItems;
    }

    @Nullable
    public InventoryMode getInventoryMode() {
        return inventoryMode;
    }

    @Nullable
    public List<LineItemDraft> getLineItems() {
        return lineItems;
    }

    @Nullable
    public Address getShippingAddress() {
        return shippingAddress;
    }

    @Nullable
    public Reference<ShippingMethod> getShippingMethod() {
        return shippingMethod;
    }
}
