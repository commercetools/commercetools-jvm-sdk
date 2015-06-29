package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.*;

import java.util.Optional;
import java.util.function.Function;

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

    public QueryPredicate<T> where(final QueryPredicate<PartialProductVariantQueryModel> embeddedPredicate) {
        return new EmbeddedQueryPredicate<>(this, embeddedPredicate);
    }

    public QueryPredicate<T> where(final Function<PartialProductVariantQueryModel, QueryPredicate<PartialProductVariantQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(get()));
    }

    public PriceCollectionQueryModel<T> prices() {
        return new PriceCollectionQueryModel<>(Optional.of(this), Optional.of("prices"));
    }
}
