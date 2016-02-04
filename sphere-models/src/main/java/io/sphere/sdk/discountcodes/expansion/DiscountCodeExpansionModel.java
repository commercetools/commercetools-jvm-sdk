package io.sphere.sdk.discountcodes.expansion;

import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.List;

public interface DiscountCodeExpansionModel<T> extends ExpansionPathContainer<T> {
    CartDiscountExpansionModel<T> cartDiscounts();

    ExpansionPathContainer<T> references();

    static DiscountCodeExpansionModel<DiscountCode> of() {
        return new DiscountCodeExpansionModelImpl<>();
    }

    static <T> DiscountCodeExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new DiscountCodeExpansionModelImpl<>(parentPath, path);
    }
}
