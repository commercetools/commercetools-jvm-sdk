package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

final class DiscountedLineItemPortionExpansionModelImpl<T> extends ExpansionModelImpl<T> implements DiscountedLineItemPortionExpansionModel<T> {

    DiscountedLineItemPortionExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }


    @Override
    public CartDiscountExpansionModel<T> discount() {
        return CartDiscountExpansionModel.of(buildPathExpression(), "discount");
    }
}
