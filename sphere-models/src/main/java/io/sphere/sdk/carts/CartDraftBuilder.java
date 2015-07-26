package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

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

    CartDraftBuilder(final CurrencyUnit currency, @Nullable final String customerId, @Nullable final CountryCode country, @Nullable final InventoryMode inventoryMode) {
        this.currency = currency;
        this.customerId = customerId;
        this.country = country;
        this.inventoryMode = inventoryMode;
    }

    CartDraftBuilder(final CartDraft template) {
        this(template.getCurrency(), template.getCustomerId(), template.getCountry(), template.getInventoryMode());
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
        return new CartDraft(currency, customerId, country, inventoryMode);
    }
}
