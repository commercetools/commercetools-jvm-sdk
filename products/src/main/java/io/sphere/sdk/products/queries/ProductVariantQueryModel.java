package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.*;

import java.util.Optional;

public class ProductVariantQueryModel<M> extends QueryModelImpl<M> {

    public static PartialProductVariantQueryModel get() {
        return new PartialProductVariantQueryModel(Optional.empty(), Optional.empty());
    }

    ProductVariantQueryModel(final Optional<? extends QueryModel<M>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    ProductVariantQueryModel(Optional<? extends QueryModel<M>> parent, final String pathSegment) {
        this(parent, Optional.of(pathSegment));
    }

    public StringQueryModel<M> sku() {
        return new StringQuerySortingModel<>(Optional.of(this), "sku");
    }


    public Predicate<M> where(final Predicate<PartialProductVariantQueryModel> embeddedPredicate) {
        return new EmbeddedPredicate<>(this, embeddedPredicate);
    }
}
