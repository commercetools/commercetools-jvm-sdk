package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;

import javax.annotation.Nullable;
import java.util.List;

final class DiscountedPriceExpansionModelImpl<T> extends ExpansionModelImpl<T> implements DiscountedPriceExpansionModel<T> {
    DiscountedPriceExpansionModelImpl(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    @Override
    public ProductDiscountExpansionModel<T> discount() {
        return ProductDiscountExpansionModel.of(buildPathExpression(), "discount");
    }
}

