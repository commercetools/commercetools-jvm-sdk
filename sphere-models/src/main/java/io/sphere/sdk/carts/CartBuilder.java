package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.DefaultModelFluentBuilder;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.utils.MoneyImpl;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CartBuilder extends DefaultModelFluentBuilder<CartBuilder, Cart> {

    private Optional<String> customerId = Optional.empty();
    private Optional<String> customerEmail = Optional.empty();
    private List<LineItem> lineItems = Collections.emptyList();
    private List<CustomLineItem> customLineItems = Collections.emptyList();
    private MonetaryAmount totalPrice;
    private Optional<TaxedPrice> taxedPrice = Optional.empty();
    private CartState cartState = CartState.defaultValue();
    private Optional<Address> shippingAddress = Optional.empty();
    private Optional<Address>  billingAddress = Optional.empty();
    private InventoryMode inventoryMode = InventoryMode.defaultValue();
    private Optional<Reference<CustomerGroup>> customerGroup = Optional.empty();
    private Optional<CountryCode> country = Optional.empty();
    private Optional<CartShippingInfo> shippingInfo = Optional.empty();

    private CartBuilder(final CurrencyUnit currency) {
        this.totalPrice = MoneyImpl.of(BigDecimal.ZERO, currency);
    }

    public static CartBuilder of(final CurrencyUnit currency) {
        return new CartBuilder(currency);
    }

    public CartBuilder customerId(final Optional<String> customerId) {
        this.customerId = customerId;
        return this;
    }

    public CartBuilder customerId(final String customerId) {
        return customerId(Optional.of(customerId));
    }

    public CartBuilder customerEmail(final Optional<String> customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public CartBuilder customerEmail(final String customerEmail) {
        return customerEmail(Optional.of(customerEmail));
    }

    public CartBuilder lineItems(final List<LineItem> lineItems) {
        this.lineItems = lineItems;
        return this;
    }

    public CartBuilder customLineItems(final List<CustomLineItem> customLineItems) {
        this.customLineItems = customLineItems;
        return this;
    }

    public CartBuilder totalPrice(final MonetaryAmount totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public CartBuilder taxedPrice(final Optional<TaxedPrice> taxedPrice) {
        this.taxedPrice = taxedPrice;
        return this;
    }

    public CartBuilder taxedPrice(final TaxedPrice taxedPrice) {
        return taxedPrice(Optional.of(taxedPrice));
    }

    public CartBuilder cartState(final CartState cartState) {
        this.cartState = cartState;
        return this;
    }

    public CartBuilder shippingAddress(final Optional<Address> shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public CartBuilder shippingAddress(final Address shippingAddress) {
        return shippingAddress(Optional.of(shippingAddress));
    }

    public CartBuilder billingAddress(final Optional<Address> billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public CartBuilder billingAddress(final Address billingAddress) {
        return billingAddress(Optional.of(billingAddress));
    }

    public CartBuilder inventoryMode(final InventoryMode inventoryMode) {
        this.inventoryMode = inventoryMode;
        return this;
    }

    public CartBuilder customerGroup(final Optional<Reference<CustomerGroup>> customerGroup) {
        this.customerGroup = customerGroup;
        return this;
    }

    public CartBuilder customerGroup(final Reference<CustomerGroup> customerGroup) {
        return customerGroup(Optional.of(customerGroup));
    }

    public CartBuilder country(final Optional<CountryCode> country) {
        this.country = country;
        return this;
    }

    public CartBuilder country(final CountryCode country) {
        return country(Optional.of(country));
    }

    public CartBuilder shippingInfo(final Optional<CartShippingInfo> shippingInfo) {
        this.shippingInfo = shippingInfo;
        return this;
    }

    public CartBuilder shippingInfo(final CartShippingInfo shippingInfo) {
        return shippingInfo(Optional.of(shippingInfo));
    }

    @Override
    protected CartBuilder getThis() {
        return this;
    }

    @Override
    public Cart build() {
        return new CartImpl(id, version, createdAt, lastModifiedAt, customerId, customerEmail,
                lineItems, customLineItems, totalPrice, taxedPrice, cartState, shippingAddress,
                billingAddress, inventoryMode, customerGroup, country, shippingInfo);
    }
}
