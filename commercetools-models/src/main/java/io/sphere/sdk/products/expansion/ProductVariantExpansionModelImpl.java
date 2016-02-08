package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

import javax.annotation.Nullable;
import java.util.List;

final class ProductVariantExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ProductVariantExpansionModel<T> {
    ProductVariantExpansionModelImpl(@Nullable final List<String> parentPath, final String paths) {
        super(parentPath, paths);
    }

    @Override
    public PriceExpansionModel<T> prices() {
        return prices("*");
    }

    @Override
    public PriceExpansionModel<T> prices(final int index) {
        return prices("" + index);
    }

    @Override
    public ProductAttributeExpansionModel<T> attributes() {
        return new ProductAttributeExpansionModelImpl<>(pathExpression(), "attributes[*]");
    }

    private PriceExpansionModel<T> prices(final String index) {
        return new PriceExpansionModelImpl<>(pathExpression(), "prices[" + index + "]");
    }
}

