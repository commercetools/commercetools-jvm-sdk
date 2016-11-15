package io.sphere.sdk.products.expansion;

import javax.annotation.Nullable;
import java.util.List;

public interface ProductVariantExpansionModel<T> {
    PriceExpansionModel<T> prices();

    PriceExpansionModel<T> prices(int index);

    ProductAttributeExpansionModel<T> attributes();

    static <T> ProductVariantExpansionModel<T> of(@Nullable final List<String> parentPath, final String paths) {
        return new ProductVariantExpansionModelImpl<>(parentPath, paths);
    }
}
