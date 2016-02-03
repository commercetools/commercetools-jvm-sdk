package io.sphere.sdk.discountcodes.expansion;

import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import javax.annotation.Nullable;

public class DiscountCodeExpansionModel<T> extends ExpansionModel<T> {
    private DiscountCodeExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    DiscountCodeExpansionModel() {
        super();
    }

    public static DiscountCodeExpansionModel<DiscountCode> of() {
        return new DiscountCodeExpansionModel<>();
    }

    public CartDiscountExpansionModel<T> cartDiscounts() {
        return CartDiscountExpansionModel.of(pathExpression(), "cartDiscounts[*]");
    }

    public ExpansionPathContainer<T> references() {
        return expansionPath("references[*]");
    }
}
