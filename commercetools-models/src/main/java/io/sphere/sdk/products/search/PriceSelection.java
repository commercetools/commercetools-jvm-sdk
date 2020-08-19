package io.sphere.sdk.products.search;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import java.util.List;

/**
 * Parameters to select prices in {@link ProductProjectionSearch}.
 *
 * Use {@link PriceSelectionBuilder} or {@link PriceSelectionDsl} to create an instance.
 *
 * @see ProductProjectionSearch#withPriceSelection(PriceSelection)
 *
 */
public interface PriceSelection {
    @Nonnull
    String getPriceCurrency();
    @Nullable
    String getPriceCountry();
    @Nullable
    String getPriceCustomerGroup();
    @Nullable
    String getPriceChannel();

    static PriceSelectionDsl ofCurrencyCode(final String currencyCode) {
        return PriceSelectionBuilder.ofCurrencyCode(currencyCode).build();
    }

    static PriceSelectionDsl of(@Nonnull final CurrencyUnit currencyUnit) {
        return ofCurrencyCode(currencyUnit.getCurrencyCode());
    }
}
