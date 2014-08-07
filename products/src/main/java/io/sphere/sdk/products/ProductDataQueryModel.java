package io.sphere.sdk.products;

import java.util.Optional;
import java.util.function.Function;

import io.sphere.sdk.queries.*;

public final class ProductDataQueryModel extends EmbeddedQueryModel<Product> {

    private static final ProductDataQueryModel instance = new ProductDataQueryModel(Optional.empty(), Optional.empty());

    ProductDataQueryModel(Optional<? extends QueryModel<Product>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQuerySortingModel<Product> name() {
        return localizedStringQueryModel("name");
    }

    public LocalizedStringQueryModel<Product> description() {
        return localizedStringQueryModel("description");
    }

    public LocalizedStringQuerySortingModel<Product> slug() {
        return localizedStringQueryModel("slug");
    }

    public Predicate<Product> where(final Function<ProductDataQueryModel, Predicate<Product>> f) {
        final Predicate<Product> subPredicate = f.apply(instance);
        return new SubPredicate<>(this, subPredicate);
    }
}

