package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;
import java.util.function.Function;

class ProductVariantQueryModelImpl<T> extends QueryModelImpl<T> implements ProductVariantQueryModel<T> {

    ProductVariantQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQueryModel<T> key() {
        return stringQuerySortingModel("key");
    }

    @Override
    public StringQueryModel<T> sku() {
        return stringModel("sku");
    }

    @Override
    public QueryPredicate<T> where(final QueryPredicate<EmbeddedProductVariantQueryModel> embeddedPredicate) {
        return embedPredicate(embeddedPredicate);
    }

    @Override
    public QueryPredicate<T> where(final Function<EmbeddedProductVariantQueryModel, QueryPredicate<EmbeddedProductVariantQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(EmbeddedProductVariantQueryModelImpl.of()));
    }

    @Override
    public PriceCollectionQueryModel<T> prices() {
        return new PriceCollectionQueryModelImpl<>(this, "prices");
    }
}
