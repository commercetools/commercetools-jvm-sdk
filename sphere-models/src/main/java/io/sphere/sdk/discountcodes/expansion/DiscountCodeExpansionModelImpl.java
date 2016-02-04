package io.sphere.sdk.discountcodes.expansion;

import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import javax.annotation.Nullable;
import java.util.List;

final class DiscountCodeExpansionModelImpl<T> extends ExpansionModel<T> implements DiscountCodeExpansionModel<T> {
    DiscountCodeExpansionModelImpl(@Nullable final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    DiscountCodeExpansionModelImpl() {
        super();
    }

    @Override
    public CartDiscountExpansionModel<T> cartDiscounts() {
        return CartDiscountExpansionModel.of(pathExpression(), "cartDiscounts[*]");
    }

    @Override
    public ExpansionPathContainer<T> references() {
        return expansionPath("references[*]");
    }
}
