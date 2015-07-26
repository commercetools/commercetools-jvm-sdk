package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;

public class CartDraft {
    private final CurrencyUnit currency;
    @Nullable
    private final String customerId;
    @Nullable
    private final CountryCode country;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final InventoryMode inventoryMode;

    CartDraft(final CurrencyUnit currency, final String customerId,
              @Nullable final CountryCode country,
              @Nullable final InventoryMode inventoryMode) {
        this.currency = currency;
        this.customerId = customerId;
        this.country = country;
        this.inventoryMode = inventoryMode;
    }

    public static CartDraft of(final CurrencyUnit currency) {
        return new CartDraft(currency, null, null, null);
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

    public CartDraft withCustomerId(@Nullable final String customerId) {
        return new CartDraftBuilder(this).customerId(customerId).build();
    }

    public CartDraft withCountry(@Nullable final CountryCode country) {
        return new CartDraftBuilder(this).country(country).build();
    }

    public CartDraft withInventoryMode(@Nullable final InventoryMode inventoryMode) {
        return new CartDraftBuilder(this).inventoryMode(inventoryMode).build();
    }
}
