package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.TaxMode;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import java.util.List;


public final class CartDraftDsl extends Base implements CartDraft {
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
    @Nullable
    private final TaxMode taxMode;


    CartDraftDsl(final CurrencyUnit currency,
                 @Nullable final String customerId,
                 @Nullable final CountryCode country,
                 @Nullable final InventoryMode inventoryMode,
                 @Nullable final CustomFieldsDraft custom,
                 @Nullable final String customerEmail,
                 @Nullable final List<LineItemDraft> lineItems,
                 @Nullable final List<CustomLineItemDraft> customLineItems,
                 @Nullable final Address shippingAddress,
                 @Nullable final Address billingAddress,
                 @Nullable final Reference<ShippingMethod> shippingMethod,
                 @Nullable final TaxMode taxMode) {
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
        this.taxMode = taxMode;
    }

    public static CartDraftDsl of(final CurrencyUnit currency) {
        return new CartDraftDsl(currency, null, null, null, null, null, null, null, null, null, null, null);
    }

    @Override
    public CurrencyUnit getCurrency() {
        return currency;
    }

    @Override
    @Nullable
    public String getCustomerId() {
        return customerId;
    }

    @Override
    @Nullable
    public CountryCode getCountry() {
        return country;
    }
    @Override
    @Nullable
    public InventoryMode getInventoryMode() {
        return inventoryMode;
    }

    @Override
    @Nullable
    public String getCustomerEmail() {
        return customerEmail;
    }

    @Override
    @Nullable
    public Address getBillingAddress() {
        return billingAddress;
    }

    @Override
    @Nullable
    public List<CustomLineItemDraft> getCustomLineItems() {
        return customLineItems;
    }

    @Override
    @Nullable
    public List<LineItemDraft> getLineItems() {
        return lineItems;
    }

    @Override
    @Nullable
    public Address getShippingAddress() {
        return shippingAddress;
    }

    @Override
    @Nullable
    public Reference<ShippingMethod> getShippingMethod() {
        return shippingMethod;
    }

    public CartDraftDsl withCustomerId(@Nullable final String customerId) {
        return new CartDraftBuilder(this).customerId(customerId).build();
    }

    public CartDraftDsl withCustomerEmail(@Nullable final String customerEmail) {
        return new CartDraftBuilder(this).customerEmail(customerEmail).build();
    }

    public CartDraftDsl withCountry(@Nullable final CountryCode country) {
        return new CartDraftBuilder(this).country(country).build();
    }

    public CartDraftDsl withInventoryMode(@Nullable final InventoryMode inventoryMode) {
        return new CartDraftBuilder(this).inventoryMode(inventoryMode).build();
    }

    public CartDraftDsl withCustom(@Nullable final CustomFieldsDraft custom) {
        return new CartDraftBuilder(this).custom(custom).build();
    }
    
    public CartDraftDsl withLineItems(@Nullable final List<LineItemDraft> lineItems) {
        return new CartDraftBuilder(this).lineItems(lineItems).build();
    }  
    
    public CartDraftDsl withCustomLineItems(@Nullable final List<CustomLineItemDraft> customLineItems) {
        return new CartDraftBuilder(this).customLineItems(customLineItems).build();
    }

    public CartDraftDsl withShippingAddress(@Nullable final Address shippingAddress) {
        return new CartDraftBuilder(this).shippingAddress(shippingAddress).build();
    }
    
    public CartDraftDsl withBillingAddress(@Nullable final Address billingAddress) {
        return new CartDraftBuilder(this).billingAddress(billingAddress).build();
    }

    public CartDraftDsl withShippingMethod(@Nullable final Referenceable<ShippingMethod> shippingMethod) {
        return new CartDraftBuilder(this).shippingMethod(shippingMethod).build();
    }

    /**
     *
     * @deprecated use {@link #withCustom(CustomFieldsDraft)} instead
     * @param custom draft for custom fields
     * @return CartDraft with custom fields
     */
    @Deprecated
    public CartDraftDsl witCustom(@Nullable final CustomFieldsDraft custom) {
        return withCustom(custom);
    }

    @Override
    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Override
    @Nullable
    public TaxMode getTaxMode() {
        return taxMode;
    }

    public CartDraftDsl withTaxMode(@Nullable final TaxMode taxMode) {
        return new CartDraftBuilder(this).taxMode(taxMode).build();
    }
}
