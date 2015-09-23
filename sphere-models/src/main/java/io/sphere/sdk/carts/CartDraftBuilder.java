package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;

public class CartDraftBuilder extends Base implements Builder<CartDraft> {
    private final CurrencyUnit currency;
    @Nullable
    private String customerId;
    @Nullable
    private CountryCode country;
    @Nullable
    private InventoryMode inventoryMode;
    @Nullable
    private CustomFieldsDraft custom;

    CartDraftBuilder(final CurrencyUnit currency, @Nullable final String customerId, @Nullable final CountryCode country, @Nullable final InventoryMode inventoryMode, @Nullable final CustomFieldsDraft custom) {
        this.currency = currency;
        this.customerId = customerId;
        this.country = country;
        this.inventoryMode = inventoryMode;
        this.custom = custom;
    }

    CartDraftBuilder(final CartDraft template) {
        this(template.getCurrency(), template.getCustomerId(), template.getCountry(), template.getInventoryMode(), template.getCustom());
    }

    public static CartDraftBuilder of(final CurrencyUnit currency) {
        return new CartDraftBuilder(CartDraft.of(currency));
    }

    public CartDraftBuilder customerId(@Nullable final String customerId) {
        this.customerId = customerId;
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
    public CartDraft build() {
        return new CartDraft(currency, customerId, country, inventoryMode, custom);
    }

    public CartDraftBuilder custom(final CustomFieldsDraft custom) {
        this.custom = custom;
        return this;
    }
}
