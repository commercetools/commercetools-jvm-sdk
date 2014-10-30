package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neovisionaries.i18n.CountryCode;

import javax.money.CurrencyUnit;
import java.util.Optional;

public class CartDraft {
    private final CurrencyUnit currency;
    private final Optional<String> customerId;
    private final Optional<CountryCode> country;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Optional<InventoryMode> inventoryMode;

    CartDraft(final CurrencyUnit currency, final Optional<String> customerId,
                     final Optional<CountryCode> country,
                     final Optional<InventoryMode> inventoryMode) {
        this.currency = currency;
        this.customerId = customerId;
        this.country = country;
        this.inventoryMode = inventoryMode;
    }

    public static CartDraft of(final CurrencyUnit currency) {
        return new CartDraft(currency, Optional.empty(), Optional.empty(), Optional.empty());
    }

    public CurrencyUnit getCurrency() {
        return currency;
    }

    public Optional<String> getCustomerId() {
        return customerId;
    }

    public Optional<CountryCode> getCountry() {
        return country;
    }

    public Optional<InventoryMode> getInventoryMode() {
        return inventoryMode;
    }

    public CartDraft withCustomerId(final Optional<String> customerId) {
        return new CartDraftBuilder(this).customerId(customerId).build();
    }

    public CartDraft withCustomerId(final String customerId) {
        return withCustomerId(Optional.of(customerId));
    }

    public CartDraft withCountry(final Optional<CountryCode> country) {
        return new CartDraftBuilder(this).country(country).build();
    }

    public CartDraft withCountry(final CountryCode country) {
        return withCountry(Optional.of(country));
    }

    public CartDraft withInventoryMode(final Optional<InventoryMode> inventoryMode) {
        return new CartDraftBuilder(this).inventoryMode(inventoryMode).build();
    }

    public CartDraft withInventoryMode(final InventoryMode inventoryMode) {
        return withInventoryMode(Optional.of(inventoryMode));
    }
}
