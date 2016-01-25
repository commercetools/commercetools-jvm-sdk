package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.ResourceImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;

class CartImpl extends ResourceImpl<Cart> implements Cart {
    @Nullable
    private final String customerId;
    @Nullable
    private final String customerEmail;
    private final List<LineItem> lineItems;
    private final List<CustomLineItem> customLineItems;
    private final MonetaryAmount totalPrice;
    @Nullable
    private final TaxedPrice taxedPrice;
    private final CartState cartState;
    @Nullable
    private final Address shippingAddress;
    @Nullable
    private final Address billingAddress;
    private final InventoryMode inventoryMode;
    @Nullable
    private final Reference<CustomerGroup> customerGroup;
    @Nullable
    private final CountryCode country;
    @Nullable
    private final CartShippingInfo shippingInfo;
    private final List<DiscountCodeInfo> discountCodes;
    @Nullable
    private final CustomFields custom;
    @Nullable
    private final PaymentInfo paymentInfo;

    @JsonCreator
    CartImpl(final String id, final Long version, final ZonedDateTime createdAt,
             final ZonedDateTime lastModifiedAt, @Nullable final String customerId,
             @Nullable final String customerEmail, final List<LineItem> lineItems,
             final List<CustomLineItem> customLineItems, final MonetaryAmount totalPrice,
             @Nullable final TaxedPrice taxedPrice, final CartState cartState,
             @Nullable final Address shippingAddress, @Nullable final Address billingAddress,
             final InventoryMode inventoryMode, @Nullable final Reference<CustomerGroup> customerGroup,
             @Nullable final CountryCode country, @Nullable final CartShippingInfo shippingInfo,
             final List<DiscountCodeInfo> discountCodes, @Nullable final CustomFields custom, @Nullable final PaymentInfo paymentInfo) {
        super(id, version, createdAt, lastModifiedAt);
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.lineItems = lineItems;
        this.customLineItems = customLineItems;
        this.totalPrice = totalPrice;
        this.taxedPrice = taxedPrice;
        this.cartState = cartState;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.inventoryMode = inventoryMode;
        this.customerGroup = customerGroup;
        this.country = country;
        this.shippingInfo = shippingInfo;
        this.discountCodes = discountCodes;
        this.custom = custom;
        this.paymentInfo = paymentInfo;
    }

    @Override
    @Nullable
    public String getCustomerId() {
        return customerId;
    }

    @Override
    @Nullable
    public String getCustomerEmail() {
        return customerEmail;
    }

    @Override
    public List<LineItem> getLineItems() {
        return lineItems;
    }

    @Override
    public List<CustomLineItem> getCustomLineItems() {
        return customLineItems;
    }

    @Override
    public MonetaryAmount getTotalPrice() {
        return totalPrice;
    }

    @Override
    @Nullable
    public TaxedPrice getTaxedPrice() {
        return taxedPrice;
    }

    @Override
    public CartState getCartState() {
        return cartState;
    }

    @Override
    @Nullable
    public Address getShippingAddress() {
        return shippingAddress;
    }

    @Override
    @Nullable
    public Address getBillingAddress() {
        return billingAddress;
    }

    @Override
    public InventoryMode getInventoryMode() {
        return inventoryMode;
    }

    @Override
    @Nullable
    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }

    @Override
    @Nullable
    public CountryCode getCountry() {
        return country;
    }

    @Override
    @Nullable
    public CartShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    @Override
    public List<DiscountCodeInfo> getDiscountCodes() {
        return discountCodes;
    }

    @Override
    @Nullable
    public CustomFields getCustom() {
        return custom;
    }

    @Override
    @Nullable
    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }
}
