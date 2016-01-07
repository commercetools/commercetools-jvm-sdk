package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import java.util.List;

/**
 * <p>Draft for {@link Cart} creation.</p>
 *
 * @see io.sphere.sdk.carts.commands.CartCreateCommand
 * @see CartDraftBuilder
 */
public class CartDraft extends Base implements CustomDraft {
    private final CurrencyUnit currency;
    @Nullable
    private final String customerId;
    @Nullable
    private final String customerEmail;
    @Nullable
    private final CountryCode country;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final InventoryMode inventoryMode;

    @Nullable
    private final CustomFieldsDraft custom;
    @Nullable
    private final List<LineItemDraft> lineItems;
    @Nullable
    private final List<CustomLineItemDraft> customLineItems;
    @Nullable
    private final Address shippingAddress;
    @Nullable
    private final Address billingAddress;
    @Nullable
    private final Reference<ShippingMethod> shippingMethod;


    CartDraft(final CurrencyUnit currency,
              @Nullable final String customerId,
              @Nullable final CountryCode country,
              @Nullable final InventoryMode inventoryMode,
              @Nullable final CustomFieldsDraft custom,
              @Nullable final String customerEmail,
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

    public static CartDraft of(final CurrencyUnit currency) {
        return new CartDraft(currency, null, null, null, null, null, null, null, null, null, null);
    }

    public CurrencyUnit getCurrency() {
        return currency;
    }

    @Nullable
    public String getCustomerId() {
        return customerId;
    }

    @Nullable
    public CountryCode getCountry() {
        return country;
    }
    @Nullable
    public InventoryMode getInventoryMode() {
        return inventoryMode;
    }

    @Nullable
    public String getCustomerEmail() {
        return customerEmail;
    }

    @Nullable
    public Address getBillingAddress() {
        return billingAddress;
    }

    @Nullable
    public List<CustomLineItemDraft> getCustomLineItems() {
        return customLineItems;
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

    public CartDraft withCustomerId(@Nullable final String customerId) {
        return new CartDraftBuilder(this).customerId(customerId).build();
    }

    public CartDraft withCustomerEmail(@Nullable final String customerEmail) {
        return new CartDraftBuilder(this).customerEmail(customerEmail).build();
    }

    public CartDraft withCountry(@Nullable final CountryCode country) {
        return new CartDraftBuilder(this).country(country).build();
    }

    public CartDraft withInventoryMode(@Nullable final InventoryMode inventoryMode) {
        return new CartDraftBuilder(this).inventoryMode(inventoryMode).build();
    }

    public CartDraft withCustom(@Nullable final CustomFieldsDraft custom) {
        return new CartDraftBuilder(this).custom(custom).build();
    }
    
    public CartDraft withLineItems(@Nullable final List<LineItemDraft> lineItems) {
        return new CartDraftBuilder(this).lineItems(lineItems).build();
    }  
    
    public CartDraft withCustomLineItems(@Nullable final List<CustomLineItemDraft> customLineItems) {
        return new CartDraftBuilder(this).customLineItems(customLineItems).build();
    }

    public CartDraft withShippingAddress(@Nullable final Address shippingAddress) {
        return new CartDraftBuilder(this).shippingAddress(shippingAddress).build();
    }
    
    public CartDraft withBillingAddress(@Nullable final Address billingAddress) {
        return new CartDraftBuilder(this).billingAddress(billingAddress).build();
    }

    public CartDraft withShippingMethod(@Nullable final Referenceable<ShippingMethod> shippingMethod) {
        return new CartDraftBuilder(this).shippingMethod(shippingMethod).build();
    }

    /**
     *
     * @deprecated use {@link #withCustom(CustomFieldsDraft)} instead
     * @param custom draft for custom fields
     * @return CartDraft with custom fields
     */
    @Deprecated
    public CartDraft witCustom(@Nullable final CustomFieldsDraft custom) {
        return withCustom(custom);
    }

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }
}
