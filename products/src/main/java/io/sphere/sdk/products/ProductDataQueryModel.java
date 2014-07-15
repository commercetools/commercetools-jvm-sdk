package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.queries.*;

public final class ProductDataQueryModel<T> extends EmbeddedQueryModel<T, ProductQueryModel<Product>> {

    private static final ProductDataQueryModel<ProductDataQueryModel<Product>> instance =
            new ProductDataQueryModel<>(Optional.empty(), Optional.<String>empty());

    public static ProductDataQueryModel<ProductDataQueryModel<Product>> get() {
        return instance;
    }

    ProductDataQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQuerySortingModel<T> name() {
        return localizedNameModel();
    }

    public LocalizedStringQueryModel<T> description() {
        return localizedStringQueryModel("description");
    }

    public LocalizedStringQuerySortingModel<T> slug() {
        return localizedSlugModel();
    }
}

