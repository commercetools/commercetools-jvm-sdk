package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;

/**
 * <p>Draft for {@link Cart} creation.</p>
 *
 * @see io.sphere.sdk.carts.commands.CartCreateCommand
 * @see CartDraftBuilder
 */
public class CartDraft {
    private final CurrencyUnit currency;
    @Nullable
    private final String customerId;
    @Nullable
    private final CountryCode country;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final InventoryMode inventoryMode;

    @Nullable
    private final CustomFieldsDraft custom;

    CartDraft(final CurrencyUnit currency, final String customerId,
              @Nullable final CountryCode country,
              @Nullable final InventoryMode inventoryMode,
              @Nullable final CustomFieldsDraft custom) {
        this.currency = currency;
        this.customerId = customerId;
        this.country = country;
        this.inventoryMode = inventoryMode;
        this.custom = custom;
    }

    public static CartDraft of(final CurrencyUnit currency) {
        return new CartDraft(currency, null, null, null, null);
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

    public CartDraft withCustom(@Nullable final CustomFieldsDraft custom) {
        return new CartDraftBuilder(this).custom(custom).build();
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
