package io.sphere.sdk.carts;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.money.CurrencyUnit;
import java.util.Optional;

public class CartDraftBuilder extends Base implements Builder<CartDraft> {
    private final CurrencyUnit currency;
    private Optional<String> customerId;
    private Optional<CountryCode> country;
    private Optional<InventoryMode> inventoryMode;

    CartDraftBuilder(final CurrencyUnit currency, final Optional<String> customerId, final Optional<CountryCode> country, final Optional<InventoryMode> inventoryMode) {
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

    public CartDraftBuilder customerId(final Optional<String> customerId) {
        this.customerId = customerId;
        return this;
    }

    public CartDraftBuilder customerId(final String customerId) {
        return customerId(Optional.of(customerId));
    }

    public CartDraftBuilder country(final Optional<CountryCode> country) {
        this.country = country;
        return this;
    }

    public CartDraftBuilder country(final CountryCode country) {
        return country(Optional.of(country));
    }

    public CartDraftBuilder inventoryMode(final Optional<InventoryMode> inventoryMode) {
        this.inventoryMode = inventoryMode;
        return this;
    }

    public CartDraftBuilder inventoryMode(final InventoryMode inventoryMode) {
        return inventoryMode(Optional.of(inventoryMode));
    }

    @Override
    public CartDraft build() {
        return new CartDraft(currency, customerId, country, inventoryMode);
    }
}
