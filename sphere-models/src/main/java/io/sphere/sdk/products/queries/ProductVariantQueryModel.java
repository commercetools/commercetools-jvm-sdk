package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.*;

import java.util.function.Function;

public class ProductVariantQueryModel<T> extends QueryModelImpl<T> {

    ProductVariantQueryModel(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQueryModel<T> sku() {
        return stringModel("sku");
    }

    public QueryPredicate<T> where(final QueryPredicate<PartialProductVariantQueryModel> embeddedPredicate) {
        return new EmbeddedQueryPredicate<>(this, embeddedPredicate);
    }

    public QueryPredicate<T> where(final Function<PartialProductVariantQueryModel, QueryPredicate<PartialProductVariantQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(PartialProductVariantQueryModel.of()));
    }

    public PriceCollectionQueryModel<T> prices() {
        return new PriceCollectionQueryModel<>(this, "prices");
    }
}
