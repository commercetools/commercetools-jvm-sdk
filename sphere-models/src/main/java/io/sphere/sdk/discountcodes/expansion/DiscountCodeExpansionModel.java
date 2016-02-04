package io.sphere.sdk.discountcodes.expansion;

import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.expansion.ExpandedModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import javax.annotation.Nullable;
import java.util.List;

public final class DiscountCodeExpansionModel<T> extends ExpandedModel<T> {
    private DiscountCodeExpansionModel(@Nullable final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    DiscountCodeExpansionModel() {
        super();
    }

    public static DiscountCodeExpansionModel<DiscountCode> of() {
        return new DiscountCodeExpansionModel<>();
    }

    public static <T> DiscountCodeExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new DiscountCodeExpansionModel<>(parentPath, path);
    }

    public CartDiscountExpansionModel<T> cartDiscounts() {
        return CartDiscountExpansionModel.of(pathExpression(), "cartDiscounts[*]");
    }

    public ExpansionPathContainer<T> references() {
        return expansionPath("references[*]");
    }
}
