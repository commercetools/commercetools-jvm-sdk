package io.sphere.sdk.products.search;

import javax.annotation.Nullable;

public interface PriceSelectionRequestDsl<T> {
    T withPriceSelection(@Nullable final PriceSelection priceSelection);

    @Nullable
    PriceSelection getPriceSelection();
}
