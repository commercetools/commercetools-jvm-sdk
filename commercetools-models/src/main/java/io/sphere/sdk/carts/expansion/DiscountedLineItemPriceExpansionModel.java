package io.sphere.sdk.carts.expansion;

import javax.annotation.Nullable;
import java.util.List;

public interface DiscountedLineItemPriceExpansionModel<T> {
    DiscountedLineItemPortionExpansionModel<T> includedDiscounts();

    static <T> DiscountedLineItemPriceExpansionModel<T> of(@Nullable final List<String> parentPath, final String paths) {
        return new DiscountedLineItemPriceExpansionModelImpl<>(parentPath, paths);
    }
}
