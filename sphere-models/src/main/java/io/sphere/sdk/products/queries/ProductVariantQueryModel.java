package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.StringQueryModel;

import javax.annotation.Nullable;
import java.util.function.Function;

public interface ProductVariantQueryModel<T> {
    StringQueryModel<T> sku();

    QueryPredicate<T> where(QueryPredicate<PartialProductVariantQueryModel> embeddedPredicate);

    QueryPredicate<T> where(Function<PartialProductVariantQueryModel, QueryPredicate<PartialProductVariantQueryModel>> embeddedPredicate);

    PriceCollectionQueryModel<T> prices();

    static <T> ProductVariantQueryModel<T> of(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        return new ProductVariantQueryModelImpl<>(parent, pathSegment);
    }
}
