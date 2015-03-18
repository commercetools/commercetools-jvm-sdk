package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultModelImpl;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

class CartImpl extends DefaultModelImpl<Cart> implements Cart {
    private final Optional<String> customerId;
    private final Optional<String> customerEmail;
    private final List<LineItem> lineItems;
    private final List<CustomLineItem> customLineItems;
    private final MonetaryAmount totalPrice;
    private final Optional<TaxedPrice> taxedPrice;
    private final CartState cartState;
    private final Optional<Address> shippingAddress;
    private final Optional<Address>  billingAddress;
    private final InventoryMode inventoryMode;
    private final Optional<Reference<CustomerGroup>> customerGroup;
    private final Optional<CountryCode> country;
    private final Optional<CartShippingInfo> shippingInfo;

    @JsonCreator
    CartImpl(final String id, final long version, final Instant createdAt,
             final Instant lastModifiedAt, final Optional<String> customerId,
             final Optional<String> customerEmail, final List<LineItem> lineItems,
             final List<CustomLineItem> customLineItems, final MonetaryAmount totalPrice,
             final Optional<TaxedPrice> taxedPrice, final CartState cartState,
             final Optional<Address> shippingAddress, final Optional<Address> billingAddress,
             final InventoryMode inventoryMode, final Optional<Reference<CustomerGroup>> customerGroup,
             final Optional<CountryCode> country, final Optional<CartShippingInfo> shippingInfo) {
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
    }

    @Override
    public Optional<String> getCustomerId() {
        return customerId;
    }

    @Override
    public Optional<String> getCustomerEmail() {
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
    public Optional<TaxedPrice> getTaxedPrice() {
        return taxedPrice;
    }

    @Override
    public CartState getCartState() {
        return cartState;
    }

    @Override
    public Optional<Address> getShippingAddress() {
        return shippingAddress;
    }

    @Override
    public Optional<Address> getBillingAddress() {
        return billingAddress;
    }

    @Override
    public InventoryMode getInventoryMode() {
        return inventoryMode;
    }

    @Override
    public Optional<Reference<CustomerGroup>> getCustomerGroup() {
        return customerGroup;
    }

    @Override
    public Optional<CountryCode> getCountry() {
        return country;
    }

    @Override
    public Optional<CartShippingInfo> getShippingInfo() {
        return shippingInfo;
    }
}
