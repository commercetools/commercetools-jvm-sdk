package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.*;

import java.util.Optional;

public class ProductVariantQueryModel<T> extends QueryModelImpl<T> {

    public static PartialProductVariantQueryModel get() {
        return new PartialProductVariantQueryModel(Optional.empty(), Optional.empty());
    }

    ProductVariantQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    ProductVariantQueryModel(Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        this(parent, Optional.of(pathSegment));
    }

    public StringQueryModel<T> sku() {
        return new StringQuerySortingModel<>(Optional.of(this), "sku");
    }


    public Predicate<T> where(final Predicate<PartialProductVariantQueryModel> embeddedPredicate) {
        return new EmbeddedPredicate<>(this, embeddedPredicate);
    }
}
